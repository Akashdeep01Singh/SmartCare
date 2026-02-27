package com.smartcare.ui.inventory

data class MedicineItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val isAvailable: Boolean
)
