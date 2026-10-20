package ru.laert.pokemons.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.data.repository.PokemonRepositoryImpl
import ru.laert.pokemons.domain.repository.FavoriteRepository
import ru.laert.pokemons.domain.usecase.GetPokemonDetailsUseCase
import ru.laert.pokemons.ui.list.ListUiState

class DetailViewModel(
    pokemonId : Int,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val repository = PokemonRepositoryImpl()
    private val getPokemonDetailsUseCase = GetPokemonDetailsUseCase(repository)

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    val isFavorite: StateFlow<Boolean> = favoriteRepository.isFavorite(pokemonId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val cachedPokemon = favoriteRepository.getFavoriteById(pokemonId)
                if (cachedPokemon != null) {
                    _uiState.value = DetailUiState.Success(cachedPokemon)
                } else {
                    val pokemon = getPokemonDetailsUseCase(pokemonId)
                    _uiState.value = DetailUiState.Success(pokemon)
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun toggleFavorite(pokemon: Pokemon) {
        viewModelScope.launch {
            if (isFavorite.value) {
                favoriteRepository.removeFavorite(pokemon.id)
            } else {
                favoriteRepository.addFavorite(pokemon)
            }
        }
    }
}