package com.smartcare.ui.symptoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val isIncoming: Boolean // true = AI, false = User
)

class SymptomCheckerViewModel : ViewModel() {

    private val _chatMessages = MutableLiveData<List<ChatMessage>>(emptyList())
    val chatMessages: LiveData<List<ChatMessage>> = _chatMessages

    init {
        // Initial Bot Greeting
        addMessage(ChatMessage(
            text = "Hello! Please select a symptom from the list below so I can assess your risk level.\n\nਸਤਿ ਸ੍ਰੀ ਅਕਾਲ! ਕਿਰਪਾ ਕਰਕੇ ਹੇਠਾਂ ਦਿੱਤੀ ਸੂਚੀ ਵਿੱਚੋਂ ਆਪਣੀ ਸਮੱਸਿਆ ਚੁਣੋ।",
            isIncoming = true
        ))
    }

    private fun addMessage(message: ChatMessage) {
        val currentList = _chatMessages.value.orEmpty().toMutableList()
        currentList.add(message)
        _chatMessages.postValue(currentList)
    }

    fun submitSymptom(symptomEn: String) {
        // 1. Add User's selected symptom to chat
        addMessage(ChatMessage(text = symptomEn, isIncoming = false))

        // 2. Simulate AI Processing delay
        viewModelScope.launch {
            delay(1000)
            val triageResult = calculateTriage(symptomEn)
            addMessage(ChatMessage(text = triageResult, isIncoming = true))
        }
    }

    private fun calculateTriage(symptom: String): String {
        return when (symptom.lowercase()) {
            "chest pain", "ਛਾਤੀ ਵਿੱਚ ਦਰਦ", "difficulty breathing", "ਸਾਹ ਲੈਣ ਵਿੱਚ ਦਿੱਕਤ" -> {
                "🚨 HIGH RISK (ਖਤਰਨਾਕ):\nPlease visit the ER at Nabha Civil Hospital immediately.\nਕਿਰਪਾ ਕਰਕੇ ਤੁਰੰਤ ਨਾਭਾ ਸਿਵਲ ਹਸਪਤਾਲ ਦੀ ਐਮਰਜੈਂਸੀ ਵਿੱਚ ਜਾਓ।"
            }
            "high fever", "ਤੇਜ਼ ਬੁਖਾਰ", "severe abdominal pain", "ਢਿੱਡ ਵਿੱਚ ਤੇਜ਼ ਦਰਦ" -> {
                "⚠️ MEDIUM RISK (ਦਰਮਿਆਨਾ ਖਤਰਾ):\nYou should consult a doctor via Telemedicine today.\nਤੁਹਾਨੂੰ ਅੱਜ ਡਾਕਟਰ ਨਾਲ ਸਲਾਹ ਕਰਨੀ ਚਾਹੀਦੀ ਹੈ।"
            }
            "mild headache", "ਹਲਕਾ ਸਿਰਦਰਦ", "cough", "ਖਾਂਸੀ", "cold", "ਜ਼ੁਕਾਮ" -> {
                "✅ LOW RISK (ਘੱਟ ਖਤਰਾ):\nYou can rest at home. Search for Paracetamol in the Pharmacy tab.\nਤੁਸੀਂ ਘਰ ਆਰਾਮ ਕਰ ਸਕਦੇ ਹੋ। ਫਾਰਮੇਸੀ ਵਿੱਚ ਦਵਾਈ ਲੱਭੋ।"
            }
            else -> {
                "Please describe that differently. Select an option from the menu.\nਕਿਰਪਾ ਕਰਕੇ ਦੁਬਾਰਾ ਚੁਣੋ।"
            }
        }
    }
}
