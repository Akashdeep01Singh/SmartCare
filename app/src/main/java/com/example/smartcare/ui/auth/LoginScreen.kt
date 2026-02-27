package com.smartcare.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartcare.ui.LightBackground
import com.smartcare.ui.SoftGold
import com.smartcare.ui.TrustworthyTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var isOtpSent by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isOtpSent) "Enter OTP" else "Welcome to SmartCare",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TrustworthyTeal
        )
        Text(
            text = if (isOtpSent) "We've sent a code to $phoneNumber" else "Login securely using your phone number",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
        )

        if (!isOtpSent) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number / ਮੋਬਾਈਲ ਨੰਬਰ") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Phone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TrustworthyTeal,
                    focusedLabelColor = TrustworthyTeal,
                    cursorColor = TrustworthyTeal
                ),
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            OutlinedTextField(
                value = otpCode,
                onValueChange = { otpCode = it },
                label = { Text("6-Digit OTP / ਓ.ਟੀ.ਪੀ") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TrustworthyTeal,
                    focusedLabelColor = TrustworthyTeal,
                    cursorColor = TrustworthyTeal
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (!isOtpSent) {
                    // Trigger Firebase OTP request here
                    isOtpSent = true
                } else {
                    // Verify Firebase OTP here
                    onLoginSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp), // High contrast, large touch target
            colors = ButtonDefaults.buttonColors(containerColor = SoftGold),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (isOtpSent) "Verify OTP (ਪੁਸ਼ਟੀ ਕਰੋ)" else "Send OTP (ਓ.ਟੀ.ਪੀ ਭੇਜੋ)",
                color = Color(0xFF1E1E1E), // Dark Theme text for accessibility contrast
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
