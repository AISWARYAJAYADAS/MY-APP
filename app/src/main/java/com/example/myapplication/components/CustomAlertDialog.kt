package com.example.myapplication.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun CustomAlertDialog(
    message: String,
    noButtonText: String? = null,
    yesButtonText: String? = null,
    buttonAction: ((Boolean) -> Unit)? = null,
) {

    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (noButtonText != null) {
                        OutlinedButton(
                            onClick = {
                                showDialog = false
                                buttonAction?.invoke(false)
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f)
                        ) {
                            Text(noButtonText, modifier = Modifier.padding(4.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(21.dp))
                    if (yesButtonText != null) {
                        OutlinedButton(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            ),
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f),
                            onClick = {
                                showDialog = false
                                buttonAction?.invoke(true)
                            }) {
                            Text(yesButtonText, modifier = Modifier.padding(4.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = message,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 40.dp, bottom = 30.dp)
                    )

                }
            }
        )
    }


}

@Preview
@Composable
fun PreviewCustomAlertDialog() {
    CustomAlertDialog(
        message = stringResource(R.string.logout_confirmation),
        noButtonText = stringResource(id = R.string.no),
        yesButtonText = stringResource(id = R.string.yes)
    )

}

