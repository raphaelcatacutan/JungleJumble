package com.plm.junglejumble.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.GenericShape
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun ComponentPopupContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    shadowColor: Color = Color.Gray,
    shape: Shape = RoundedCornerShape(16.dp),
    isPushable: Boolean = true,
    onClick: (() -> Unit)? = null,
    image: Painter? = null,
    content: @Composable BoxScope.() -> Unit // Image parameter
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

    val clickableModifier = if (onClick != null && isPushable) {
        Modifier.pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    isPressed = true
                    tryAwaitRelease()
                    isPressed = false
                    onClick()
                }
            )
        }
    } else Modifier

    Box(
        modifier = modifier
            .padding(bottom = if (isPushable) 6.dp else 0.dp)
            .then(clickableModifier)
    ) {
        // Shadow base layer
        Box(
            modifier = Modifier
                .matchParentSize() // respects size from outer Box
                .offset(y = 6.dp)
                .alpha(shadowAlpha)
                .clip(shape)
                .background(shadowColor)
        )

        // Foreground content container
        Box(
            modifier = Modifier
                .matchParentSize() // makes it fill the outer size
                .offset(y = offsetY)
                .clip(shape)
                .background(backgroundColor), // No padding here for the image to cover the full space
            contentAlignment = Alignment.Center
        ) {
            // Image if provided, it will fill the inner container and be clipped
            image?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize() // Ensure image covers the whole container
                        .clip(shape), // Clip the image inside the shape
                    contentScale = ContentScale.Crop // Scale and crop the image to fill the container
                )
            }

            // Content inside the Box, now with padding applied to it (not the image)
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp) // Padding only affects content
            ) {
                content()
            }
        }

    }
}

val CrackedShape = GenericShape { size, _ ->
    // TOP edge (left → right)
    moveTo(0f, 20f)
    lineTo(size.width * 0.2f, 0f)
    lineTo(size.width * 0.4f, 30f)
    lineTo(size.width * 0.6f, 0f)
    lineTo(size.width * 0.8f, 25f)
    lineTo(size.width, 10f)

    // RIGHT edge (top → bottom)
    lineTo(size.width - 20f, size.height * 0.2f)
    lineTo(size.width, size.height * 0.4f)
    lineTo(size.width - 25f, size.height * 0.6f)
    lineTo(size.width, size.height * 0.8f)
    lineTo(size.width - 15f, size.height)

    // BOTTOM edge (right → left)
    lineTo(size.width * 0.8f, size.height - 25f)
    lineTo(size.width * 0.6f, size.height)
    lineTo(size.width * 0.4f, size.height - 30f)
    lineTo(size.width * 0.2f, size.height)
    lineTo(0f, size.height - 20f)

    // LEFT edge (bottom → top)
    lineTo(20f, size.height * 0.8f)
    lineTo(0f, size.height * 0.6f)
    lineTo(25f, size.height * 0.4f)
    lineTo(0f, size.height * 0.2f)

    close()
}