package com.example.checklist_android_app.ui.emptylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.checklist_android_app.data.ChecklistDatabase
import com.example.checklist_android_app.data.ChecklistItem
import com.example.checklist_android_app.data.ChecklistMeta
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EmptyListViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ChecklistDatabase.getDatabase(application)
    private val checklistDao = database.checklistDao()
    private val itemDao = database.itemDao()

    private val _checklistMeta = MutableLiveData<ChecklistMeta>()
    val checklistMeta: LiveData<ChecklistMeta> = _checklistMeta

    private val _checklistItems = MutableLiveData<List<ChecklistItem>>()
    val checklistItems: LiveData<List<ChecklistItem>> = _checklistItems

    fun createNewChecklist(title: String, description: String) {
        viewModelScope.launch {
            val meta = ChecklistMeta(
                title = title,
                description = description,
                createdAt = System.currentTimeMillis()
            )
            val id = checklistDao.insertChecklist(meta)
            meta.id = id
            _checklistMeta.value = meta
            _checklistItems.value = emptyList()
        }
    }

    fun addItem(content: String) {
        viewModelScope.launch {
            val currentItems = _checklistItems.value ?: emptyList()
            val newItem = ChecklistItem(
                content = content,
                position = currentItems.size,
                checklistId = _checklistMeta.value?.id ?: return@launch
            )
            val id = itemDao.insertItem(newItem)
            newItem.id = id
            _checklistItems.value = currentItems + newItem
        }
    }

    fun removeItem(item: ChecklistItem) {
        viewModelScope.launch {
            itemDao.deleteItem(item)
            val currentItems = _checklistItems.value?.filter { it.id != item.id } ?: emptyList()
            _checklistItems.value = currentItems
        }
    }

    fun toggleItemChecked(item: ChecklistItem) {
        viewModelScope.launch {
            itemDao.updateItem(item.copy(isChecked = !item.isChecked))
            val currentItems = _checklistItems.value?.map {
                if (it.id == item.id) it.copy(isChecked = !it.isChecked) else it
            } ?: emptyList()
            _checklistItems.value = currentItems
        }
    }

    fun reorderItems(fromPosition: Int, toPosition: Int) {
        viewModelScope.launch {
            val currentItems = _checklistItems.value ?: return@launch
            val item = currentItems[fromPosition]
            val newItems = currentItems.toMutableList()
            newItems.removeAt(fromPosition)
            newItems.add(toPosition, item)
            
            // Update positions
            newItems.forEachIndexed { index, checklistItem ->
                itemDao.updateItem(checklistItem.copy(position = index))
            }
            
            _checklistItems.value = newItems
        }
    }

    fun loadChecklist(checklistId: Long) {
        viewModelScope.launch {
            val meta = checklistDao.getChecklistById(checklistId)
            val items = itemDao.getItemsForChecklist(checklistId).first()
            _checklistMeta.value = meta
            _checklistItems.value =  items
        }
    }

    fun deleteChecklist() {
        viewModelScope.launch {
            _checklistMeta.value?.let { meta ->
                checklistDao.deleteChecklist(meta)
                itemDao.deleteItemsForChecklist(meta.id)
                _checklistMeta.value = null
                _checklistItems.value = emptyList()
            }
        }
    }
}