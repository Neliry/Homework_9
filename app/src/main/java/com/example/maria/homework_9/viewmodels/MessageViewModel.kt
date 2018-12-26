package com.example.maria.homework_9.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.maria.homework_9.entities.Message
import com.example.maria.homework_9.repositories.MessageRepository


class MessageViewModel(application: Application) : BaseViewModel(application) {

    private val repository: MessageRepository = MessageRepository(application)
    private var allMessages: LiveData<List<Message>>

    init {
        allMessages = repository.getAllMessages()
    }

    fun insert(note: Message) {
        repository.insert(note)
    }

    fun update(note: Message) {
        repository.update(note)
    }

    fun delete(note: Message) {
        repository.delete(note)
    }

    fun getAllMessages(): LiveData<List<Message>> {
        return allMessages
    }
}