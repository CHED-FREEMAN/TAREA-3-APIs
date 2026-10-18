package com.example.trabajo_3_apis.ui.perros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trabajo_3_apis.models.Perro

@Composable
fun PerrosView(viewModel: PerrosViewModel = PerrosViewModel()) {
    val perros by viewModel.perros.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(perros) { perro ->
            PerroCard(perro = perro)
        }
    }
}

@Composable
fun PerroCard(perro: Perro) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Imagen URL: ${perro.message}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
