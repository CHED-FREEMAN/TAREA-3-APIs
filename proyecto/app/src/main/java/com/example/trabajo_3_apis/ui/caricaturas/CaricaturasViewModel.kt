package com.example.trabajo_3_apis.ui.caricaturas

import androidx.lifecycle.ViewModel
import com.example.trabajo_3_apis.models.Caricatura
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CaricaturasViewModel : ViewModel() {
    private val _caricaturas = MutableStateFlow<List<Caricatura>>(
        listOf(
            Caricatura(title = "Batman: The Animated Series", year = 1992, image = "https://example.com/batman.jpg"),
            Caricatura(title = "X-Men", year = 1992, image = "https://example.com/xmen.jpg"),
            Caricatura(title = "Spider-Man", year = 1994, image = "https://example.com/spiderman.jpg")
        )
    )
    val caricaturas: StateFlow<List<Caricatura>> = _caricaturas.asStateFlow()
}
