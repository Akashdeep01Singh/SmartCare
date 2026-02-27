package com.smartcare.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartcare.ui.ClinicalWhite
import com.smartcare.ui.LightBackground
import com.smartcare.ui.SoftGold
import com.smartcare.ui.TrustworthyTeal

@Composable
fun LanguageSelectionScreen(onLanguageSelected: (String) -> Unit) {
    val languages = listOf(
        Pair("pa", "ਪੰਜਾਬੀ (Punjabi)"),
        Pair("hi", "हिन्दी (Hindi)"),
        Pair("en", "English")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Language,
            contentDescription = "Language",
            modifier = Modifier.size(80.dp),
            tint = TrustworthyTeal
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Choose Your Language",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TrustworthyTeal
        )
        Text(
            text = "ਆਪਣੀ ਭਾਸ਼ਾ ਚੁਣੋ / अपनी भाषा चुनें",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
        )

        languages.forEach { (code, displayName) ->
            LanguageCard(displayName = displayName) {
                // In a real app, save this preference in DataStore/SharedPreferences
                // to persist throughout the app
                onLanguageSelected(code)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LanguageCard(displayName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = ClinicalWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = displayName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
        }
    }
}
