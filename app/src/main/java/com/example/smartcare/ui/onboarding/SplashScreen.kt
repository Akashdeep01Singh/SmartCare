package com.smartcare.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // 3 seconds timeout
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)), // Clinical White
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Using a generic medical icon as placeholder for the logo. In production replace with SVG/Vector
            Image(
                painter = painterResource(id = android.R.drawable.ic_dialog_info),
                contentDescription = "SmartCare Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "SmartCare System",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF008080) // Trustworthy Teal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bridging Healthcare for Nabha",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}
