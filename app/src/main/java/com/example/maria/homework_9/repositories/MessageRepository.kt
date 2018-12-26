package com.example.maria.homework_9.repositories

import com.example.maria.homework_9.daos.MessageDao
import android.os.AsyncTask
import com.example.maria.homework_9.databases.ChatDatabase
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.maria.homework_9.entities.Message


class MessageRepository(application: Application){
    private val messageDao: MessageDao
    private val allMessages: LiveData<List<Message>>

    init {
        val database = ChatDatabase.getInstance(application)
        messageDao = database.messageDao()
        allMessages = messageDao.allMessages
    }

    fun insert(message: Message) {
        InsertMesageAsyncTask(messageDao).execute(message)
    }

    fun update(message: Message) {
        UpdateMesageAsyncTask(messageDao).execute(message)
    }

    fun delete(message: Message) {
        DeleteMesageAsyncTask(messageDao).execute(message)
    }

    fun getAllMessages(): LiveData<List<Message>> {
        return allMessages
    }

    private class InsertMesageAsyncTask internal constructor(private val noteDao: MessageDao) : AsyncTask<Message, Void, Void>() {

        override fun doInBackground(vararg messages: Message): Void? {
            noteDao.insert(messages[0])
            return null
        }
    }

    private class UpdateMesageAsyncTask internal constructor(private val noteDao: MessageDao) : AsyncTask<Message, Void, Void>() {

        override fun doInBackground(vararg messages: Message): Void? {
            noteDao.update(messages[0])
            return null
        }
    }

    private class DeleteMesageAsyncTask internal constructor(private val noteDao: MessageDao) : AsyncTask<Message, Void, Void>() {

        override fun doInBackground(vararg messages: Message): Void? {
            noteDao.delete(messages[0])
            return null
        }
    }
}