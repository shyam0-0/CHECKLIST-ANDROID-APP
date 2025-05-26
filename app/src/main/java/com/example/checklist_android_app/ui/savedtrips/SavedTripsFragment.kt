package com.example.checklist_android_app.ui.savedtrips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider  // Add this import
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checklist_android_app.R
import com.example.checklist_android_app.databinding.FragmentSavedTripsBinding
import com.example.checklist_android_app.model.SavedTrip
import com.example.checklist_android_app.model.TripDetails
import com.example.checklist_android_app.model.TripDates
import com.example.checklist_android_app.ui.savedtrips.adapter.SavedTripsAdapter

class SavedTripsFragment : Fragment() {
    private var _binding: FragmentSavedTripsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SavedTripsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedTripsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SavedTripsViewModel::class.java]
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.tripsRecyclerView.layoutManager = LinearLayoutManager(context)
        
        viewModel.trips.observe(viewLifecycleOwner) { trips ->
            binding.tripsRecyclerView.adapter = SavedTripsAdapter(trips) { tripId ->
                findNavController().navigate(R.id.action_nav_savedtrips_to_newtrip)
            }
            updateEmptyState(trips.isEmpty())
        }

        binding.searchInput.addTextChangedListener { text ->
            (binding.tripsRecyclerView.adapter as? SavedTripsAdapter)?.filter(text.toString())
        }

        binding.createTripButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_savedtrips_to_newtrip)
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.apply {
            tripsRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
            emptyStateLayout.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}