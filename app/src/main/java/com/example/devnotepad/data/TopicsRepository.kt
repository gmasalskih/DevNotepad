package com.example.devnotepad.data

import androidx.lifecycle.LiveData
import com.example.devnotepad.NotepadData
import com.example.devnotepad.Topic
import com.example.devnotepad.data.local.TopicDao

class TopicsRepository(private val topicDao: TopicDao): NotepadRepositoryContractForStructure {

    /**
     * LiveData список тем для наблюдения из модели фрагмента.
     * */
    val allTopics: LiveData<List<Topic>> = topicDao.getAllTopics()

    /**
     * Синхронное получение списка направлений.
     * */
    override suspend fun getAllElementsSync(): List<NotepadData> {
        return topicDao.getAllTopicsSync()
    }

    /**
     * Вставляет тему в БД с заменой содержимого, если она уже существует.
     * */
    override suspend fun insertElement(notepadData: NotepadData) {
        topicDao.insertTopic(notepadData as Topic)
    }

    /**
     * Удаляет тему из БД.
     * */
    override suspend fun deleteElement(notepadData: NotepadData) {
        topicDao.deleteTopic(notepadData as Topic)
    }
}