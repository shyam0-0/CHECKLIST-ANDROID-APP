package com.example.checklist_android_app.model

data class SavedTrip(
    val id: String,
    val name: String,
    val details: TripDetails,
    val createdAt: String,
    val isFavorite: Boolean
)

data class TripDetails(
    val travelType: String,
    val destinationType: String,
    val duration: Int,
    val travelDates: TripDates,
    val travelerGroup: String,
    val travelerCategories: List<String>,
    val specialRequirements: List<String>
)

data class TripDates(
    val startDate: String,
    val endDate: String
)