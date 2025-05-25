package com.example.checklist_android_app.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    private val _notifications = MutableLiveData(true)
    val notifications: LiveData<Boolean> = _notifications

    private val _locationServices = MutableLiveData(false)
    val locationServices: LiveData<Boolean> = _locationServices

    private val _autoBackup = MutableLiveData(true)
    val autoBackup: LiveData<Boolean> = _autoBackup

    private val _dataSync = MutableLiveData(false)
    val dataSync: LiveData<Boolean> = _dataSync

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    fun setNotifications(enabled: Boolean) {
        _notifications.value = enabled
    }

    fun setLocationServices(enabled: Boolean) {
        _locationServices.value = enabled
    }

    fun setAutoBackup(enabled: Boolean) {
        _autoBackup.value = enabled
    }

    fun setDataSync(enabled: Boolean) {
        _dataSync.value = enabled
    }

    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
    }

    fun saveSettings() {
        // TODO: Implement settings persistence
        // This would typically involve saving to SharedPreferences or a database
    }
}