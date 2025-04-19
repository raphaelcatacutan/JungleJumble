package com.plm.junglejumble.ui.pages

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plm.junglejumble.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.plm.junglejumble.ui.components.ComponentThreeDContainer
import com.plm.junglejumble.utils.SessionManager
import com.plm.junglejumble.utils.SessionManager.userViewModel
import kotlin.system.exitProcess

@Composable
fun ViewMainMenu(navController: NavController = rememberNavController()) {
    val backgroundImage = painterResource(id = R.drawable.background1)
    val logoImage = painterResource(id = R.drawable.logo)
    var showExitDialog by remember { mutableStateOf(false) }
    var showDifficultyDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showExitDialog = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.8f,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 50.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            ComponentThreeDContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                backgroundColor = Color(0xFF6B8E23),
                shadowColor = Color(0xFF4A5F17),
                cornerRadius = 15.dp,
                isPushable = true,
                onClick = {
                    showDifficultyDialog = true
                }
            ) {
                Text("PLAY", color = Color(0xFFF5F5DC))
            }

            Spacer(modifier = Modifier.height(12.dp))

            ComponentThreeDContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                backgroundColor = Color(0xFF5C4033),
                shadowColor = Color(0xFF3A261C),
                cornerRadius = 15.dp,
                isPushable = true,
                onClick = { navController.navigate("card_catalog") }
            ) {
                Text("CARD CATALOG", color = Color(0xFFF5F5DC))
            }

            Spacer(modifier = Modifier.height(12.dp))

            ComponentThreeDContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                backgroundColor = Color(0xFF3B5F5F),
                shadowColor = Color(0xFF273F3F),
                cornerRadius = 15.dp,
                isPushable = true,
                onClick = { navController.navigate("leaderboard") }
            ) {
                Text("VIEW HIGH SCORES", color = Color(0xFFF5F5DC))
            }

            Spacer(modifier = Modifier.height(12.dp))

            ComponentThreeDContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                backgroundColor = Color(0xFF8B3A3A),
                shadowColor = Color(0xFF5C2626),
                cornerRadius = 15.dp,
                isPushable = true,
                onClick = { showExitDialog = true }
            ) {
                Text("EXIT", color = Color(0xFFF5F5DC))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(top = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = logoImage,
                contentDescription = "Logo",
                modifier = Modifier
                    .size(290.dp)
            )
        }

        // Show exit dialog if state is true
        if (showExitDialog) {
            DialogExit(onDismiss = { showExitDialog = false })
        }

        if (showDifficultyDialog) {
            DialogDifficulty(onDismiss = { showDifficultyDialog = false }, navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainMenu() {
    ViewMainMenu()
}
@Composable
fun DialogExit(onDismiss: () -> Unit) {
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
                    .height(450.dp)
                    .padding(top = 110.dp, bottom = 50.dp),
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
                        text = "Are you sure you want to exit to your home screen?",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(32.dp))

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
                        Text("NO", color = Color(0xFFF5F5DC))
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
                            android.os.Process.killProcess(android.os.Process.myPid())
                            exitProcess(0)
                        }
                    ) {
                        Text("YES", color = Color(0xFFF5F5DC))
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

@Composable
fun DialogDifficulty(
    onDismiss: () -> Unit,
    navController: NavController
) {
    var difficulty by remember { mutableStateOf("Medium (4x4)") }
    var time by remember { mutableStateOf("30 Second") }

    val difficultyOptions = listOf("Easy (3x4)", "Medium (4x4)", "Hard (6x6)")
    val timeOptions = listOf("15 Second", "30 Second", "60 Second")

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
                    .height(480.dp)
                    .padding(top = 120.dp, bottom = 50.dp),
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
                    ComponentDropdownSelector("DIFFICULTY:", difficulty, difficultyOptions) {
                        difficulty = it
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ComponentDropdownSelector("TIME:", time, timeOptions) {
                        time = it
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ComponentThreeDContainer(
                        modifier = Modifier
                            .height(55.dp)
                            .width(55.dp),
                        backgroundColor = Color(0xFF6B8E23),
                        shadowColor = Color(0xFF4A5F17),
                        cornerRadius = 15.dp,
                        isPushable = true,
                        onClick = {

                            onDismiss()
                            val cardNumber = when (difficultyOptions.indexOf(difficulty)) {
                                0 -> 12
                                1 -> 16
                                2 -> 36
                                else -> 16
                            }
                            val duration = when (timeOptions.indexOf(time)) {
                                0 -> 15
                                1 -> 30
                                2 -> 60
                                else -> 30
                            }
                            navController.navigate("game/$cardNumber/$duration")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Start Game",
                            tint = Color.White
                        )
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
@Composable
fun ComponentDropdownSelector(
    label: String,
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.Start) {
        Text(text = label, fontWeight = FontWeight.Bold, color = Color.White)
        Box {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            )

            // Transparent clickable overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .clickable {
                        expanded = true
                        Log.w("ComposeWarning", "Clicked")
                    }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(200.dp) // Match the width of the text field
                    .background(Color(0xFF607D8B))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelect(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

