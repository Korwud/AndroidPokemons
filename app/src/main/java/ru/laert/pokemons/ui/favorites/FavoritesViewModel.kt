package ru.laert.pokemons.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.domain.repository.FavoriteRepository

class FavoritesViewModel(
    favoriteRepository: FavoriteRepository
) : ViewModel() {

    val favorites: StateFlow<List<Pokemon>> = favoriteRepository.getFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}