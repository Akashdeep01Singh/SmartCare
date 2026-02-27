package com.smartcare.ui.telemedicine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartcare.data.local.Doctor

class TelemedicineViewModel : ViewModel() {

    private val _selectedDoctor = MutableLiveData<Doctor?>()
    val selectedDoctor: LiveData<Doctor?> = _selectedDoctor

    private val _selectedDateStr = MutableLiveData<String>("")
    val selectedDateStr: LiveData<String> = _selectedDateStr

    private val _selectedTimeStr = MutableLiveData<String>("")
    val selectedTimeStr: LiveData<String> = _selectedTimeStr

    private val _isLowBandwidth = MutableLiveData<Boolean>(true)
    val isLowBandwidth: LiveData<Boolean> = _isLowBandwidth

    fun selectDoctor(doctor: Doctor) {
        _selectedDoctor.value = doctor
    }

    fun setDate(date: String) {
        _selectedDateStr.value = date
    }

    fun setTime(time: String) {
        _selectedTimeStr.value = time
    }

    fun setLowBandwidthMode(enabled: Boolean) {
        _isLowBandwidth.value = enabled
    }
}
