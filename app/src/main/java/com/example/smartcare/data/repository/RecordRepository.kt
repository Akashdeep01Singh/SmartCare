package com.smartcare.data.repository

import com.smartcare.data.local.HealthRecord
import com.smartcare.data.local.RecordDao
import kotlinx.coroutines.flow.Flow

class RecordRepository(private val recordDao: RecordDao) {

    // Offline-First Principle: The UI always observes the local Room DB directly.
    val allRecords: Flow<List<HealthRecord>> = recordDao.getAllRecords()

    suspend fun insert(record: HealthRecord) {
        recordDao.insertRecord(record)
    }

    /**
     * In a full implementation, this function would:
     * 1. Check network connectivity.
     * 2. If online, fetch from Firebase.
     * 3. Save fetched data to Room via `recordDao.insertAll()`.
     * 4. Because `allRecords` is a Flow, the UI instantly updates.
     * If offline, it simply does nothing and the user continues to see
     * the cached Room database.
     */
    suspend fun syncRecordsFromRemote(patientId: String) {
        // Placeholder for Firebase Sync Login
    }
}
