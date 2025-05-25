package com.example.checklist_android_app.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.checklist_android_app.R
import com.example.checklist_android_app.databinding.FragmentHelpFaqBinding

class HelpFaqFragment : Fragment() {
    private var _binding: FragmentHelpFaqBinding? = null
    private val binding get() = _binding!!
    private lateinit var helpViewModel: HelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpFaqBinding.inflate(inflater, container, false)
        helpViewModel = ViewModelProvider(requireParentFragment()).get(HelpViewModel::class.java)
        setupFaqs()
        return binding.root
    }

    private fun setupFaqs() {
        helpViewModel.faqs.forEach { faq ->
            val faqView = layoutInflater.inflate(R.layout.fragment_item_faq, binding.faqContainer, false)
            faqView.findViewById<TextView>(R.id.questionText).text = faq.question
            faqView.findViewById<TextView>(R.id.answerText).text = faq.answer
            binding.faqContainer.addView(faqView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}