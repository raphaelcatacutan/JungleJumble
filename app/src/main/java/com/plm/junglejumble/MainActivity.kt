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
import androidx.room.Room
import com.plm.junglejumble.ui.pages.ViewCardCatalog
import com.plm.junglejumble.ui.pages.ViewGame
import com.plm.junglejumble.ui.pages.ViewLeaderBoard
import com.plm.junglejumble.ui.pages.ViewLogin
import com.plm.junglejumble.ui.pages.ViewMainMenu
import com.plm.junglejumble.ui.pages.ViewSignup
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

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { ViewLogin(navController) }
        composable("signup") { ViewSignup(navController) }
        composable("main-menu") { ViewMainMenu(navController) }
        composable("leaderboard") { ViewLeaderBoard(navController) }
        composable("game") { ViewGame(navController) }
        composable("card_catalog") { ViewCardCatalog(navController) }
    }

    AnimatedContent(targetState = navController.currentBackStackEntry) { _ ->
        // Pang animate ng pages
        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
            //customize animations for each composable
        }
    }
}
