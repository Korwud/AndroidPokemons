package ru.laert.pokemons.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.laert.pokemons.data.repository.PokemonRepositoryImpl
import ru.laert.pokemons.domain.usecase.GetPokemonUseCase

class ListViewModel : ViewModel() {
    private val repository = PokemonRepositoryImpl()
    private val getPokemonUseCase = GetPokemonUseCase(repository)

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            try {
                val pokemons = getPokemonUseCase()
                _uiState.value = ListUiState.Success(pokemons)
            } catch (e: Exception){
                _uiState.value = ListUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}