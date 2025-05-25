package com.example.checklist_android_app.ui.help

import androidx.lifecycle.ViewModel

class HelpViewModel : ViewModel() {
    val faqs = listOf(
        FAQ(
            "How do I create a new trip checklist?",
            "To create a new trip checklist, navigate to the 'New Trip' section and fill out the travel questionnaire with details about your trip. The app will automatically generate a customized packing list based on your responses."
        ),
        FAQ(
            "Can I edit my checklist after it's generated?",
            "Yes! After your checklist is generated, you can add, remove, or modify items. Simply tap on an item to mark it as packed, or use the edit buttons to make changes to the list."
        ),
        FAQ(
            "How do I save my checklist for future trips?",
            "After generating your checklist, tap the 'Save' button at the bottom of the screen. You can give your checklist a name and it will be stored in the 'Saved Trips' section for future reference."
        ),
        FAQ(
            "Can I share my checklist with travel companions?",
            "Yes! Once you've created a checklist, use the share button to send your checklist via email, messaging apps, or other sharing options available on your device."
        ),
        FAQ(
            "Does the app work offline?",
            "Yes, the basic features of the app work offline. You can create, view, and edit checklists without an internet connection. Some features like cloud sync require an internet connection."
        )
    )

    data class FAQ(
        val question: String,
        val answer: String
    )

    fun submitSupportRequest(name: String, email: String, subject: String, message: String): Boolean {
        // Implement support request submission logic here
        return true
    }
}