package com.example.checklist_android_app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {
    @Insert
    suspend fun insertChecklist(checklist: ChecklistMeta): Long

    @Query("SELECT * FROM checklist_meta WHERE id = :id")
    suspend fun getChecklistById(id: Long): ChecklistMeta

    @Query("SELECT * FROM checklist_meta ORDER BY createdAt DESC")
    fun getAllChecklists(): Flow<List<ChecklistMeta>>

    @Delete
    suspend fun deleteChecklist(checklist: ChecklistMeta)
}

@Dao
interface ItemDao {
    @Insert
    suspend fun insertItem(item: ChecklistItem): Long

    @Update
    suspend fun updateItem(item: ChecklistItem)

    @Query("SELECT * FROM checklist_items WHERE checklistId = :checklistId ORDER BY position ASC")
    fun getItemsForChecklist(checklistId: Long): Flow<List<ChecklistItem>>

    @Delete
    suspend fun deleteItem(item: ChecklistItem)

    @Query("DELETE FROM checklist_items WHERE checklistId = :checklistId")
    suspend fun deleteItemsForChecklist(checklistId: Long)
} 