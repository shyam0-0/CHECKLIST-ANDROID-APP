package com.example.checklist_android_app.ui.newtrips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date

class NewTripsViewModel : ViewModel() {
    private val _currentStep = MutableLiveData(1)
    val currentStep: LiveData<Int> = _currentStep

    private val _tripName = MutableLiveData("")
    val tripName: LiveData<String> = _tripName

    private val _travelType = MutableLiveData("Domestic")
    val travelType: LiveData<String> = _travelType

    private val _destinationType = MutableLiveData("City")
    val destinationType: LiveData<String> = _destinationType

    private val _duration = MutableLiveData(3)
    val duration: LiveData<Int> = _duration

    private val _startDate = MutableLiveData<Date?>()
    val startDate: LiveData<Date?> = _startDate

    private val _endDate = MutableLiveData<Date?>()
    val endDate: LiveData<Date?> = _endDate

    private val _travelerGroup = MutableLiveData("Solo")
    val travelerGroup: LiveData<String> = _travelerGroup

    private val _travelerCategories = MutableLiveData<List<String>>(listOf("Adults"))
    val travelerCategories: LiveData<List<String>> = _travelerCategories

    private val _specialRequirements = MutableLiveData<List<String>>(listOf())
    val specialRequirements: LiveData<List<String>> = _specialRequirements
    
    fun updateTripName(name: String) {
        _tripName.value = name
    }

    fun updateTravelType(type: String) {
        _travelType.value = type
    }

    fun updateDestinationType(type: String) {
        _destinationType.value = type
    }

    fun updateDuration(days: Int) {
        _duration.value = days
    }

    fun updateDates(start: Date?, end: Date?) {
        _startDate.value = start
        _endDate.value = end
    }

    fun updateTravelerGroup(group: String) {
        _travelerGroup.value = group
    }

    fun updateTravelerCategories(categories: List<String>) {
        _travelerCategories.value = categories
    }

    fun updateSpecialRequirements(requirements: List<String>) {
        _specialRequirements.value = requirements
    }

    fun nextStep() {
        if (_currentStep.value!! < 3) {
            _currentStep.value = _currentStep.value!! + 1
        }
    }

    fun previousStep() {
        if (_currentStep.value!! > 1) {
            _currentStep.value = _currentStep.value!! - 1
        }
    }
}