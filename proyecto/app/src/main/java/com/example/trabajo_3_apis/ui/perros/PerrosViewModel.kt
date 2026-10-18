package com.example.trabajo_3_apis.ui.perros

import androidx.lifecycle.ViewModel
import com.example.trabajo_3_apis.models.Perro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PerrosViewModel : ViewModel() {
    private val _perros = MutableStateFlow<List<Perro>>(
        listOf(
            Perro(message = "https://images.dog.ceo/breeds/retriever-golden/n02099601_3004.jpg"),
            Perro(message = "https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg"),
            Perro(message = "https://images.dog.ceo/breeds/poodle-standard/n02113799_2280.jpg")
        )
    )
    val perros: StateFlow<List<Perro>> = _perros.asStateFlow()
}
