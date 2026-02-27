package com.smartcare.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_records")
data class HealthRecord(
    @PrimaryKey
    val id: String,
    val date: Long,
    val doctorName: String,
    val prescriptionText: String,
    val hospitalName: String = "Nabha Civil Hospital",
    val diagnosis: String = "General Checkup" // Added for Dashboard completeness
)
