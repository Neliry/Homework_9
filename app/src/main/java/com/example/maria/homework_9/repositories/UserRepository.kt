package com.example.maria.homework_9.repositories

import android.os.AsyncTask
import com.example.maria.homework_9.databases.ChatDatabase
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.maria.homework_9.daos.UserDao
import com.example.maria.homework_9.entities.User


class UserRepository(application: Application) {
    private val userDao: UserDao
    private val allUsers: LiveData<List<User>>

    init {
        val database = ChatDatabase.getInstance(application)
        userDao = database.userDao()
        allUsers = userDao.allUsers
    }

    fun insert(user: User) {
        InsertNoteAsyncTask(userDao).execute(user)
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }

    private class InsertNoteAsyncTask internal constructor(private val userDao: UserDao) : AsyncTask<User, Void, Void>() {

        override fun doInBackground(vararg users: User): Void? {
            userDao.insert(users[0])
            return null
        }
    }
}