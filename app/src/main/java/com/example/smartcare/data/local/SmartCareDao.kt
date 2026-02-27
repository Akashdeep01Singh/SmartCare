package com.smartcare.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SmartCareDao {
    // ---- Health Records ----
    @Query("SELECT * FROM health_records WHERE patientId = :patientId ORDER BY timestamp DESC")
    suspend fun getHealthRecords(patientId: String): List<HealthRecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthRecords(vararg records: HealthRecordEntity)

    @Query("SELECT * FROM health_records WHERE isSynced = 0")
    suspend fun getUnsyncedHealthRecords(): List<HealthRecordEntity>

    @Query("UPDATE health_records SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markHealthRecordsSynced(ids: List<String>)

    // ---- Triage Reports ----
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTriageReport(report: TriageReportEntity)

    @Query("SELECT * FROM triage_reports WHERE isSynced = 0")
    suspend fun getUnsyncedTriageReports(): List<TriageReportEntity>

    @Query("UPDATE triage_reports SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markTriageReportsSynced(ids: List<String>)
}
