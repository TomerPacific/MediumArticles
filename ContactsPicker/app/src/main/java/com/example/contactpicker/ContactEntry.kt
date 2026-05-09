package com.example.contactpicker

data class ContactEntry(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)
