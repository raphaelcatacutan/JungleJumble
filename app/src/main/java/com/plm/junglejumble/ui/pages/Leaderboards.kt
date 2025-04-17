package com.plm.junglejumble.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plm.junglejumble.R
import com.plm.junglejumble.database.models.Score
import com.plm.junglejumble.utils.SessionManager.scoreViewModel
import com.plm.junglejumble.utils.SessionManager.userViewModel

data class LeaderboardEntry(
    val rank: Int,
    val name: String,
    val score: Int,
    val isTopThree: Boolean = false
)

@Composable
fun ViewLeaderBoard(navController: NavController = rememberNavController()) {
    val backgroundImage = painterResource(id = R.drawable.background1)

    val leaderboardEntries: List<Score> = scoreViewModel?.scores
        ?.sortedByDescending { it.score }
        ?: emptyList()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Background jungle
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title banner
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF4CAF50))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "LEADERBOARD",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Leaderboard content with rounded green background
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF4CAF50))
                    .padding(16.dp)
            ) {
                Column {
                    // Header row
                    ComponentLeaderboardHeader()

                    Spacer(modifier = Modifier.height(8.dp))

                    // List of entries
                    LazyColumn {
                        itemsIndexed(leaderboardEntries) { index, entry ->
                            ComponentLeaderboardRow(entry, index + 1)
                        }
                    }

                }
            }

            // Close button at the bottom
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF388E3C), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ComponentLeaderboardHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "RANK",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "NAME",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "SCORE",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ComponentLeaderboardRow(entry: Score, rank: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rank (with medals for top 3)
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (rank <= 3) {
                // Using medal emojis for top 3
                Text(
                    text = when(rank) {
                        1 -> "🏅"
                        2 -> "🥈"
                        3 -> "🥉"
                        else -> rank.toString()
                    },
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = rank.toString(),
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Name field with light gray background
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray.copy(alpha = 0.7f))
                .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userViewModel?.users?.find { it.id == entry.ownerId}?.name ?: "None",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        // Score field
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = entry.score.toString(),
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLeaderBoard() {
    ViewLeaderBoard()
}