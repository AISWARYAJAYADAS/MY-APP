package com.example.myapplication.create_listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun CreateListingBottomSheetContent() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 28.dp, end = 28.dp),
        text = stringResource(id = R.string.create_listing_bottom_sheet_title),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(
                start = 28.dp,
                top = 33.dp,
                end = 28.dp,
                bottom = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_listing_using_pdt),
            contentDescription = null,
            modifier = Modifier.padding(end = 32.dp)
        )
        Text(
            text = stringResource(id = R.string.create_listing_from_pdt),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }

    Divider(modifier = Modifier.padding(start = 28.dp, end = 28.dp),color = Color.LightGray)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(
                start = 28.dp,
                top = 16.dp,
                end = 28.dp,
                bottom = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_edit_photo),
            contentDescription = null,
            modifier = Modifier.padding(end = 32.dp)
        )
        Text(
            text = stringResource(id = R.string.create_listing_from_draft),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
    Divider(modifier = Modifier.padding(start = 28.dp, end = 28.dp),color = Color.LightGray)

}