package com.example.trabajo_3_apis.ui.caricaturas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabajo_3_apis.data.CartoonsRepository
import com.example.trabajo_3_apis.models.Caricatura
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CaricaturasViewModel(
    private val repository: CartoonsRepository = CartoonsRepository()
) : ViewModel() {

    private val _caricaturas = MutableStateFlow<List<Caricatura>>(emptyList())
    val caricaturas: StateFlow<List<Caricatura>> = _caricaturas.asStateFlow()

    init {
        obtenerCartoons()
    }

    fun obtenerCartoons() {
        viewModelScope.launch {
            try {
                val listaCartoons = repository.obtenerCaricaturas()
                _caricaturas.value = listaCartoons
            } catch (e: Exception) {
                Log.e("CaricaturasViewModel", "Error al cargar caricaturas: ${e.message}", e)
            }
        }
    }
}
