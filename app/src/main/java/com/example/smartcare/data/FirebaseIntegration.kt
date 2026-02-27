package com.smartcare.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class SmartCareRepository {

    private val firestore: FirebaseFirestore by lazy {
        val db = FirebaseFirestore.getInstance()
        // 1. Enable Offline Persistence for Offline-First usage
        // This ensures Digital Health Records (DHR) are accessible without 4G
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()
        db.firestoreSettings = settings
        db
    }

    private val realtimeDb: FirebaseDatabase by lazy {
        val db = FirebaseDatabase.getInstance()
        // Enable disk persistence for highly dynamic data
        db.setPersistenceEnabled(true)
        db
    }

    /**
     * Fetch Patient Health Records (DHR).
     * Because persistence is enabled, this will return cached data immediately
     * if the user has no internet connection, fulfilling the Offline-First rule.
     */
    suspend fun getDigitalHealthRecords(patientId: String): List<HealthRecord> {
        return try {
            val snapshot = firestore.collection("patients")
                .document(patientId)
                .collection("records")
                .get()
                .await() // Uses Kotlin Coroutines with Firebase

            snapshot.documents.mapNotNull { doc ->
                // Data mapping (Decryption would be intercept here in a real scenario)
                doc.toObject(HealthRecord::class.java)
            }
        } catch (e: Exception) {
            // Handle error, potentially falling back to SQLite room DB if needed out-of-band
            emptyList()
        }
    }

    /**
     * Submit AI Symptom Checker results.
     * Uses 'Sync when Online'. If the user is offline, Firestore batches this locally
     * and syncs to Nabha Civil Hospital's queue automatically when 4G resumes.
     */
    fun submitTriageReport(patientId: String, aiReport: AiSymptomReport) {
        // We encrypt PII locally before sending to Firestore for HIPAA compliance
        val encryptedReport = HIPAAComplianceEngine.encrypt(aiReport)

        firestore.collection("triage_queue")
            .document(patientId)
            .set(encryptedReport)
        // Fire-and-forget; Firestore background sync takes over
    }

    /**
     * Doctor Availability Matrix & Pharmacy Tracker
     * Uses Realtime Database for quick, low-latency live changes.
     */
    fun subscribeToDoctorAvailability(hospitalId: String, onUpdate: (DoctorAvailability) -> Unit) {
        val ref = realtimeDb.getReference("hospitals/$hospitalId/availability")

        // Listeners persist even when offline (providing last known state),
        // and instantly sync upon reconnection.
        ref.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val data = snapshot.getValue(DoctorAvailability::class.java)
                if (data != null) {
                    onUpdate(data)
                }
            }
            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                // Handle cancellation
            }
        })
    }
}

// Data Models
data class HealthRecord(val id: String = "", val diagnosis: String = "", val date: Long = 0L)
data class AiSymptomReport(val symptoms: String, val urgency: Int, val timestamp: Long)
data class DoctorAvailability(val totalDoctors: Int = 0, val available: Int = 0, val emergencyQueue: Int = 0)

// Dummy encryption wrapper to represent local E2EE
object HIPAAComplianceEngine {
    fun encrypt(data: Any): Any = data // In production, implement KeyStore + AES GCM Encryption
}
