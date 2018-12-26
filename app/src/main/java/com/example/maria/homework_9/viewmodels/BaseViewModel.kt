package com.example.maria.homework_9.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    @Suppress("MemberVisibilityCanBePrivate")

    protected val _showProgressBar = MutableLiveData<Boolean>()

    val showProgressBar: LiveData<Boolean> = _showProgressBar
}