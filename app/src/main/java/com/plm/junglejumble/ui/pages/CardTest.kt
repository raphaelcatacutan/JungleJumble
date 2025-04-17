import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CardGrid() {
    var cards by remember {
        mutableStateOf(
            List(16) { index -> CardItem(id = index, isSelected = false) }
        )
    }

    var selectedIndices by remember { mutableStateOf<List<Int>>(emptyList()) }
    var isProcessing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cards.size) { index ->
            val card = cards[index]
            CardComposable(
                isSelected = card.isSelected,
                onClick = {
                    if (isProcessing || card.isSelected) return@CardComposable

                    // Select the card
                    selectedIndices = selectedIndices + index
                    cards = cards.mapIndexed { i, item ->
                        if (i == index) item.copy(isSelected = true) else item
                    }

                    // If 2 cards are selected, start delay and reset
                    if (selectedIndices.size == 2) {
                        isProcessing = true
                        coroutineScope.launch {
                            delay(1000L)

                            cards = cards.mapIndexed { i, item ->
                                if (selectedIndices.contains(i)) item.copy(isSelected = false)
                                else item
                            }

                            selectedIndices = emptyList()
                            isProcessing = false
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CardComposable(isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color.Green else Color.Gray)
            .clickable { onClick() }
    )
}

data class CardItem(
    val id: Int,
    val isSelected: Boolean
)
