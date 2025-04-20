package com.plm.junglejumble.ui.components

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalDragOrCancellation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange

@Composable
fun ComponentSwipeDetector(
    onSwipeRight: () -> Unit,
    onSwipeLeft: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown()
                    var totalDrag = 0f

                    var drag = awaitHorizontalDragOrCancellation(down.id)
                    while (drag != null) {
                        totalDrag += drag.positionChange().x
                        drag.consume() // mark event as consumed
                        drag = awaitHorizontalDragOrCancellation(drag.id)
                    }

                    when {
                        totalDrag > 100f -> onSwipeRight()
                        totalDrag < -100f -> onSwipeLeft()
                    }
                }
            }
    ) {
        content()
    }
}
