package com.example.devnotepad.data.repositories

import androidx.lifecycle.LiveData
import com.example.devnotepad.ArticleCodeSnippet
import com.example.devnotepad.NotepadData
import com.example.devnotepad.data.local.ArticleCodeSnippetDao

/**
 * Репозиторий обрабатывает запросы к содержимому статей.
 * */
class ArticlesCodeSnippetsRepository(
    private val articleCodeSnippetDao: ArticleCodeSnippetDao
) : RepositoryContractForArticlesContent {

    val allArticlesCodeSnippets: LiveData<List<ArticleCodeSnippet>> =
        articleCodeSnippetDao.getArticleCodeSnippets()

    /**
     * Синхронное получение списка параграфов.
     * */
    override suspend fun getAllElementsSync(parentIdFromServer: Int): List<NotepadData> {
        return articleCodeSnippetDao.getArticleCodeSnippetsSync(parentIdFromServer)
    }

    /**
     * Вставляет параграф в БД с заменой содержимого, если он уже существует.
     * */
    override suspend fun insertElement(notepadData: NotepadData) {
        articleCodeSnippetDao.insertArticleCodeSnippet(notepadData as ArticleCodeSnippet)
    }

    /**
     * Удаляет параграф из БД.
     * */
    override suspend fun deleteElement(notepadData: NotepadData) {
        articleCodeSnippetDao.deleteArticleCodeSnippet(notepadData as ArticleCodeSnippet)
    }

    /**
     * Обновляет поле webViewHeight.
     * */
    suspend fun updateWebViewHeight(webViewHeight: Int, articleCodeSnippetId: Int) {
        articleCodeSnippetDao.updateWebViewHeight(webViewHeight, articleCodeSnippetId)
    }

    /**
     * Получает значение webViewHeight.
     * */
    suspend fun getWebViewHeight(articleCodeSnippetId: Int): Int {
        return articleCodeSnippetDao.getWebViewHeight(articleCodeSnippetId)
    }
}