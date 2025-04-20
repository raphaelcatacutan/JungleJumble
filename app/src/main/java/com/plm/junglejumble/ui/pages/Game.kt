package com.plm.junglejumble.ui.pages

import android.content.Context
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
import androidx.compose.material3.Icon
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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.plm.junglejumble.ui.components.ComponentThreeDContainer
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
    var isWin by remember { mutableStateOf(false) }

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
                    isWin = false
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
            painter = when(cardCount) {
                12 -> painterResource(R.drawable.bg2)
                16 -> painterResource(R.drawable.bg3)
                36 -> painterResource(R.drawable.bg4)
                else -> painterResource(R.drawable.background1)
            }, // 🖼️ set this
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 30.dp, bottom = 50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                ComponentThreeDContainer(
                    modifier = Modifier
                        .width(55.dp)
                        .height(55.dp),
                    backgroundColor = Color(0xFF78909C),
                    shadowColor = Color(0xFF546E7A),
                    cornerRadius = 15.dp,
                    isPushable = true,
                    onClick = { isPaused = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Pause",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ComponentThreeDContainer(
                    modifier = Modifier
                        .width(160.dp)
                        .height(75.dp),
                    backgroundColor = Color(0xFF9E7B3D  ),
                    shadowColor = Color(0xFF6A4F1B ),
                    cornerRadius = 15.dp
                ) {
                    // Timer Box
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = when(cardCount) {
                                12 -> "Easy Mode"
                                16 -> "Medium Mode"
                                36 -> "Hard Mode"
                                else -> "Medium Mode"
                            },
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                            lineHeight = 16.sp
                        )
                        Text(
                            text = timer,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                            lineHeight = 20.sp
                        )
                    }
                }
                ComponentThreeDContainer(
                    modifier = Modifier
                        .width(175.dp)
                        .height(50.dp),
                    backgroundColor = Color(0xFF8B3A3A),
                    shadowColor = Color(0xFF531F1F),
                    cornerRadius = 15.dp
                ) {
                    // Score Display
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Score: ${score.intValue}",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                val grid = sqrt(cardCount.toDouble()).toInt()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(grid),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
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
                                                isWin = true
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
                        ) {
                            Box {
                                Image(
                                    painter = painterResource(id = R.drawable.background1),
                                    contentDescription = "Jungle Card",
                                    contentScale = ContentScale.FillWidth
                                )
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(Color.White.copy(alpha = 0.25f)) // Adjust alpha for brightness
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Jungle Card",
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }
                }
            }

            ComponentThreeDContainer(
                modifier = Modifier
                    .width(175.dp)
                    .height(50.dp),
                backgroundColor = Color(0xFF3B5E3B),
                shadowColor = Color(0xFF1F331F),
                cornerRadius = 15.dp
            ) {
                Text(
                    text = "Remaining Pairs: $remainingCards",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )
            }
        }

        // Show exit dialog if state is true
        if (isPaused) {
            DialogPaused(onDismiss = { isPaused = false }, navController, coroutineScope)
        }

        // Show exit dialog if state is true
        if (isGameOver) {
            DialogGameOver(onDismiss = { isGameOver = false }, navController, isWin, cardCount, duration)
        }
    }
}

@Composable
fun ComponentFlipCard(
    isSelected: Boolean,
    onClick: () -> Unit,
    index: Int,
    front: @Composable () -> Unit
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
            .border(2.dp, Color(0xFF3A2B00), shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(animals[index].color)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        if (rotation.value <= 90f) {
            front()
        } else {
            val imageId = animals[index].imageResId
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Jungle Card",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .graphicsLayer(rotationY = 180f)
                    .padding(5.dp)
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
fun DialogGameOver(onDismiss: () -> Unit, navController: NavController, isWin: Boolean, cardCount: Int, duration: Int) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .graphicsLayer(clip = false)
        ) {
            ComponentThreeDContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(425.dp)
                    .padding(top = 115.dp, bottom = 50.dp),
                backgroundColor = Color(0xFF455A64),
                shadowColor = Color(0xFF263238),
                cornerRadius = 15.dp,
                isPushable = false,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = if (isWin) "Great Job! Let's Play Again" else "Nice Try! You'll get it next time",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ComponentThreeDContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        backgroundColor = Color(0xFF04773D),
                        shadowColor = Color(0xFF012317),
                        cornerRadius = 15.dp,
                        isPushable = true,
                        onClick = {
                            onDismiss()
                            navController.navigate("game/${cardCount}/${duration}")
                        }
                    ) {
                        Text("Play Again", color = Color(0xFFF5F5DC))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ComponentThreeDContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        backgroundColor = Color(0xFFEF5350),
                        shadowColor = Color(0xFFC62828),
                        cornerRadius = 15.dp,
                        isPushable = true,
                        onClick = {
                            onDismiss()
                            navController.navigate("main-menu")
                        }
                    ) {
                        Text("Exit", color = Color(0xFFF5F5DC))
                    }
                }
            }


            // Logo "floating" above dialog content
            Image(
                painter = painterResource(id = if (isWin) R.drawable.won else R.drawable.over),
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-30).dp)
                    .size(230.dp)
            )
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

    val musicFlow = remember { PreferencesManager.getMusic(context) }
    val soundFlow = remember { PreferencesManager.getSounds(context) }

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
                .fillMaxSize()
                .wrapContentHeight()
                .graphicsLayer(clip = false)
        ) {
            ComponentThreeDContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(top = 90.dp, bottom = 50.dp),
                backgroundColor = Color(0xFF455A64),
                shadowColor = Color(0xFF263238),
                cornerRadius = 15.dp,
                isPushable = false,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(top = 20.dp)
                ) {

                    // Music
                    ComponentSettingRow("Background Music:", musicEnabled) { isEnabled ->
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
                    ComponentSettingRow("Sound Effects:", soundEnabled) { isEnabled ->
                        soundEnabled = isEnabled
                        coroutineScope.launch {
                            PreferencesManager.saveSounds(soundEnabled, context)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ComponentThreeDContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        backgroundColor = Color(0xFF78909C),
                        shadowColor = Color(0xFF546E7A),
                        cornerRadius = 15.dp,
                        isPushable = true,
                        onClick = onDismiss
                    ) {
                        Text("Resume", color = Color(0xFFF5F5DC))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ComponentThreeDContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        backgroundColor = Color(0xFFEF5350),
                        shadowColor = Color(0xFFC62828),
                        cornerRadius = 15.dp,
                        isPushable = true,
                        onClick = {
                            onDismiss()
                            navController.navigate("main-menu")
                        }
                    ) {
                        Text("Exit", color = Color(0xFFF5F5DC))
                    }
                }
            }


            // Logo "floating" above dialog content
            Image(
                painter = painterResource(id = R.drawable.paused),
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-30).dp)
                    .size(230.dp)
            )
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
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
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
