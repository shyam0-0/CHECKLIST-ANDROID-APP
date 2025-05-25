package com.example.checklist_android_app.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.checklist_android_app.R
import com.example.checklist_android_app.databinding.FragmentHelpContactBinding

class HelpContactFragment : Fragment() {
    private var _binding: FragmentHelpContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var helpViewModel: HelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpContactBinding.inflate(inflater, container, false)
        helpViewModel = ViewModelProvider(requireParentFragment()).get(HelpViewModel::class.java)
        setupSubmitButton()
        return binding.root
    }

    private fun setupSubmitButton() {
        binding.submitButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val subject = binding.subjectInput.text.toString()
            val message = binding.messageInput.text.toString()

            if (helpViewModel.submitSupportRequest(name, email, subject, message)) {
                Toast.makeText(context, getString(R.string.message_sent), Toast.LENGTH_SHORT).show()
                clearForm()
            }
        }
    }

    private fun clearForm() {
        binding.nameInput.text?.clear()
        binding.emailInput.text?.clear()
        binding.subjectInput.text?.clear()
        binding.messageInput.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}