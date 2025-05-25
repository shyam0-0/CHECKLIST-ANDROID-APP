package com.example.checklist_android_app.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.checklist_android_app.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.notifications.observe(viewLifecycleOwner) { isEnabled ->
            binding.switchNotifications.isChecked = isEnabled
        }

        viewModel.locationServices.observe(viewLifecycleOwner) { isEnabled ->
            binding.switchLocation.isChecked = isEnabled
        }

        viewModel.autoBackup.observe(viewLifecycleOwner) { isEnabled ->
            binding.switchAutoBackup.isChecked = isEnabled
        }

        viewModel.dataSync.observe(viewLifecycleOwner) { isEnabled ->
            binding.switchDataSync.isChecked = isEnabled
        }

        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isEnabled ->
            binding.switchDarkTheme.isChecked = isEnabled
        }
    }

    private fun setupListeners() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotifications(isChecked)
        }

        binding.switchLocation.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setLocationServices(isChecked)
        }

        binding.switchAutoBackup.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setAutoBackup(isChecked)
        }

        binding.switchDataSync.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDataSync(isChecked)
        }

        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkTheme(isChecked)
        }

        binding.buttonUpgrade.setOnClickListener {
            showSnackbar("Premium features coming soon!")
        }

        binding.buttonExportData.setOnClickListener {
            showSnackbar("Export functionality coming soon!")
        }

        binding.buttonDeleteAccount.setOnClickListener {
            showSnackbar("Account deletion coming soon!")
        }

        binding.buttonSaveSettings.setOnClickListener {
            viewModel.saveSettings()
            showSnackbar("Settings saved successfully!")
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}