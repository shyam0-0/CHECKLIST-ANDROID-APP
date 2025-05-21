package com.example.checklist_android_app.ui.mychecklists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.checklist_android_app.databinding.FragmentMyChecklistsBinding

class MyCheckListsFragment : Fragment() {

    private var _binding: FragmentMyChecklistsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myCheckListsViewModel =
            ViewModelProvider(this).get(MyCheckListsViewModel::class.java)

        _binding = FragmentMyChecklistsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMyChecklists
        myCheckListsViewModel.text.observe(viewLifecycleOwner) { text ->
            textView.text = text
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} // Added closing brace