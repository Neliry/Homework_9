package com.example.maria.homework_9.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.maria.homework_9.entities.Message

@Dao
interface MessageDao {

    @get:Query("SELECT*FROM message_table ORDER BY id DESC")
    val allMessages: LiveData<List<Message>>

    @Insert
    fun insert(message: Message)

    @Update
    fun update(message: Message)

    @Delete
    fun delete(message: Message)
}