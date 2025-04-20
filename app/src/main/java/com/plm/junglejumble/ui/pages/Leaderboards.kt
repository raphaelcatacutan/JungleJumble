package com.plm.junglejumble.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plm.junglejumble.R
import com.plm.junglejumble.database.models.Score
import com.plm.junglejumble.ui.components.ComponentPopupContainer
import com.plm.junglejumble.ui.components.ComponentThreeDContainer
import com.plm.junglejumble.ui.components.CrackedShape
import com.plm.junglejumble.utils.SessionManager.scoreViewModel
import com.plm.junglejumble.utils.SessionManager.userViewModel

@Composable
fun ViewLeaderBoard(navController: NavController = rememberNavController()) {
    val backgroundImage = painterResource(id = R.drawable.background1)

    val logoImage = painterResource(id = R.drawable.leaderboards)
    val leaderboardEntries: List<Score> = scoreViewModel?.scores
        ?.sortedByDescending { it.score }
        ?.take(10)
        ?: emptyList()

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
                .padding(
                    horizontal = 15.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Spacer(modifier = Modifier.height(200.dp))

            ComponentPopupContainer(
                modifier = Modifier
                    .size(width = 315.dp, height = 530.dp),
                backgroundColor = Color(0xFF8B4513),
                shadowColor = Color(0xFF644E40),
                shape = CrackedShape,
                isPushable = false,
                image = painterResource(R.drawable.ggg)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    Column {
                        LazyColumn {
                            itemsIndexed(leaderboardEntries) { index, entry ->
                                ComponentLeaderboardRow(entry, index + 1)
                            }
                        }

                    }
                }
            }

            ComponentThreeDContainer(
                modifier = Modifier
                    .width(55.dp)
                    .height(55.dp),
                backgroundColor = Color(0xFF78909C),
                shadowColor = Color(0xFF546E7A),
                cornerRadius = 15.dp,
                isPushable = true,
                onClick = { navController.navigate("main-menu") }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(top = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = logoImage,
                contentDescription = "Logo",
                modifier = Modifier
                    .size(295.dp)
            )
        }
    }
}

@Composable
fun ComponentLeaderboardRow(entry: Score, rank: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
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
                    fontSize = 20.sp,
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
        ComponentThreeDContainer(
            modifier = Modifier
                .width(100.dp)
                .height(35.dp),
            backgroundColor = when(rank) {
                1 -> Color(0xFFA86D01)
                2 -> Color(0xFF4A4A4A)
                3 -> Color(0xFF6E3B3B)
                else -> Color(0xFF8E6A3D)
            },
            shadowColor = when(rank) {
                1 -> Color(0xFF3E2C18)
                2 -> Color(0xFF2C2C2C)
                3 -> Color(0xFF3D1F1F)
                else -> Color(0xFF6A4E2F)
            },
            cornerRadius = 10.dp,
            isPushable = false
        ) {
            Text(
                text = userViewModel?.users?.find { it.id == entry.ownerId}?.name ?: "None",
                fontSize = 16.sp,
                color = Color.White,
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
                fontWeight = FontWeight.Bold,
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