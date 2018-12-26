package com.example.maria.homework_9.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
class Message(
        @field:ColumnInfo(name = "user_id")
        val userId: Int,

        @field:ColumnInfo(name = "text")
        var text: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}