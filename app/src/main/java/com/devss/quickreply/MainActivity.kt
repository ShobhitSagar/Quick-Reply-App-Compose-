package com.devss.quickreply

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.airbnb.lottie.compose.*
import com.devss.quickreply.ui.theme.QuickReplyTheme

class MainActivity : ComponentActivity() {
    private val demo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (isSystemInDarkTheme()) {
                this.window.statusBarColor = ContextCompat.getColor(this, R.color.black)
            }
            QuickReplyTheme {
                if (demo) LottieAnimation()
                else MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Quick Reply")
            },
                actions = { MainMenu() })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { sToast("Fab Clicked", context) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "Hello World!")
        }
    }
}

fun sToast(msg: String, context: Context) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

@Composable
fun MainMenu() {
    val context = LocalContext.current

    var showMenu by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { sToast("Hehe!", context) }) {
        Icon(imageVector = Icons.Default.Favorite, contentDescription = "")
    }
    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
            DropdownMenuItem(onClick = {
                sToast("Profile Clicked", context)
                showMenu = false
            }) {
                Text(text = "Profile")
            }
            DropdownMenuItem(onClick = {
                sToast("Settings Clicked", context)
                showMenu = false
            }) {
                Text(text = "Settings")
            }
        }
    }
}

@Composable
fun LottieAnimation() {
    var isLottiePlaying by remember {
        mutableStateOf(true)
    }
    var animationSpeed by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.lottie_under_construction)
    )

    // to control the lottie animation
    val lottieAnimation by animateLottieCompositionAsState(
        // pass the composition created above
        composition,
        // Iterates Forever
        iterations = LottieConstants.IterateForever,
        // Lottie and pause/play
        isPlaying = isLottiePlaying,
        // Increasing the speed of change Lottie
        speed = animationSpeed,
        restartOnPlay = false

    )

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pass the composition and the progress state
        LottieAnimation(
            composition,
            lottieAnimation,
            modifier = Modifier.size(300.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuickReplyTheme {
        MyApp()
    }
}