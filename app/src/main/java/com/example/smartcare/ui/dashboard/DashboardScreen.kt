package com.smartcare.ui.dashboard

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartcare.ui.ClinicalWhite
import com.smartcare.ui.LightBackground
import com.smartcare.ui.SoftGold
import com.smartcare.ui.TrustworthyTeal

@Composable
fun MainDashboard(isOffline: Boolean = false, lastUpdated: String = "10 mins ago") {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = { DashboardBottomNavigation(selectedTab) { selectedTab = it } },
        containerColor = LightBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isOffline) {
                OfflineBannerDashboard(lastUpdated)
            }

            // 1. Doctor Availability Ticker (Horizontal Scroll)
            DoctorAvailabilityTicker()

            Spacer(modifier = Modifier.height(16.dp))

            // Header Message
            Text(
                text = "ਸਤਿ ਸ੍ਰੀ ਅਕਾਲ",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = TrustworthyTeal,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "How can we help you today?",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Service Grid
            ServiceGrid()
        }
    }
}

// --- Ticker ---
@Composable
fun DoctorAvailabilityTicker() {
    val scrollState = rememberScrollState()

    // Auto-scroll effect (simulated ticker)
    LaunchedEffect(Unit) {
        while (true) {
            scrollState.animateScrollTo(
                value = scrollState.maxValue,
                animationSpec = tween(durationMillis = 15000, easing = LinearEasing)
            )
            scrollState.scrollTo(0)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TrustworthyTeal.copy(alpha = 0.1f))
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.NotificationsActive,
            contentDescription = "Alert",
            tint = TrustworthyTeal,
            modifier = Modifier.padding(start = 16.dp, end = 8.dp).size(20.dp)
        )
        Text(
            text = "LIVE UPDATE: Nabha Civil Hospital - 11/23 Doctors Active • Dr. Singh (Cardio) Available • Dr. Kaur (Pediatrics) in Emergency • OPD Wait Time: 45 min • ",
            color = TrustworthyTeal,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            maxLines = 1,
            modifier = Modifier.horizontalScroll(scrollState)
        )
    }
}

// --- Service Grid ---
data class ServiceItem(val title: String, val icon: ImageVector, val color: Color, val isPrimary: Boolean = false)

@Composable
fun ServiceGrid() {
    val services = listOf(
        ServiceItem("Check Symptoms\n(AI Assistant)", Icons.Default.SmartToy, TrustworthyTeal, true),
        ServiceItem("Call a Doctor\n(Telemedicine)", Icons.Default.VideoCall, Color(0xFFE53935), true),
        ServiceItem("Medicine\nAvailability", Icons.Default.MedicalServices, Color(0xFF43A047)),
        ServiceItem("My Health Card\n(QR ID)", Icons.Default.QrCode, Color(0xFF1E88E5))
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(services) { service ->
            ServiceCard(service)
        }
    }
}

@Composable
fun ServiceCard(item: ServiceItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { /* Navigate to feature */ },
        colors = CardDefaults.cardColors(
            containerColor = if (item.isPrimary) item.color else ClinicalWhite
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (item.isPrimary) ClinicalWhite.copy(alpha = 0.2f) else item.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    item.icon,
                    contentDescription = item.title,
                    tint = if (item.isPrimary) ClinicalWhite else item.color,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = item.title,
                color = if (item.isPrimary) ClinicalWhite else Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}

// --- Bottom Navigation ---
@Composable
fun DashboardBottomNavigation(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = ClinicalWhite,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple("Home", Icons.Default.Home, 0),
            Triple("Consult", Icons.Default.ChatBubbleOutline, 1),
            Triple("Pharmacy", Icons.Default.LocalPharmacy, 2),
            Triple("Records", Icons.Default.FolderShared, 3)
        )

        items.forEach { (label, icon, index) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, fontSize = 12.sp) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkTextSelection(),
                    selectedTextColor = TrustworthyTeal,
                    indicatorColor = SoftGold,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

fun DarkTextSelection(): Color = Color(0xFF1E1E1E)

@Composable
fun OfflineBannerDashboard(lastUpdated: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF3CD))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.CloudOff, contentDescription = "Offline", tint = Color(0xFF856404), modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Offline Mode - Last updated: $lastUpdated",
            color = Color(0xFF856404),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
