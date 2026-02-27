package com.smartcare.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    // Returns a Flow so the UI automatically updates when the DB changes
    @Query("SELECT * FROM health_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<HealthRecord>>

    @Query("SELECT * FROM health_records WHERE id = :recordId LIMIT 1")
    suspend fun getRecordById(recordId: String): HealthRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: HealthRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<HealthRecord>)
}
