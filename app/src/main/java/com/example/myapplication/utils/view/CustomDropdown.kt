import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun CustomDropdown(
    items: List<String>,
    selectedItem: MutableState<String>,
    expanded: MutableState<Boolean>,
    onItemSelected: (String) -> Unit,
    textColor: Color = Color.Black,
    textSize: TextUnit = 14.sp,
    contentWidth: Dp = 200.dp,
    contentHeight: Dp = 48.dp,
    backgroundColor: Color = colorResource(id = R.color.app_background)
) {
    Box(
        modifier = Modifier
            .widthIn(contentWidth)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(4.dp))
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .heightIn(contentHeight)
        ) {
            Box(
                modifier = Modifier
                    .background(backgroundColor)
                    .clickable(onClick = { expanded.value = !expanded.value })
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .heightIn(contentHeight)
            ) {
                Text(
                    text = selectedItem.value,
                    style = TextStyle(color = textColor, fontSize = textSize),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(item)
                            expanded.value = false
                        },
                        text = {
                            Text(
                                text = item,
                                style = TextStyle(color = textColor, fontSize = textSize),
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}
