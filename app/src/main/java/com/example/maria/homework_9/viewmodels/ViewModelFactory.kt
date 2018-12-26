package com.example.maria.homework_9.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MessageViewModel::class.java) -> {
            MessageViewModel(application) as T
        }
        else -> throw IllegalArgumentException()
    }
}