package com.example.contactpicker

data class ContactEntry(
    val id: String,
    val name: String,
    val phoneNumber: String? = null,
    val isSelected: Boolean = false
)
