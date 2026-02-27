package com.smartcare.ui.records

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.smartcare.data.local.Prescription
import com.smartcare.data.local.PrescriptionDatabase
import kotlinx.coroutines.launch

class HealthRecordsViewModel(application: Application) : AndroidViewModel(application) {

    private val prescriptionDao = PrescriptionDatabase.getDatabase(application).prescriptionDao()

    // LiveData stream of all offline Prescriptions via Room
    val allPrescriptions: LiveData<List<Prescription>> = prescriptionDao.getAllRecords().asLiveData()

    fun insertPrescription(prescription: Prescription) = viewModelScope.launch {
        prescriptionDao.insert(prescription)
    }
}
