package com.plm.junglejumble.ui.pages

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
                .padding(16.dp)
                .background(
                    color = Color(0xFF73D478),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Title
                Text(
                    text = "EXIT GAME",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color(0xFF09A237), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Message
                Text(
                    text = "Are you sure you want to exit?",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // No button
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(end = 8.dp)
                    ) {
                        Text("NO", color = Color.White, fontSize = 16.sp)
                    }

                    // Yes button
                    Button(
                        onClick = {
                            // Exit the app
                            android.os.Process.killProcess(android.os.Process.myPid())
                            exitProcess(0)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(start = 8.dp)
                    ) {
                        Text("YES", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
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

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .background(Color(0xFF8BC34A), RoundedCornerShape(24.dp))
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SELECT\nDIFFICULTY",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                ComponentDropdownSelector("DIFFICULTY:", difficulty, difficultyOptions) {
                    difficulty = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                ComponentDropdownSelector("TIME:", time, timeOptions) {
                    time = it
                }

                Spacer(modifier = Modifier.height(24.dp))

                IconButton(
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
                    },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0xFF2E7D32), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Start Game",
                        tint = Color.White
                    )
                }
            }
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
                onDismissRequest = { expanded = false }
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

