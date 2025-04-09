package com.plm.junglejumble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plm.junglejumble.ui.pages.Page1
import com.plm.junglejumble.ui.pages.Page2
import com.plm.junglejumble.ui.theme.JungleJumbleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JungleJumbleTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Add nalang dito ng mga pages
    NavHost(navController = navController, startDestination = "page1") {
        composable("page1") { Page1(navController) }
        composable("page2") { Page2(navController) }
    }

    AnimatedContent(targetState = navController.currentBackStackEntry) { _ ->
        // Pang animate ng pages
        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
            //customize animations for each composable
        }
    }
}
