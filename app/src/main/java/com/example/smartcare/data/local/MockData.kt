package com.smartcare.data.local

data class Doctor(
    val id: String,
    val name: String,
    val specialty: String,
    val isAvailable: Boolean
)

object MockData {
    val doctors = listOf(
        Doctor("1", "Dr. A. Singh", "General Physician", true),
        Doctor("2", "Dr. R. Kaur", "Pediatrician", true),
        Doctor("3", "Dr. M. Sharma", "Cardiologist", false),
        Doctor("4", "Dr. S. Gill", "Orthopedic", true),
        Doctor("5", "Dr. H. Sandhu", "Dermatologist", true)
    )
}
