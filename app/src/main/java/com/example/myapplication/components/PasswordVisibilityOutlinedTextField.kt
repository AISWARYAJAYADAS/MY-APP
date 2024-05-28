package com.example.myapplication.components

import PasswordVisibilityIcon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun PasswordVisibilityOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    isError: Boolean = false,
    errorMessageResId: Int? = null
) {
    Column (

    ){
        OutlinedTextField(
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 14.sp,
                    color = Color(0xFF6A6A6A)
                )
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                PasswordVisibilityIcon(
                    isPasswordVisible = isPasswordVisible,
                    onToggleClick = onTogglePasswordVisibility
                )
            },
            singleLine = true,
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
