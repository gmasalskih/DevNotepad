package com.example.devnotepad.data.data_handlers

import com.example.devnotepad.NotepadData
import kotlinx.coroutines.Job

interface NotePadViewModelContract {
    fun insertElement(notepadData: NotepadData): Job
    fun deleteElement(notepadData: NotepadData): Job
}