package com.example.myapplication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun CustomGradientButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF3B8DE4),
                        Color(0xFF4FC2E8)
                    ),
                ),
                shape = RoundedCornerShape(25.dp)
            ),
      colors = ButtonDefaults.buttonColors(
          containerColor = Color.Transparent,
      ),

        onClick = {
            onClick.invoke()
        }
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.font_bold)),
            fontSize = 16.sp,
            )
    }
}