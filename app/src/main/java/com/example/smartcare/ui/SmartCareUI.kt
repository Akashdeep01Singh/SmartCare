package com.smartcare.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- 1. Visual Identity & Theme ---
val ClinicalWhite = Color(0xFFFFFFFF)
val TrustworthyTeal = Color(0xFF008080)
val SoftGold = Color(0xFFFFD700)
val DarkText = Color(0xFF1E1E1E)
val LightBackground = Color(0xFFF5F7F8)

@Composable
fun SmartCareTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = TrustworthyTeal,
        secondary = SoftGold,
        background = LightBackground,
        surface = ClinicalWhite,
        onPrimary = ClinicalWhite,
        onSurface = DarkText
    )
    MaterialTheme(colorScheme = colors) { content() }
}

// --- 2. Offline Connectivity Banner ---
@Composable
fun OfflineBanner(lastUpdated: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF3CD))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Warning, contentDescription = "Offline", tint = Color(0xFF856404))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Cached Data (Offline) - Last updated: $lastUpdated",
            color = Color(0xFF856404),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// --- 3. Home Screen & AI Symptom Checker ---
@Composable
fun SmartCareApp(isOffline: Boolean = true, lastUpdated: String = "10 mins ago") {
    SmartCareTheme {
        Scaffold(
            bottomBar = { AppBottomNavigation() },
            containerColor = LightBackground
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isOffline) {
                    OfflineBanner(lastUpdated = lastUpdated)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Header
                Text(
                    text = "ਸਤਿ ਸ੍ਰੀ ਅਕਾਲ (Welcome)",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = TrustworthyTeal,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Symptom Checker Card
                SymptomCheckerCard()

                Spacer(modifier = Modifier.height(16.dp))

                // Doctor Availability Matrix
                DoctorAvailabilityCard()
            }
        }
    }
}

@Composable
fun SymptomCheckerCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = ClinicalWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "AI Symptom Checker",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TrustworthyTeal
            )
            Text(
                "Tap the microphone and describe how you feel in Punjabi or Hindi.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            Button(
                onClick = { /* Launch Voice Recorder */ },
                colors = ButtonDefaults.buttonColors(containerColor = SoftGold),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), // Large touch target
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.Mic,
                    contentDescription = "Microphone",
                    tint = DarkText,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Speak Symptoms (ਬੋਲੋ)", color = DarkText, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DoctorAvailabilityCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = ClinicalWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.LocalHospital, contentDescription = "Hospital", tint = TrustworthyTeal)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Nabha Civil Hospital Status", fontWeight = FontWeight.Bold, color = DarkText)
                Text("12 / 23 Doctors Available Currently", color = TrustworthyTeal, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun AppBottomNavigation() {
    NavigationBar(
        containerColor = ClinicalWhite,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.PermDeviceInformation, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ClinicalWhite,
                selectedTextColor = TrustworthyTeal,
                indicatorColor = TrustworthyTeal,
                unselectedIconColor = Color.Gray
            )
        )
        // Add additional tabs for Records (DHR), Pharmacy, etc.
    }
}
