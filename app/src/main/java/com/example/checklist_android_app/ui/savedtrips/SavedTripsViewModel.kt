package com.example.checklist_android_app.ui.savedtrips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.checklist_android_app.model.SavedTrip
import com.example.checklist_android_app.model.TripDetails
import com.example.checklist_android_app.model.TripDates

class SavedTripsViewModel : ViewModel() {
    private val _trips = MutableLiveData<List<SavedTrip>>()
    val trips: LiveData<List<SavedTrip>> = _trips

    init {
        loadMockTrips()
    }

    private fun loadMockTrips() {
        _trips.value = listOf(
            SavedTrip(
                id = "1",
                name = "Summer Beach Vacation",
                details = TripDetails(
                    travelType = "Domestic",
                    destinationType = "Beach",
                    duration = 7,
                    travelDates = TripDates(
                        startDate = "2025-07-15",
                        endDate = "2025-07-22"
                    ),
                    travelerGroup = "Family",
                    travelerCategories = listOf("Adults", "Kids"),
                    specialRequirements = listOf("Baby Essentials")
                ),
                createdAt = "2025-06-10",
                isFavorite = true
            ),
            SavedTrip(
                id = "2",
                name = "Business Trip to New York",
                details = TripDetails(
                    travelType = "Domestic",
                    destinationType = "City",
                    duration = 3,
                    travelDates = TripDates(
                        startDate = "2025-08-05",
                        endDate = "2025-08-08"
                    ),
                    travelerGroup = "Solo",
                    travelerCategories = listOf("Adults"),
                    specialRequirements = listOf("Tech Gear")
                ),
                createdAt = "2025-07-25",
                isFavorite = false
            ),
            SavedTrip(
                id = "3",
                name = "European Adventure",
                details = TripDetails(
                    travelType = "International",
                    destinationType = "Multi-City",
                    duration = 14,
                    travelDates = TripDates(
                        startDate = "2025-09-10",
                        endDate = "2025-09-24"
                    ),
                    travelerGroup = "Couple",
                    travelerCategories = listOf("Adults"),
                    specialRequirements = listOf()
                ),
                createdAt = "2025-08-15",
                isFavorite = true
            )
        )
    }
}