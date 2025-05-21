package com.example.checklist_android_app.ui.savedtrips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedTripsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Saved Trips section"
    }
    val text: LiveData<String> = _text
}