package com.smartcare.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_records")
data class HealthRecordEntity(
    @PrimaryKey
    val id: String,
    val patientId: String,
    val diagnosis: String,
    val symptoms: String,
    val prescription: String,
    val doctorId: String,
    val timestamp: Long,
    val isSynced: Boolean = false // Crucial for 'Sync when Online' protocol
)

@Entity(tableName = "triage_reports")
data class TriageReportEntity(
    @PrimaryKey
    val id: String,
    val patientId: String,
    val symptomsText: String,
    val aiCalculatedUrgency: Int,
    val timestamp: Long,
    val isSynced: Boolean = false
)
