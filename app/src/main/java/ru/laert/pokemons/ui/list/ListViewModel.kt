package ru.laert.pokemons.ui.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.laert.pokemons.data.repository.PokemonRepository

class ListViewModel : ViewModel() {
    private val repository = PokemonRepository()

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        _uiState.value = ListUiState.Success(repository.getPokemons())
    }
}