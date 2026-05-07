package com.smartcare.ui.symptoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SymptomCheckerViewModel : ViewModel() {

    private val _triageResult = MutableLiveData<String?>()
    val triageResult: LiveData<String?> = _triageResult

    fun analyzeSymptoms(symptoms: List<String>) {
        val symptomsLower = symptoms.map { it.lowercase() }

        viewModelScope.launch {
            // Simple triage based on conditions matching requirements
            var hasFever = false
            var hasCough = false
            var hasBreathingDifficulty = false
            var hasSevereChestPain = false

            symptomsLower.forEach {
                if (it.contains("fever") || it.contains("ਬੁਖਾਰ")) hasFever = true
                if (it.contains("cough") || it.contains("ਖਾਂਸੀ")) hasCough = true
                if (it.contains("breathing") || it.contains("ਸਾਹ")) hasBreathingDifficulty = true
                if (it.contains("chest pain") || it.contains("ਛਾਤੀ") || it.contains("severe") || it.contains("ਤੇਜ਼")) hasSevereChestPain = true
            }

            val resultMessage = if (hasBreathingDifficulty || hasSevereChestPain) {
                "🚨 HIGH RISK (Red Triage):\nPlease visit the ER at Nabha Civil Hospital immediately.\nਕਿਰਪਾ ਕਰਕੇ ਤੁਰੰਤ ਨਾਭਾ ਸਿਵਲ ਹਸਪਤਾਲ ਦੀ ਐਮਰਜੈਂਸੀ ਵਿੱਚ ਜਾਓ।"
            } else if (hasFever && hasCough) {
                "⚠️ MEDIUM RISK (Yellow Triage):\nYou should consult a doctor via Telemedicine today.\nਤੁਹਾਨੂੰ ਅੱਜ ਡਾਕਟਰ ਨਾਲ ਸਲਾਹ ਕਰਨੀ ਚਾਹੀਦੀ ਹੈ।"
            } else {
                "✅ LOW RISK (Green Triage):\nYou can rest at home. Monitor your symptoms.\nਤੁਸੀਂ ਘਰ ਆਰਾਮ ਕਰ ਸਕਦੇ ਹੋ। ਆਪਣੇ ਲੱਛਣਾਂ 'ਤੇ ਨਜ਼ਰ ਰੱਖੋ।"
            }

            _triageResult.postValue(resultMessage)
        }
    }

    fun onTriageResultShown() {
        _triageResult.value = null
    }
}
