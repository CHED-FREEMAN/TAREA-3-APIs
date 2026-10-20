package com.example.trabajo_3_apis.ui.perros

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabajo_3_apis.data.DogRepository
import com.example.trabajo_3_apis.data.local.DogDatabase
import com.example.trabajo_3_apis.data.local.DogFavoritoEntity
import com.example.trabajo_3_apis.models.Perro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PerrosViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = DogDatabase.obtener(application)
    private val repository = DogRepository(database.favoritoDao())

    private val _perros = MutableStateFlow<List<Perro>>(emptyList())
    val perros: StateFlow<List<Perro>> = _perros.asStateFlow()

    private val _favoritosIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoritosIds: StateFlow<Set<Int>> = _favoritosIds.asStateFlow()

    private val _favoritos = MutableStateFlow<List<DogFavoritoEntity>>(emptyList())
    val favoritos: StateFlow<List<DogFavoritoEntity>> = _favoritos.asStateFlow()

    init {
        obtenerPerros()
        observarFavoritos()
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

    private fun observarFavoritos() {
        viewModelScope.launch {
            repository.observarIdsPerrosFavoritos()?.collectLatest { ids ->
                _favoritosIds.value = ids.toSet()
            }
        }
        viewModelScope.launch {
            repository.observarPerrosFavoritos()?.collectLatest { favs ->
                _favoritos.value = favs
            }
        }
    }

    fun toggleFavorito(perro: Perro) {
        viewModelScope.launch {
            if (_favoritosIds.value.contains(perro.id)) {
                repository.eliminarFavorito(perro.id)
            } else {
                repository.agregarFavorito(perro)
            }
        }
    }
}
