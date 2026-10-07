package ru.laert.pokemons.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.data.repository.PokemonRepositoryImpl
import ru.laert.pokemons.domain.usecase.GetPokemonDetailsUseCase
import ru.laert.pokemons.ui.list.ListUiState

class DetailViewModel(pokemonId : Int) : ViewModel() {
    private val repository = PokemonRepositoryImpl()
    private val getPokemonDetailsUseCase = GetPokemonDetailsUseCase(repository)

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val pokemon = getPokemonDetailsUseCase(pokemonId)
                _uiState.value = DetailUiState.Success(pokemon)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }
}