package com.example.checklist_android_app.ui.savedtrips.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.checklist_android_app.databinding.ItemSavedTripBinding
import com.example.checklist_android_app.model.SavedTrip

class SavedTripsAdapter(
    private val trips: List<SavedTrip>,
    private val onTripSelected: (String) -> Unit
) : RecyclerView.Adapter<SavedTripsAdapter.TripViewHolder>() {

    private var filteredTrips = trips.toList()

    class TripViewHolder(val binding: ItemSavedTripBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val binding = ItemSavedTripBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = filteredTrips[position]
        holder.binding.apply {
            tripName.text = trip.name
            tripDetails.text = "${trip.details.travelType} - ${trip.details.duration} days"
            root.setOnClickListener { onTripSelected(trip.id) }
        }
    }

    override fun getItemCount() = filteredTrips.size

    fun filter(query: String) {
        filteredTrips = if (query.isEmpty()) {
            trips
        } else {
            trips.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}