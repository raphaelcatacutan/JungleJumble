package com.plm.junglejumble.ui.pages

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plm.junglejumble.R
import com.plm.junglejumble.database.models.Score
import com.plm.junglejumble.utils.PreferencesManager
import com.plm.junglejumble.utils.SessionManager.currentUser
import com.plm.junglejumble.utils.SessionManager.scoreViewModel
import com.plm.junglejumble.utils.generatePairs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.math.sqrt
import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope

var mediaPlayer: MediaPlayer? = null

data class CardItem(
    val id: Int,
    val isSelected: Boolean
)

@Composable
fun ViewGame(cardCount: Int, duration: Int, navController: NavController = rememberNavController()) {
    var isPaused by remember { mutableStateOf(false) }
    var isGameOver by remember { mutableStateOf(false) }
    var gameOverReason by remember { mutableStateOf("") }


    val score = remember { mutableIntStateOf(0) }

    // Timer
    var time by remember { mutableIntStateOf(duration) }
    val timer = "%02d:%02d".format(time / 60, time % 60)
    LaunchedEffect(isPaused) {
        while (true) {
            if (!isPaused && !isGameOver) {
                delay(1000L) // 1 second
                time -= 1
                if (time <= 0) {
                    isGameOver = true
                    gameOverReason = "Timer ran out"
                    scoreViewModel?.addScore(Score(ownerId = currentUser!!.id, score = score.intValue))
                }
            } else {
                delay(100L) // Small delay to avoid busy loop
            }
        }
    }

    // Cards
    val pairs = remember {
        generatePairs(cardCount)
    }
    var cards by remember {
        mutableStateOf(
            List(cardCount) { index -> CardItem(id = index, isSelected = false) }
        )
    }

    var remainingCards by remember { mutableIntStateOf(cardCount/2) }
    var selectedIndices by remember { mutableStateOf<List<Int>>(emptyList()) }
    var isProcessing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = true) {
        isPaused = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                    onClick = {
                        isPaused = true
                    },
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
                    text = timer,
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.White)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                val grid = sqrt(cardCount.toDouble()).toInt()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(grid),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
                    Log.w("ComposeWarning", pairs.toString())
                    items(cardCount) { index ->
                        val i = pairs.indexOfFirst { it.first == index || it.second == index }

                        val card = cards[index]
                        ComponentFlipCard(
                            isSelected = card.isSelected,
                            onClick = {
                                if (isProcessing || card.isSelected) return@ComponentFlipCard

                                selectedIndices = selectedIndices + index
                                cards = cards.mapIndexed { i, item ->
                                    if (i == index) item.copy(isSelected = true) else item
                                }

                                if (selectedIndices.size == 2) {
                                    isProcessing = true
                                    coroutineScope.launch {
                                        delay(1000L)

                                        val first = selectedIndices[0]
                                        val second = selectedIndices[1]

                                        val isMatch = pairs.any {
                                            (it.first == first && it.second == second) || (it.first == second && it.second == first)
                                        }

                                        if (isMatch) {
                                            val basePoints = 5
                                            val multiplier = 0.1f
                                            val bonus = (time * multiplier).roundToInt()
                                            val pointsEarned = basePoints + bonus
                                            score.intValue += pointsEarned
                                            remainingCards--
                                            if (remainingCards <= 0) {
                                                isGameOver = true
                                                gameOverReason = "You Won"
                                                scoreViewModel?.addScore(Score(ownerId = currentUser!!.id, score = score.intValue))
                                            }
                                        } else {
                                            cards = cards.mapIndexed { i, item ->
                                                if (selectedIndices.contains(i)) item.copy(isSelected = false)
                                                else item
                                            }
                                        }

                                        selectedIndices = emptyList()
                                        isProcessing = false
                                    }
                                }
                            },
                            index = i
                        )
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
                        text = "Remaining Pairs: ${remainingCards}",
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
                        text = "Score: ${score.intValue}",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                    )
                }
            }
        }

        // Show exit dialog if state is true
        if (isPaused) {
            DialogPaused(onDismiss = { isPaused = false }, navController, coroutineScope)
        }

        // Show exit dialog if state is true
        if (isGameOver) {
            DialogGameOver(onDismiss = { isGameOver = false }, navController, gameOverReason, cardCount, duration)
        }
    }
}

