package com.smartcare.ui.records

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.smartcare.data.local.AppDatabase
import com.smartcare.data.local.HealthRecord
import com.smartcare.data.repository.RecordRepository
import kotlinx.coroutines.launch

class RecordsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecordRepository
    val allRecords: LiveData<List<HealthRecord>>

    init {
        // Initialize DAO from Room Database
        val recordDao = AppDatabase.getDatabase(application).recordDao()
        repository = RecordRepository(recordDao)

        // Convert Room Flow into LiveData for the UI to observe changes instantly
        allRecords = repository.allRecords.asLiveData()
    }

    // Insert new health record purely into the local offline Room DB
    fun insert(record: HealthRecord) = viewModelScope.launch {
        repository.insert(record)
    }
}
