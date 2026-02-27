package com.smartcare.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartcare.ui.ClinicalWhite
import com.smartcare.ui.LightBackground
import com.smartcare.ui.SoftGold
import com.smartcare.ui.TrustworthyTeal

data class OnboardingPage(val title: String, val description: String, val iconRes: Int)

val onboardingPages = listOf(
    OnboardingPage(
        title = "Telemedicine at your Fingertips",
        description = "Consult Civil Hospital doctors via video or audio directly from your phone. Switches to audio-only if your 4G drops.",
        iconRes = android.R.drawable.ic_menu_camera
    ),
    OnboardingPage(
        title = "Live Medicine Stock",
        description = "Check real-time availability of necessary medicines at Nabha Civil Hospital and local pharmacies to prevent wasted trips.",
        iconRes = android.R.drawable.ic_menu_agenda
    ),
    OnboardingPage(
        title = "Offline Health Records",
        description = "Your prescriptions and medical history are saved securely on your device, accessible even without an internet connection.",
        iconRes = android.R.drawable.ic_menu_save
    )
)

@Composable
fun OnboardingScreen(onFinishOnboarding: () -> Unit) {
    var currentPage by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Content
        val page = onboardingPages[currentPage]

        // Placeholder for Lottie Animation or Vector Image
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(ClinicalWhite),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = androidx.compose.ui.res.painterResource(id = page.iconRes),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = TrustworthyTeal
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TrustworthyTeal,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            fontSize = 16.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Pagination Dots
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(onboardingPages.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (index == currentPage) 12.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (index == currentPage) TrustworthyTeal else Color.LightGray)
                )
            }
        }

        // Action Button
        Button(
            onClick = {
                if (currentPage < onboardingPages.size - 1) {
                    currentPage++
                } else {
                    onFinishOnboarding()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // High contrast, large touch target
            colors = ButtonDefaults.buttonColors(containerColor = SoftGold),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (currentPage == onboardingPages.size - 1) "Get Started" else "Next",
                color = Color(0xFF1E1E1E), // Dark Text for contrast on Gold
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (currentPage < onboardingPages.size - 1) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next", tint = Color(0xFF1E1E1E))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
