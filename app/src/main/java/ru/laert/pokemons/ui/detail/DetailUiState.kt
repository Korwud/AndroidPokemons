package ru.laert.pokemons.ui.detail

import ru.laert.pokemons.domain.model.Pokemon

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val pokemon: Pokemon) : DetailUiState
    data class Error(val message: String) : DetailUiState
}