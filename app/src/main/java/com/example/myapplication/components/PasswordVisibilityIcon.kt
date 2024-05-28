import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

@Composable
fun PasswordVisibilityIcon(
    isPasswordVisible: Boolean, onToggleClick: () -> Unit
) {
    val transition = updateTransition(targetState = isPasswordVisible, label = "")

    val rotation by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 300, easing = LinearEasing)
        }, label = ""
    ) { state ->
        if (state) 1.0f else 0.0f
    }

    val passwordIcon: Painter = painterResource(
        if (isPasswordVisible) R.drawable.ic_visibility_off
        else R.drawable.ic_visibility
    )

    Box(modifier = Modifier.clickable(onClick = onToggleClick)) {
        Icon(
            painter = passwordIcon,
            contentDescription = "Password Visibility Icon",
            modifier = Modifier.rotate(rotation),
            tint = Color(0xFF6A6A6A).copy(alpha = 0.6f),
        )
    }
}


