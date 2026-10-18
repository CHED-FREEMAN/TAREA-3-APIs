package com.example.trabajo_3_apis.ui.bromas

import androidx.lifecycle.ViewModel
import com.example.trabajo_3_apis.models.Broma
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BromasViewModel : ViewModel() {
    private val _bromas = MutableStateFlow<List<Broma>>(
        listOf(
            Broma(setup = "¿Por qué los programadores prefieren el invierno?", punchline = "Porque hace frío en el servidor."),
            Broma(setup = "¿Qué hace un programador cuando está triste?", punchline = "Se compila."),
            Broma(setup = "Hay 10 tipos de personas en el mundo...", punchline = "Los que entienden binario y los que no.")
        )
    )
    val bromas: StateFlow<List<Broma>> = _bromas.asStateFlow()
}
