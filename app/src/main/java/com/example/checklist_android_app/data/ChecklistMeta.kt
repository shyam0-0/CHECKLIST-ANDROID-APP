package com.example.checklist_android_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklist_meta")
data class ChecklistMeta(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val title: String,
    val description: String,
    val createdAt: Long
) 