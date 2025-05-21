package com.example.checklist_android_app.ui.newtrips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewTripsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is New Trips section"
    }
    val text: LiveData<String> = _text
}