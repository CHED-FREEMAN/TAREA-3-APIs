package com.example.trabajo_3_apis.ui.perros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.trabajo_3_apis.models.Perro

@Composable
fun PerrosView(viewModel: PerrosViewModel = viewModel()) {
    val perros by viewModel.perros.collectAsState()
    val favoritosIds by viewModel.favoritosIds.collectAsState()
    val favoritos by viewModel.favoritos.collectAsState()

    var soloFavoritos by remember { mutableStateOf(false) }

    val listaAMostrar = if (soloFavoritos) {
        favoritos.map { Perro(message = it.imagenUrl) }
    } else {
        perros
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (soloFavoritos) "Perros Favoritos (${favoritos.size})" else "Todos los Perros",
                style = MaterialTheme.typography.titleMedium
            )
            FilterChip(
                selected = soloFavoritos,
                onClick = { soloFavoritos = !soloFavoritos },
                label = { Text(if (soloFavoritos) "Ver Todos" else "Ver Favoritos") },
                leadingIcon = {
                    Icon(
                        imageVector = if (soloFavoritos) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (soloFavoritos) Color.Red else LocalContentColor.current
                    )
                }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listaAMostrar) { perro ->
                val esFavorito = favoritosIds.contains(perro.id)
                PerroCard(
                    perro = perro,
                    esFavorito = esFavorito,
                    onToggleFavorito = { viewModel.toggleFavorito(perro) }
                )
            }
        }
    }
}

@Composable
fun PerroCard(
    perro: Perro,
    esFavorito: Boolean,
    onToggleFavorito: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(560.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = perro.message,
                contentDescription = "Imagen de perro",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = onToggleFavorito,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (esFavorito) Color.Red else Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
