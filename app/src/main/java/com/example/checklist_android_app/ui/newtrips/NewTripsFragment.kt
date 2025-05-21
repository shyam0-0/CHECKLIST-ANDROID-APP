package com.example.checklist_android_app.ui.newtrips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.checklist_android_app.databinding.FragmentNewTripsBinding

class NewTripsFragment : Fragment() {

    private var _binding: FragmentNewTripsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val newTripsViewModel =
            ViewModelProvider(this).get(NewTripsViewModel::class.java)

        _binding = FragmentNewTripsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNewTrips
        newTripsViewModel.text.observe(viewLifecycleOwner) { text ->
            textView.text = text
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}