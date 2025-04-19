package com.plm.junglejumble.ui.pages

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plm.junglejumble.R
import com.plm.junglejumble.database.models.User
import com.plm.junglejumble.ui.components.ComponentPopupContainer
import com.plm.junglejumble.ui.components.ComponentThreeDContainer
import com.plm.junglejumble.ui.components.CrackedShape
import com.plm.junglejumble.utils.SessionManager
import com.plm.junglejumble.utils.SessionManager.userViewModel

@Composable
fun ViewLogin(navController: NavController = rememberNavController()) {
    val backgroundImage = painterResource(id = R.drawable.background1)
    val logoImage = painterResource(id = R.drawable.logo)
    var errorMessage by remember { mutableStateOf("") }

    BackHandler(enabled = true) { }

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
            alpha = 0.8f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ComponentPopupContainer(
                modifier = Modifier
                    .size(width = 315.dp, height = 450.dp),
                backgroundColor = Color(0xFF8B4513),
                shadowColor = Color(0xFF644E40),
                shape = CrackedShape,
                onClick = { /* your action here */ },
                isPushable = false,
                image = painterResource(R.drawable.ggg)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .background(Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = errorMessage,
                        fontSize = 13.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    var username by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }

                    ComponentThreeDContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        backgroundColor = Color(0xFF7E6F5A),
                        shadowColor = Color(0xFF5D4F3E),
                        cornerRadius = 15.dp,
                        isPushable = false,
                        onClick = {  }
                    ) {
                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            placeholder = { Text("Username") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            colors = TextFieldDefaults.colors(
                                // Background colors
                                focusedContainerColor = Color.Transparent, // Dark brown background
                                unfocusedContainerColor = Color.Transparent, // Muted brown background
                                focusedTextColor = Color.White, // Light beige text
                                unfocusedTextColor = Color.White, // Light beige text
                                unfocusedPlaceholderColor = Color.LightGray, // Soft light brown placeholder
                                focusedPlaceholderColor = Color.LightGray, // Soft light brown placeholder
                                errorContainerColor = Color.Transparent, // Deep rust red for error state
                                // Remove all indicators
                                focusedIndicatorColor = Color.Transparent, // No focus indicator
                                unfocusedIndicatorColor = Color.Transparent, // No unfocused indicator
                                disabledIndicatorColor = Color.Transparent, // No disabled indicator
                                errorIndicatorColor = Color.Transparent // No error indicator
                            ),
                            singleLine = true,

                            )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ComponentThreeDContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        backgroundColor = Color(0xFF7E6F5A),
                        shadowColor = Color(0xFF5D4F3E),
                        cornerRadius = 15.dp,
                        isPushable = false,
                        onClick = {  }
                    ) {
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                            modifier = Modifier
                                .matchParentSize(),
                            colors = TextFieldDefaults.colors(
                                // Background colors
                                focusedContainerColor = Color.Transparent, // Dark brown background
                                unfocusedContainerColor = Color.Transparent, // Muted brown background
                                focusedTextColor = Color.White, // Light beige text
                                unfocusedTextColor = Color.White, // Light beige text
                                unfocusedPlaceholderColor = Color.LightGray, // Soft light brown placeholder
                                focusedPlaceholderColor = Color.LightGray, // Soft light brown placeholder
                                errorContainerColor = Color.Transparent, // Deep rust red for error state
                                // Remove all indicators
                                focusedIndicatorColor = Color.Transparent, // No focus indicator
                                unfocusedIndicatorColor = Color.Transparent, // No unfocused indicator
                                disabledIndicatorColor = Color.Transparent, // No disabled indicator
                                errorIndicatorColor = Color.Transparent // No error indicator
                            ),
                            singleLine = true,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ComponentThreeDContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        backgroundColor = Color(0xFF4A7C59),
                        shadowColor = Color(0xFF2E3B2F),
                        cornerRadius = 15.dp,
                        isPushable = true,
                        onClick = {
                            val user = userViewModel?.users?.find {
                                it.name == username && it.password == password
                            }
                            if (user == null) {
                                errorMessage = "User not found"
                                navController.navigate("main-menu") // TODO: Remove this
                            } else {
                                SessionManager.currentUser = user;
                                navController.navigate("main-menu")
                            }
                        }
                    ) {
                        Text("LOGIN", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        Text(
                            "No Account? ",
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                        Text(
                            text = "Sign-up",
                            color = Color(0xFFDAA520),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.Underline, // Adds the underline
                            modifier = Modifier.clickable {
                                navController.navigate("signup")
                            }
                        )
                    }
                }
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
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    ViewLogin()
}
