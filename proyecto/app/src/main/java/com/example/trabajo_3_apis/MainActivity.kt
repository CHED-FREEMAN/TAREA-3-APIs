package com.example.trabajo_3_apis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.trabajo_3_apis.ui.bromas.BromasView
import com.example.trabajo_3_apis.ui.caricaturas.CaricaturasView
import com.example.trabajo_3_apis.ui.perros.PerrosView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkTheme = isSystemInDarkTheme()
            val colorScheme = if (darkTheme) {
                darkColorScheme()
            } else {
                lightColorScheme()
            }
            MaterialTheme(colorScheme = colorScheme) {
                MainApp()
            }
        }
    }
}

sealed class Screen(val route: String, val title: String) {
    object Perros : Screen("perros", "Perritos")
    object Caricaturas : Screen("caricaturas", "Caricaturas")
    object Bromas : Screen("bromas", "Bromas")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val items = listOf(
        Screen.Perros,
        Screen.Caricaturas,
        Screen.Bromas
    )
    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Perros) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = selectedScreen.title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            when (screen) {
                                Screen.Perros -> Icon(Icons.Default.Pets, contentDescription = screen.title)
                                Screen.Caricaturas -> Icon(Icons.Default.Star, contentDescription = screen.title)
                                Screen.Bromas -> Icon(Icons.Default.Face, contentDescription = screen.title)
                            }
                        },
                        label = { Text(screen.title) },
                        selected = selectedScreen == screen,
                        onClick = { selectedScreen = screen }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedScreen) {
                Screen.Perros -> PerrosView()
                Screen.Caricaturas -> CaricaturasView()
                Screen.Bromas -> BromasView()
            }
        }
    }
}
