package com.example.checklist_android_app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "checklist_items",
    foreignKeys = [
        ForeignKey(
            entity = ChecklistMeta::class,
            parentColumns = ["id"],
            childColumns = ["checklistId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("checklistId")]
)
data class ChecklistItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val content: String,
    var isChecked: Boolean = false,
    var position: Int = 0,
    val checklistId: Long
) 