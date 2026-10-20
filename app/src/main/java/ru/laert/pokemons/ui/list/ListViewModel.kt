package ru.laert.pokemons.ui.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.laert.pokemons.data.repository.PokemonRepositoryImpl
import ru.laert.pokemons.data.repository.SettingsRepositoryImpl
import ru.laert.pokemons.domain.cache.BadgeCache
import ru.laert.pokemons.domain.model.PokemonFilter
import ru.laert.pokemons.domain.repository.SettingsRepository
import ru.laert.pokemons.domain.usecase.GetPokemonUseCase

class ListViewModel(
    context: Context,
    private val settingsRepository: SettingsRepository,
    val badgeCache: BadgeCache
) : ViewModel() {
    private val pokemonRepository = PokemonRepositoryImpl()
    private val getPokemonUseCase = GetPokemonUseCase(pokemonRepository)

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.filterFlow.collect { filter ->
                loadPokemons(filter)
            }
        }
    }

    private suspend fun loadPokemons(filter: PokemonFilter) {
        _uiState.value = ListUiState.Loading
        try {
            val pokemons = if (filter.types.isEmpty()) {
                getPokemonUseCase()
            } else {
                filter.types.flatMap { type ->
                    pokemonRepository.getPokemonsByType(type)
                }.distinctBy { it.id }.sortedBy { it.id }
            }
            _uiState.value = ListUiState.Success(pokemons)
        } catch (e: Exception) {
            _uiState.value = ListUiState.Error(e.message ?: "Ошибка загрузки")
        }
    }
}