@Composable
fun ComponentFlipCard(
    isSelected: Boolean,
    onClick: () -> Unit,
    index: Int
)
{
    val rotation = animateFloatAsState(
        targetValue = if (isSelected) 180f else 0f,
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
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        if (rotation.value <= 90f) {
            Image(
                painter = painterResource(id = R.drawable.logo), // 🔄 your card back
                contentDescription = "Jungle Card",
                contentScale = ContentScale.Crop
            )
        } else {
            val imageId = animals[index].imageResId
            Image(
                painter = painterResource(id = imageId), // 🔄 your card back
                contentDescription = "Jungle Card",
                contentScale = ContentScale.Crop,
                modifier = Modifier.graphicsLayer(
                    rotationY = 180f
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGame() {
    ViewGame(16, 120)
}

@Composable
fun DialogGameOver(onDismiss: () -> Unit, navController: NavController, gameOverReason: String, cardCount: Int, duration: Int) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color(0xFF73D478),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = gameOverReason,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color(0xFF09A237), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )

                // Resume button
                Button(
                    onClick = {
                        onDismiss()
                        navController.navigate("game/${cardCount}/${duration}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Play Again", color = Color.White, fontSize = 16.sp)
                }

                // Exit button
                Button(
                    onClick = {
                        onDismiss()
                        navController.navigate("main-menu")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Exit", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}


@Composable
fun DialogPaused(
    onDismiss: () -> Unit,
    navController: NavController,
    coroutineScope: CoroutineScope
) {
    val context = LocalContext.current

    var musicFlow = remember { PreferencesManager.getMusic(context) }
    var soundFlow = remember { PreferencesManager.getSounds(context) }

    val musicEnabledDatastore by musicFlow.collectAsState(initial = false)
    val soundEnabledDatastore by soundFlow.collectAsState(initial = false)

    var musicEnabled by remember { mutableStateOf(musicEnabledDatastore) }
    var soundEnabled by remember { mutableStateOf(soundEnabledDatastore) }

    // Sync music state with datastore
    LaunchedEffect(musicEnabledDatastore) {
        musicEnabled = musicEnabledDatastore
        // Automatically play/stop music when the state changes
        if (musicEnabled) {
            playMusic(context)
        } else {
            stopMusic()
        }
    }

    // Sync sound state with datastore
    LaunchedEffect(soundEnabledDatastore) {
        soundEnabled = soundEnabledDatastore
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color(0xFF73D478),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = "Game pause",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color(0xFF09A237), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )

                // Music toggle with dynamic playback control
                ComponentSettingRow("🎵 MUSIC:", musicEnabled) { isEnabled ->
                    musicEnabled = isEnabled
                    coroutineScope.launch {
                        PreferencesManager.saveMusic(musicEnabled, context)

                        if (musicEnabled) {
                            playMusic(context = context)
                        } else {
                            stopMusic()
                        }
                    }
                }

                // Sound toggle
                ComponentSettingRow("🔊 SOUND:", soundEnabled) { isEnabled ->
                    soundEnabled = isEnabled
                    coroutineScope.launch {
                        PreferencesManager.saveSounds(soundEnabled, context)
                    }
                }

                // Resume button
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Resume", color = Color.White, fontSize = 16.sp)
                }

                // Exit button
                Button(
                    onClick = {
                        onDismiss()
                        navController.navigate("main-menu")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Exit", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

fun playMusic(context: Context) {
    if (mediaPlayer == null) {
        mediaPlayer = MediaPlayer.create(context, R.raw.bg_music) // Replace with your actual music file in `res/raw`
        mediaPlayer?.isLooping = true // Optional: Loop the music
        mediaPlayer?.start()
    }

}


// Stop music function
fun stopMusic() {
    mediaPlayer?.stop()
    mediaPlayer?.release()
    mediaPlayer = null
}

@Composable
fun ComponentSettingRow(label: String, state: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Switch(
            checked = state,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF69F0AE),
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}
