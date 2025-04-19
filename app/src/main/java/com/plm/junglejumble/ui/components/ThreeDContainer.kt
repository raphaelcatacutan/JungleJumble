package com.plm.junglejumble.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ComponentThreeDContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    shadowColor: Color = Color.Gray,
    cornerRadius: Dp = 16.dp,
    isPushable: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val offsetY by animateDpAsState(
        targetValue = if (isPushable && isPressed) 6.dp else 0.dp,
        label = "offsetY"
    )
    val shadowAlpha by animateFloatAsState(
        targetValue = if (isPushable && isPressed) 0f else 1f,
        label = "shadowAlpha"
    )

    Box(
        modifier = modifier
            .padding(bottom = if (isPushable) 6.dp else 0.dp)
            .then(
                if (onClick != null && isPushable) Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                            onClick()
                        }
                    )
                } else Modifier
            )
    ) {
        // Shadow base layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = 6.dp)
                .alpha(shadowAlpha)
                .clip(RoundedCornerShape(cornerRadius))
                .background(shadowColor)
        )

        // Foreground content container
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = offsetY)
                .clip(RoundedCornerShape(cornerRadius))
                .background(backgroundColor),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}