package com.example.checklist_android_app.ui.savedtrips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.checklist_android_app.databinding.FragmentSavedTripsBinding

class SavedTripsFragment : Fragment() {

    private var _binding: FragmentSavedTripsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val SavedTripsViewModel =
            ViewModelProvider(this).get(SavedTripsViewModel::class.java)

        _binding = FragmentSavedTripsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSavedTrips
        SavedTripsViewModel.text.observe(viewLifecycleOwner) { text ->
            textView.text = text
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}