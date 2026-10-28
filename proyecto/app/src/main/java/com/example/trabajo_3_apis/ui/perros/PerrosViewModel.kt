package com.example.trabajo_3_apis.ui.perros

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabajo_3_apis.data.DogRepository
import com.example.trabajo_3_apis.data.Resultado
import com.example.trabajo_3_apis.data.local.DogDatabase
import com.example.trabajo_3_apis.data.local.DogFavoritoEntity
import com.example.trabajo_3_apis.models.Perro
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private data class EstadoRed(
    val cargando: Boolean = true,
    val error: String? = null,
    val hayMas: Boolean = true
)

class PerrosViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = DogDatabase.obtener(application)
    private val repository = DogRepository(database.dogCacheDao(), database.favoritoDao())

    private val _estadoRed = MutableStateFlow(EstadoRed())
    private val _favoritosIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoritosIds: StateFlow<Set<Int>> = _favoritosIds.asStateFlow()

    private val _favoritos = MutableStateFlow<List<DogFavoritoEntity>>(emptyList())
    val favoritos: StateFlow<List<DogFavoritoEntity>> = _favoritos.asStateFlow()

    private val _totalFavoritos = MutableStateFlow(0)
    val totalFavoritos: StateFlow<Int> = _totalFavoritos.asStateFlow()

    val uiState: StateFlow<PerrosUiState> = combine(
        repository.observarPerros(),
        _estadoRed,
        repository.observarIdsPerrosFavoritos() ?: flowOf(emptyList())
    ) { perros, red, favoritosIdsList ->
        PerrosUiState(
            perros = perros,
            cargando = red.cargando,
            error = red.error,
            hayMas = red.hayMas,
            favoritosIds = favoritosIdsList.toSet()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PerrosUiState(cargando = true)
    )

    init {
        viewModelScope.launch {
            if (repository.hayPerrosGuardados()) {
                _estadoRed.update { it.copy(cargando = false) }
            } else {
                pedirSiguientePagina()
            }
            // Sincronizar favoritos desde la nube al iniciar
            repository.sincronizarFavoritosDesdeNube()
        }
        observarFavoritos()
    }

    fun cargarMas() {
        val red = _estadoRed.value
        if (red.cargando || !red.hayMas) return
        _estadoRed.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch { pedirSiguientePagina() }
    }

    fun refrescarPerros() {
        viewModelScope.launch {
            _estadoRed.update { it.copy(cargando = true, error = null) }
            try {
                database.dogCacheDao().eliminarTodos()
                repository.sincronizarFavoritosDesdeNube()
                pedirSiguientePagina()
            } catch (e: Exception) {
                _estadoRed.update { it.copy(cargando = false, error = e.message) }
            }
        }
    }

    private suspend fun pedirSiguientePagina() {
        when (val resultado = repository.cargarSiguientePagina(20)) {
            is Resultado.Exito -> _estadoRed.update {
                it.copy(cargando = false, error = null, hayMas = resultado.datos > 0)
            }
            is Resultado.Error -> _estadoRed.update {
                it.copy(cargando = false, error = resultado.mensaje)
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
        viewModelScope.launch {
            repository.contarFavoritos()?.collectLatest { count ->
                _totalFavoritos.value = count
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

    fun eliminarTodosLosFavoritos() {
        viewModelScope.launch {
            repository.eliminarTodosLosFavoritos()
        }
    }
}

data class PerrosUiState(
    val perros: List<Perro> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null,
    val hayMas: Boolean = true,
    val favoritosIds: Set<Int> = emptySet()
)
