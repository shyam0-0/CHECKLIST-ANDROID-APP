package com.example.checklist_android_app.ui.newtrips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.checklist_android_app.R
import com.example.checklist_android_app.databinding.FragmentNewTripsBinding
import com.example.checklist_android_app.databinding.FragmentNewTrips1Binding
import com.example.checklist_android_app.databinding.FragmentNewTrips2Binding
import com.example.checklist_android_app.databinding.FragmentNewTrips3Binding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.chip.ChipGroup
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.*

class NewTripsFragment : Fragment() {
    private var _binding: FragmentNewTripsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewTripsViewModel
    private var currentStep = 1

    // Lazy initialization of step bindings
    private var _step1Binding: FragmentNewTrips1Binding? = null
    private var _step2Binding: FragmentNewTrips2Binding? = null
    private var _step3Binding: FragmentNewTrips3Binding? = null
    
    private val step1Binding get() = _step1Binding!!
    private val step2Binding get() = _step2Binding!!
    private val step3Binding get() = _step3Binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewTripsBinding.inflate(inflater, container, false)
        _step1Binding = FragmentNewTrips1Binding.inflate(inflater, container, false)
        _step2Binding = FragmentNewTrips2Binding.inflate(inflater, container, false)
        _step3Binding = FragmentNewTrips3Binding.inflate(inflater, container, false)
        
        viewModel = ViewModelProvider(this)[NewTripsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setupStepViews()
        updateStepUI()
        setupStepNavigationButtons()
        setupViewsForCurrentStep()
    }

    private fun setupStepViews() {
        binding.stepViewFlipper.apply {
            addView(step1Binding.root)
            addView(step2Binding.root)
            addView(step3Binding.root)
            displayedChild = currentStep - 1
        }
    }

    private fun updateStepUI() {
        binding.apply {
            stepText.text = getString(R.string.step_format, currentStep)
            stepProgress.progress = ((currentStep.toFloat() / 3) * 100).toInt()
            nextButton.text = if (currentStep == 3) {
                getString(R.string.button_generate)
            } else {
                getString(R.string.button_next)
            }
        }
    }

    private fun setupStepNavigationButtons() {
        binding.apply {
            previousButton.setOnClickListener {
                if (currentStep > 1) {
                    currentStep--
                    updateStepUI()
                    stepViewFlipper.showPrevious()
                    setupViewsForCurrentStep()
                } else {
                    findNavController().navigateUp()
                }
            }

            nextButton.setOnClickListener {
                if (currentStep < 3) {
                    currentStep++
                    updateStepUI()
                    stepViewFlipper.showNext()
                    setupViewsForCurrentStep()
                } else {
                    saveTrip()
                }
            }
        }
    }

    private fun setupViewsForCurrentStep() {
        when (currentStep) {
            1 -> setupStep1Views()
            2 -> setupStep2Views()
            3 -> setupStep3Views()
        }
    }

    private fun setupStep1Views() {
        step1Binding.apply {
            travelTypeGroup.setOnCheckedChangeListener { group, checkedId ->
                group.findViewById<Chip>(checkedId)?.let { 
                    viewModel.updateTravelType(it.text.toString())
                }
            }

            destinationTypeChips.setOnCheckedChangeListener { group, checkedId ->
                group.findViewById<Chip>(checkedId)?.let { 
                    viewModel.updateDestinationType(it.text.toString())
                }
            }
        }
    }

    private fun setupStep2Views() {
        step2Binding.apply {
            datePickerButton.setOnClickListener {
                showDatePicker()
            }
            travelGroupChips.setOnCheckedChangeListener { group, checkedId ->
                group.findViewById<Chip>(checkedId)?.let { 
                    viewModel.updateTravelerGroup(it.text.toString())
                }
            }
        }
    }

    private fun setupStep3Views() {
        step3Binding.apply {
            setupMultiSelectChipGroup(travelerCategoriesChips) {
                viewModel.updateTravelerCategories(it)
            }
            setupMultiSelectChipGroup(specialRequirementsChips) {
                viewModel.updateSpecialRequirements(it)
            }
        }
    }

    private fun showDatePicker() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(getString(R.string.select_dates))
            .build()

        dateRangePicker.addOnPositiveButtonClickListener { dateRange ->
            updateSelectedDates(dateRange)
        }
        dateRangePicker.show(parentFragmentManager, "date_picker")
    }

    private fun updateSelectedDates(dateRange: androidx.core.util.Pair<Long, Long>) {
        val startDate = Date(dateRange.first)
        val endDate = Date(dateRange.second)
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        step2Binding.dateRangeText.text = 
            "${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}"
        viewModel.updateDates(startDate, endDate)
    }

    private fun setupMultiSelectChipGroup(chipGroup: ChipGroup, onSelectionChanged: (List<String>) -> Unit) {
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChips = checkedIds.map { id ->
                group.findViewById<Chip>(id).text.toString()
            }
            onSelectionChanged(selectedChips)
        }
    }

    private fun saveTrip() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}