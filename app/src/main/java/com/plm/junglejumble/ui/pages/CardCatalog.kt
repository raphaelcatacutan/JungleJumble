package com.plm.junglejumble.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plm.junglejumble.R
import com.plm.junglejumble.ui.components.ComponentSwipeDetector
import com.plm.junglejumble.ui.components.ComponentThreeDContainer

data class AnimalCard(
    val id: Int,
    val name: String,
    val subtitle: String,
    val scientificName: String,
    val physicalCharacteristics: String,
    val habitat: String,
    val behavior: String,
    val funFact: String,
    val imageResId: Int,
    val color: Color
)
var animals = listOf(
    AnimalCard(
        id = 0,
        name = "Grumbles Honeyhug",
        subtitle = "Bear",
        scientificName = "Ursidae family",
        physicalCharacteristics = "Large body, powerful limbs, thick fur.",
        habitat = "Forests, tundras, and mountains around the world.",
        behavior = "Omnivorous, hibernate in winter (some species).",
        funFact = "Polar bears have black skin under their white fur for better heat absorption.",
        imageResId = R.drawable.bear,
        color = Color(0xFFD4E157)
    ),
    AnimalCard(
        id = 1,
        name = "Roary McMane",
        subtitle = "Lion",
        scientificName = "Panthera leo",
        physicalCharacteristics = "Large golden-brown coat, males have majestic manes.",
        habitat = "Grasslands, savannas, and woodlands of Africa and India.",
        behavior = "Social animals living in groups called prides.",
        funFact = "Lions roar to communicate, and their roar can be heard up to 5 miles away!",
        imageResId = R.drawable.lion,
        color = Color(0xFFFFEB3B)
    ),
    AnimalCard(
        id = 2,
        name = "Jumpington Hopper",
        subtitle = "Frog",
        scientificName = "Order Anura",
        physicalCharacteristics = "Smooth, moist skin, long legs for jumping.",
        habitat = "Found in various environments, especially near water.",
        behavior = "Amphibians, known for their croaks and leaping ability.",
        funFact = "Frogs absorb water through their skin, so they don’t need to drink!",
        imageResId = R.drawable.frog,
        color = Color(0xFFFFC107)
    ),
    AnimalCard(
        id = 3,
        name = "Swingy Bananasworth",
        subtitle = "Monkey",
        scientificName = "Infraorder Simiiformes",
        physicalCharacteristics = "Tail, grasping hands and feet, curious eyes.",
        habitat = "Tropical rainforests, savannas, or mountainous areas.",
        behavior = "Intelligent and social, often live in troops.",
        funFact = "Some monkeys, like capuchins, use tools to forage food!",
        imageResId = R.drawable.monkey,
        color = Color(0xFFFF9800)
    ),
    AnimalCard(
        id = 4,
        name = "Fuzzle Whiskerfox",
        subtitle = "Fox",
        scientificName = "Vulpes vulpes",
        physicalCharacteristics = "Pointed ears, bushy tail, reddish-brown fur.",
        habitat = "Forests, grasslands, mountains, and even urban areas.",
        behavior = "Solitary hunters, active mostly at night (nocturnal).",
        funFact = "Foxes can hear prey digging underground!",
        imageResId = R.drawable.fox,
        color = Color(0xFFFF5722)
    ),
    AnimalCard(
        id = 5,
        name = "Stripy Fierceclaw",
        subtitle = "Tiger",
        scientificName = "Panthera tigris",
        physicalCharacteristics = "Orange coat with black stripes, muscular build.",
        habitat = "Dense forests, grasslands, and mangroves of Asia.",
        behavior = "Solitary and territorial, excellent swimmers.",
        funFact = "No two tigers have the same stripe pattern—it’s like a fingerprint!",
        imageResId = R.drawable.tiger,
        color = Color(0xFFF44336)
    ),
    AnimalCard(
        id = 6,
        name = "Soarie Beakster",
        subtitle = "Eagle",
        scientificName = "Accipitridae family",
        physicalCharacteristics = "Sharp beak, powerful talons, excellent eyesight.",
        habitat = "Mountains, forests, and open areas near water.",
        behavior = "Skilled hunters, often soar high while searching for prey.",
        funFact = "Eagles can spot prey over 2 miles away!",
        imageResId = R.drawable.eagle,
        color = Color(0xFFE91E63)
    ),
    AnimalCard(
        id = 7,
        name = "Giggles Hyenalaugh",
        subtitle = "Hyena",
        scientificName = "Hyaenidae family",
        physicalCharacteristics = "Strong jaws, spotted or striped fur.",
        habitat = "Savannas, grasslands, and deserts of Africa and Asia.",
        behavior = "Known for their distinctive \"laugh\" and scavenging habits.",
        funFact = "Hyenas are more closely related to cats than dogs!",
        imageResId = R.drawable.hyena,
        color = Color(0xFFEC407A)
    ),
    AnimalCard(
        id = 8,
        name = "Bamboo Chompington",
        subtitle = "Panda",
        scientificName = "Ailuropoda melanoleuca",
        physicalCharacteristics = "Black-and-white fur, round face, large body.",
        habitat = "Bamboo forests in the mountains of China.",
        behavior = "Solitary and peaceful, they spend most of their day eating bamboo.",
        funFact = "A panda’s diet is 99% bamboo, even though they have carnivorous digestive systems!",
        imageResId = R.drawable.panda,
        color = Color(0xFFBA68C8)
    ),
    AnimalCard(
        id = 9,
        name = "Spotty Sneakpaw",
        subtitle = "Leopard",
        scientificName = "Panthera pardus",
        physicalCharacteristics = "Golden fur with black rosettes, athletic build.",
        habitat = "Savannas, forests, mountains, and deserts of Africa and Asia.",
        behavior = "Solitary and stealthy, excellent climbers.",
        funFact = "Leopards often hide their prey in trees to protect it from scavengers.",
        imageResId = R.drawable.cheetah,
        color = Color(0xFF9575CD)
    ),
    AnimalCard(
        id = 10,
        name = "Trunky Trombones",
        subtitle = "Elephant",
        scientificName = "Loxodonta africana / Elephas maximus",
        physicalCharacteristics = "Largest land animal, long trunk, big ears, tusks.",
        habitat = "Grasslands, forests, and savannas in Africa and Asia.",
        behavior = "Highly social and intelligent, live in herds.",
        funFact = "Elephants can recognize themselves in a mirror—a sign of self-awareness!",
        imageResId = R.drawable.elephant,
        color = Color(0xFF7E57C2)
    ),
    AnimalCard(
        id = 11,
        name = "Snappy Swampscale",
        subtitle = "Crocodile",
        scientificName = "Crocodylidae family",
        physicalCharacteristics = "Long body, strong jaws, armored scales.",
        habitat = "Freshwater rivers, lakes, and wetlands in tropical regions.",
        behavior = "Ambush predator, spends time basking in the sun.",
        funFact = "Crocodiles can hold their breath underwater for up to an hour!",
        imageResId = R.drawable.crocodile,
        color = Color(0xFF5C6BC0)
    ),
    AnimalCard(
        id = 12,
        name = "Lofty Neckreach",
        subtitle = "Giraffe",
        scientificName = "Giraffa camelopardalis",
        physicalCharacteristics = "Tallest land animal with a long neck and spotted coat.",
        habitat = "Savannas and woodlands of Africa.",
        behavior = "Feeds on tall trees, uses height for scanning predators.",
        funFact = "Giraffes have the same number of neck vertebrae as humans—just larger!",
        imageResId = R.drawable.giraffe,
        color = Color(0xFF42A5F5)
    ),
    AnimalCard(
        id = 13,
        name = "Mighty Ponderape",
        subtitle = "Gorilla",
        scientificName = "Gorilla genus",
        physicalCharacteristics = "Large and muscular with dark fur.",
        habitat = "Tropical rainforests and lowland swamps in Africa.",
        behavior = "Highly social, lives in family groups called troops.",
        funFact = "Gorillas use tools in the wild to obtain food!",
        imageResId = R.drawable.gorilla,
        color = Color(0xFF29B6F6)
    ),
    AnimalCard(
        id = 14,
        name = "Snuggly Treeko",
        subtitle = "Koala",
        scientificName = "Phascolarctos cinereus",
        physicalCharacteristics = "Small body, round ears, gray fur.",
        habitat = "Eucalyptus forests in Australia.",
        behavior = "Herbivorous, spends most time sleeping and eating leaves.",
        funFact = "Koalas have fingerprints that are very similar to humans'!",
        imageResId = R.drawable.koala,
        color = Color(0xFF26C6DA)
    ),
    AnimalCard(
        id = 15,
        name = "Rhiny Groundstomp",
        subtitle = "Rhino",
        scientificName = "Rhinocerotidae family",
        physicalCharacteristics = "Large body, thick skin, and horn(s) on the snout.",
        habitat = "Grasslands and savannas in Africa and Asia.",
        behavior = "Solitary grazers, territorial over water holes.",
        funFact = "A group of rhinos is called a crash!",
        imageResId = R.drawable.rhino,
        color = Color(0xFF26A69A)
    ),
    AnimalCard(
        id = 16,
        name = "Howly Moonpaws",
        subtitle = "Wolf",
        scientificName = "Canis lupus",
        physicalCharacteristics = "Bushy tail, thick fur, long legs.",
        habitat = "Forests, tundras, and grasslands around the Northern Hemisphere.",
        behavior = "Social animals living in packs, hunt cooperatively.",
        funFact = "Wolves communicate using howls, body language, and scent marking!",
        imageResId = R.drawable.wolf,
        color = Color(0xFF66BB6A)
    ),
    AnimalCard(
        id = 17,
        name = "Chirpy Beakfeather",
        subtitle = "Parrot",
        scientificName = "Psittaciformes order",
        physicalCharacteristics = "Brightly colored feathers, curved beak, zygodactyl feet.",
        habitat = "Tropical and subtropical regions, primarily rainforests.",
        behavior = "Highly social and intelligent, often mimic sounds.",
        funFact = "Parrots can live for over 50 years and some species are great talkers!",
        imageResId = R.drawable.parrot,
        color = Color(0xFF9CCC65)
    )
)

