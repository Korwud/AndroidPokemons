package ru.laert.pokemons.ui.list

import ru.laert.pokemons.domain.model.Pokemon

sealed interface ListUiState {
    data object Loading : ListUiState
    data class Success(val pokemons: List<Pokemon>) : ListUiState
    data class Error(val message: String) : ListUiState
}