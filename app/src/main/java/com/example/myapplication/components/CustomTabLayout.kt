package com.example.myapplication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun CustomTabLayout(
    tabData: List<TabData>, // Pass a list of TabData objects
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {


    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = colorResource(id = R.color.app_background),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = colorResource(id = R.color.text_color_active)
            )
        }
    ) {
        tabData.forEachIndexed { index, tab ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },// Notify parent of tab selection
                text = {
                    Text(
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        text = buildAnnotatedString {
                            append(tab.text)
                            val count = tab.count ?: 0
                            append(" ($count)")
                            addStyle(
                                style = SpanStyle(color = Color.Gray),
                                start = tab.text.length,
                                end = tab.text.length + count.toString().length + 3 // (count) + parentheses
                            )
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = if (selectedTabIndex == index) Color.Black else Color(0xFF6A6A6A)
                    )
                },
            )
        }
    }

}

data class TabData(val text: String, val count: Int?) // Define TabData class

@Preview
@Composable
private fun PreviewProfileTabLayout() {
    val sampleTabs = listOf(
        TabData("出品中", 10),
        TabData("取引中", null),
        TabData("取引完了", 5)
    )
    var selectedTabIndex by remember { mutableStateOf(0) }
    CustomTabLayout(
        tabData = sampleTabs,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { index ->
            selectedTabIndex = index
        }
    )
}