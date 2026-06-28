package com.notesapp.domain.model

data class Folder(
    val id: Long = 0,
    val name: String,
    val parentId: Long? = null,
    val color: Long = 0xFF8B5CF6,
    val icon: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
