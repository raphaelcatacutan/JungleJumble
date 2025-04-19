package com.plm.junglejumble

import CardGrid
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.plm.junglejumble.database.viewmodels.ScoreViewModel
import com.plm.junglejumble.database.viewmodels.ScoreViewModelFactory
import com.plm.junglejumble.database.viewmodels.UserViewModel
import com.plm.junglejumble.database.viewmodels.UserViewModelFactory
import com.plm.junglejumble.ui.pages.TestPage
import com.plm.junglejumble.ui.pages.ViewCardCatalog
import com.plm.junglejumble.ui.pages.ViewGame
import com.plm.junglejumble.ui.pages.ViewLeaderBoard
import com.plm.junglejumble.ui.pages.ViewLogin
import com.plm.junglejumble.ui.pages.ViewMainMenu
import com.plm.junglejumble.ui.pages.ViewSignup
import com.plm.junglejumble.ui.theme.JungleJumbleTheme
import com.plm.junglejumble.utils.SessionManager

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

    val context = LocalContext.current

    val userDao = AppDatabase.getDatabase(context).userDao()
    val scoreDao = AppDatabase.getDatabase(context).scoreDao()

    // Create ViewModel with factory
    val u: UserViewModel = viewModel(
        factory = UserViewModelFactory(userDao)
    )
    val s: ScoreViewModel = viewModel(
        factory = ScoreViewModelFactory(scoreDao)
    )
    SessionManager.userViewModel = u
    SessionManager.scoreViewModel = s

    u.loadUsers()
    s.loadScores()

    NavHost(navController = navController, startDestination = "main-menu") {
        composable("test") { TestPage() }
        composable("login") { ViewLogin(navController) }
        composable("signup") { ViewSignup(navController) }
        composable("main-menu") { ViewMainMenu(navController) }
        composable("leaderboard") { ViewLeaderBoard(navController) }
        composable("game/{cardNumber}/{duration}") { backStackEntry ->
            val cardNumber = backStackEntry.arguments?.getString("cardNumber")?.toInt() ?: 16
            val duration = backStackEntry.arguments?.getString("duration")?.toInt() ?: 120
            ViewGame(cardNumber, duration, navController)
        }
        composable("card_catalog") { ViewCardCatalog(navController) }
        composable("card-test") { CardGrid() }
    }

    AnimatedContent(targetState = navController.currentBackStackEntry) { _ ->
        // Pang animate ng pages
        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
            //customize animations for each composable
        }
    }
}
