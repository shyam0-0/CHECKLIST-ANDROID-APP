package com.example.checklist_android_app.ui.emptylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.checklist_android_app.data.ChecklistItem
import com.example.checklist_android_app.databinding.ItemChecklistBinding

class ChecklistAdapter(
    private val onItemChecked: (ChecklistItem) -> Unit,
    private val onItemDelete: (ChecklistItem) -> Unit
) : ListAdapter<ChecklistItem, ChecklistAdapter.ViewHolder>(ChecklistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChecklistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemChecklistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChecklistItem) {
            binding.apply {
                checkboxItem.isChecked = item.isChecked
                textItem.text = item.content
                
                checkboxItem.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked != item.isChecked) {
                        onItemChecked(item)
                    }
                }
                
                buttonDelete.setOnClickListener {
                    onItemDelete(item)
                }
            }
        }
    }

    private class ChecklistDiffCallback : DiffUtil.ItemCallback<ChecklistItem>() {
        override fun areItemsTheSame(oldItem: ChecklistItem, newItem: ChecklistItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChecklistItem, newItem: ChecklistItem): Boolean {
            return oldItem == newItem
        }
    }
} 