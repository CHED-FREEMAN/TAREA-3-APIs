package com.example.trabajo_3_apis.ui.perros

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabajo_3_apis.data.DogRepository
import com.example.trabajo_3_apis.models.Perro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerrosViewModel(
    private val repository: DogRepository = DogRepository()
) : ViewModel() {

    private val _perros = MutableStateFlow<List<Perro>>(emptyList())
    val perros: StateFlow<List<Perro>> = _perros.asStateFlow()

    init {
        obtenerPerros()
    }

    fun obtenerPerros() {
        viewModelScope.launch {
            try {
                val listaPerros = repository.obtenerPerros(50)
                _perros.value = listaPerros
            } catch (e: Exception) {
                Log.e("PerrosViewModel", "Error al cargar perros: ${e.message}", e)
            }
        }
    }
}
