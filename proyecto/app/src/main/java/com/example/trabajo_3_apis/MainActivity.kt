package com.example.trabajo_3_apis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trabajo_3_apis.ui.auth.AuthViewModel
import com.example.trabajo_3_apis.ui.auth.LoginScreen
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
                AuthApp()
            }
        }
    }
}

sealed class Screen(val route: String, val title: String) {
    object Perros : Screen("perros", "Perritos")
    object Caricaturas : Screen("caricaturas", "Caricaturas")
    object Bromas : Screen("bromas", "Bromas")
}

@Composable
fun AuthApp(authViewModel: AuthViewModel = viewModel()) {
    val usuario by authViewModel.usuario.collectAsState()
    val haySesion = usuario != null

    if (!haySesion) {
        LoginScreen(viewModel = authViewModel)
    } else {
        MainApp(
            correoUsuario = usuario?.email.orEmpty(),
            onCerrarSesion = authViewModel::cerrarSesion
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    correoUsuario: String = "",
    onCerrarSesion: () -> Unit = {}
) {
    val items = listOf(
        Screen.Perros,
        Screen.Caricaturas,
        Screen.Bromas
    )
    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Perros) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = selectedScreen.title)
                        if (correoUsuario.isNotEmpty()) {
                            Text(correoUsuario, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onCerrarSesion) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