@Composable
fun ViewCardCatalog(navController: NavController = rememberNavController()) {
    val backgroundImage = painterResource(id = R.drawable.background1)

    var currentCardIndex by remember { mutableIntStateOf(0) }
    val currentCard = animals[currentCardIndex]

    ComponentSwipeDetector(
        onSwipeRight = {
            if (currentCardIndex > 0) currentCardIndex--
            else currentCardIndex = animals.size - 1
        },
        onSwipeLeft = {
            if (currentCardIndex < animals.size - 1) currentCardIndex++
            else currentCardIndex = 0
        }
    ) {
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
                    .padding(horizontal = 32.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ComponentAnimalCardDisplay(
                    animalCard = currentCard,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .fillMaxWidth()
                )

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
        }
    }

}

@Composable
fun ComponentAnimalCardDisplay(animalCard: AnimalCard, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp), // space from top of screen
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(top = 280.dp) // pushes the info box down so image overlaps
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ComponentThreeDContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                backgroundColor = Color(0xFFA5D6A7),
                shadowColor = Color(0xFF388E3C),
                cornerRadius = 15.dp,
                isPushable = false
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = animalCard.name,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            ComponentThreeDContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                backgroundColor = Color(0xFF5D4037 ),
                shadowColor = Color(0xFF3E2723 ),
                cornerRadius = 15.dp,
                isPushable = false
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ComponentLabeledInfo("Scientific Name", animalCard.scientificName)
                    ComponentLabeledInfo("Physical Characteristics", animalCard.physicalCharacteristics)
                    ComponentLabeledInfo("Habitat", animalCard.habitat)
                    ComponentLabeledInfo("Behavior", animalCard.behavior)
                    ComponentLabeledInfo("Fun Fact", animalCard.funFact, isFunFact = true)
                }
            }
        }

        // Image is on top and slightly overlaps the box below
        Image(
            painter = painterResource(id = animalCard.imageResId),
            contentDescription = animalCard.name,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 0.dp)
                .size(310.dp)
        )
    }
}

@Composable
fun ComponentLabeledInfo(label: String, value: String, isFunFact: Boolean = false) {
    Column(modifier = Modifier.padding(vertical = 2.dp, horizontal = 2.dp)) {
        Text(
            text = "$label:",
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 15.sp,
            fontStyle = if (isFunFact) FontStyle.Italic else FontStyle.Normal,
            lineHeight = 15.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewViewCardCatalog() {
    ViewCardCatalog()
}