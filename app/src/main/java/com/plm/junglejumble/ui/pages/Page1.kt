package com.plm.junglejumble.ui.pages

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Page1(navController: NavController = rememberNavController()) {
    Button(onClick = { navController.navigate("page2") }) {
        Text(text = "Go to Page2")
    }
}