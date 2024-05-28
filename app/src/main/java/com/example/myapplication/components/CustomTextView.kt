package com.example.myapplication.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun CustomTextView(text: String,mandatory: Boolean = false) {
 Row (modifier = Modifier.fillMaxWidth()){
     Text(
         text = text,
         fontSize = 12.sp,
         fontFamily = FontFamily(Font(R.font.font_regular))
     )

     Spacer(modifier = Modifier.width(8.dp))

     if (mandatory) {
         Text(
             text = stringResource(id = R.string.mandatory_symbol),
             color = Color.Red,
             fontSize = 12.sp,
             fontFamily = FontFamily(Font(R.font.font_regular))
         )
     }
 }

}