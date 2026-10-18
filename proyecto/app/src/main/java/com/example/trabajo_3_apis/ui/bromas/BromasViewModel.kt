package com.example.trabajo_3_apis.ui.bromas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabajo_3_apis.data.JokesRepository
import com.example.trabajo_3_apis.models.Broma
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BromasViewModel(
    private val repository: JokesRepository = JokesRepository()
) : ViewModel() {

    private val _bromas = MutableStateFlow<List<Broma>>(emptyList())
    val bromas: StateFlow<List<Broma>> = _bromas.asStateFlow()

    init {
        obtenerBromas()
    }

    fun obtenerBromas() {
        viewModelScope.launch {
            try {
                val listaBromas = repository.obtenerBromas()
                _bromas.value = listaBromas
            } catch (e: Exception) {
                Log.e("BromasViewModel", "Error al cargar bromas: ${e.message}", e)
            }
        }
    }
}
