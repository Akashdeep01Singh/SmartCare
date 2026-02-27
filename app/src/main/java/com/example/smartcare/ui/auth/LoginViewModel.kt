package com.smartcare.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object OtpSent : AuthState()
    object Verified : AuthState()
    data class Error(val message: String) : AuthState()
}

class LoginViewModel : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.Idle)
    val authState: LiveData<AuthState> = _authState

    // Mock Firebase Phone Auth Flow
    fun sendOtp(phoneNumber: String) {
        if (phoneNumber.isBlank() || phoneNumber.length < 10) {
            _authState.value = AuthState.Error("Please enter a valid phone number")
            return
        }

        _authState.value = AuthState.Loading

        viewModelScope.launch {
            // Simulate network delay for Firebase OTP request
            delay(1500)
            _authState.value = AuthState.OtpSent
        }
    }

    fun verifyOtp(otp: String) {
        if (otp.length != 6) {
            _authState.value = AuthState.Error("OTP must be 6 digits")
            return
        }

        _authState.value = AuthState.Loading

        viewModelScope.launch {
            // Simulate network delay for Firebase OTP verification
            delay(1500)
            // Mock success condition (e.g. 123456 passes)
            if (otp == "123456") {
                _authState.value = AuthState.Verified
            } else {
                _authState.value = AuthState.Error("Invalid OTP. Try 123456.")
                // Reset back to OtpSent so they can try again
                delay(1000)
                _authState.value = AuthState.OtpSent
            }
        }
    }

    fun resetError() {
        if (_authState.value is AuthState.Error) {
            _authState.value = AuthState.Idle
        }
    }
}
