package com.example.trabajo_3_apis.ui.bromas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trabajo_3_apis.models.Broma

@Composable
fun BromasView(viewModel: BromasViewModel = BromasViewModel()) {
    val bromas by viewModel.bromas.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(bromas) { broma ->
            BromaCard(broma = broma)
        }
    }
}

@Composable
fun BromaCard(broma: Broma) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = broma.setup, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = broma.punchline, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
