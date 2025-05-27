package com.example.checklist_android_app.ui.emptylist

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checklist_android_app.R
import com.example.checklist_android_app.databinding.FragmentEmptyListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class EmptyListFragment : Fragment() {

    private var _binding: FragmentEmptyListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EmptyListViewModel
    private lateinit var adapter: ChecklistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[EmptyListViewModel::class.java]
        _binding = FragmentEmptyListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupObservers()
        setupFab()
        setupSwipeToDelete()

        // If no checklist is loaded, show create dialog
        if (viewModel.checklistMeta.value == null) {
            showCreateChecklistDialog()
        }

        return root
    }

    private fun setupRecyclerView() {
        adapter = ChecklistAdapter(
            onItemChecked = { item -> viewModel.toggleItemChecked(item) },
            onItemDelete = { item -> viewModel.removeItem(item) }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@EmptyListFragment.adapter
        }

        // Setup drag and drop
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun onMove(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                target: androidx.recyclerview.widget.RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                viewModel.reorderItems(fromPos, toPos)
                return true
            }

            override fun onSwiped(
                viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                direction: Int
            ) {
                // Handled by SwipeToDeleteCallback
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setupObservers() {
        viewModel.checklistItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
            binding.emptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.checklistMeta.observe(viewLifecycleOwner) { meta ->
            meta?.let {
                binding.toolbar.title = it.title
                binding.toolbar.subtitle = formatDate(it.createdAt)
            }
        }
    }

    private fun setupFab() {
        binding.fabAddItem.setOnClickListener {
            showAddItemDialog()
        }
    }

    private fun setupSwipeToDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(
                viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.adapterPosition
                val item = adapter.currentList[position]
                viewModel.removeItem(item)
                Snackbar.make(binding.root, "Item removed", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        viewModel.addItem(item.content)
                    }.show()
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerView)
    }

    private fun showCreateChecklistDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_create_checklist, null)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Create New Checklist")
            .setView(dialogBinding)
            .setPositiveButton("Create") { _, _ ->
                val title = dialogBinding.findViewById<EditText>(R.id.editTextTitle).text.toString()
                val description = dialogBinding.findViewById<EditText>(R.id.editTextDescription).text.toString()
                if (title.isNotEmpty()) {
                    viewModel.createNewChecklist(title, description)
                }
            }
            .setNegativeButton("Cancel", null)
            .setCancelable(false)
            .show()
    }

    private fun showAddItemDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_add_item, null)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add New Item")
            .setView(dialogBinding)
            .setPositiveButton("Add") { _, _ ->
                val content = dialogBinding.findViewById<EditText>(R.id.editTextItem).text.toString()
                if (content.isNotEmpty()) {
                    viewModel.addItem(content)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Checklist")
            .setMessage("Are you sure you want to delete this checklist?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteChecklist()
                showCreateChecklistDialog()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_checklist, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                Snackbar.make(binding.root, "Checklist saved", Snackbar.LENGTH_SHORT).show()
                true
            }
            R.id.action_delete -> {
                showDeleteConfirmationDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}