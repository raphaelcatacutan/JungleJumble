package com.plm.junglejumble.ui.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plm.junglejumble.R

@Composable
fun ViewGame(navController: NavController = rememberNavController()) {
    val timer = remember { mutableStateOf("2:59") }
    val score = remember { mutableStateOf(0) }
    val flips = remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background1), // 🖼️ set this
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EASY MODE",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                )

                IconButton(
                    onClick = { /* TODO: Pause game */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Pause",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Timer Box
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color(0xAA2ECC71), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = timer.value,
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3x3 Grid of Cards
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
                items(9) { index ->
                    ComponentFlipCard()
                }
            }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Flip Counter
                Box(
                    modifier = Modifier
                        .background(Color(0xFF80DEEA), shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Flips: ${flips.value}",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                    )
                }

                // Score Display
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFCDD2), shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Score: ${score.value}",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                    )
                }
            }
        }
    }
}

@Composable
fun ComponentFlipCard() {
    var flipped by remember { mutableStateOf(false) }
    val rotation = animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 8 * density
            }
            .background(
                color = Color(0xFFB2FF59), // any color you want
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { flipped = !flipped },
        contentAlignment = Alignment.Center,
    ) {
        if (rotation.value <= 90f) {
            Image(
                painter = painterResource(id = R.drawable.logo), // 🔄 your card back
                contentDescription = "Jungle Card",
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = "Hello World",
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
                    .padding(10.dp)
                    .graphicsLayer {
                        rotationY = 180f
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGame() {
    ViewGame()
}