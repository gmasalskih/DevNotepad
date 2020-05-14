package com.example.devnotepad.ui.fragment_article_content

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.devnotepad.ArticleHeader
import com.example.devnotepad.BaseApplication
import com.example.devnotepad.NotepadData
import com.example.devnotepad.data.repositories.ArticlesHeadersRepository
import com.example.devnotepad.data.local.KnowledgeRoomDatabase
import com.example.devnotepad.data.repositories.RepositoryContractForArticlesContent
import com.example.devnotepad.data.rest.DevNotepadApi
import com.example.devnotepad.data.rest.RetrofitCreator
import com.example.devnotepad.data.data_handlers.NotepadDataHandlerForContent
import com.example.devnotepad.data.data_handlers.NotepadViewModelContractForContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class ArticleContentViewModelForHeaders @Inject constructor(
    application: Application
) : AndroidViewModel(application),
    NotepadViewModelContractForContent {
    override val repositoryForArticlesContent: RepositoryContractForArticlesContent
    val allArticlesHeaders: LiveData<List<ArticleHeader>>
    private val devNotepadApi: DevNotepadApi
    private val notepadDataHandlerForContent: NotepadDataHandlerForContent
    private val headerType = "header"

    @Inject
    lateinit var retrofit: Retrofit

    init {
        val daggerAppComponent = BaseApplication.appComponent
        daggerAppComponent.inject(this)

        devNotepadApi = retrofit.create(DevNotepadApi::class.java)

        val articleHeaderDao = KnowledgeRoomDatabase.getDatabase(application).articleHeaderDao()

        repositoryForArticlesContent = ArticlesHeadersRepository(articleHeaderDao)
        allArticlesHeaders = repositoryForArticlesContent.allArticlesHeaders
        notepadDataHandlerForContent = NotepadDataHandlerForContent(this, devNotepadApi)
    }

    /**
     * Методы вставки и удаления находятся в данном классе по причине того, что здесь есть
     * доступ к viewModelScope.
     * */
    override fun insertElement(notepadData: NotepadData) =
        viewModelScope.launch(Dispatchers.IO) {
            if (notepadData is ArticleHeader) {
                repositoryForArticlesContent.insertElement(notepadData)
            }
        }

    override fun deleteElement(notepadData: NotepadData) =
        viewModelScope.launch(Dispatchers.IO) {
            if (notepadData is ArticleHeader) {
                repositoryForArticlesContent.deleteElement(notepadData)
            }
        }

    /**
     * Вызывает методы для запроса на сервер у всех классов-обработчиков элементов статьи.
     * */
    override fun makeRequestForElements(parentElementId: Int) {
        notepadDataHandlerForContent.makeRequestForContentData(headerType, parentElementId)
    }
}
