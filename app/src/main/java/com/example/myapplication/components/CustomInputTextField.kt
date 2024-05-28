package com.example.myapplication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomInputTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    isError: Boolean = false,
    errorMessageResId: Int? = null
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 14.sp,
                    color = Color(0xFF6A6A6A)
                )
            },
            shape = RoundedCornerShape(4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                errorCursorColor = Color.Red, // Adjust error cursor color
                focusedBorderColor = if (isError) Color.Red else colorResource(id = R.color.text_color_active), // Adjust focused border color
                unfocusedBorderColor = if (isError) Color.Red else Color.LightGray, // Adjust unfocused border color
            ),
        )

        // Display error text if isError is true and errorText is not null
        if (isError && errorMessageResId != null) {
            Text(
                text = stringResource(errorMessageResId),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp) // Add padding between the text field and error text
            )
        }
    }

}