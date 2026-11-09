package ru.laert.pokemons.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.laert.pokemons.domain.cache.BadgeCache
import ru.laert.pokemons.domain.model.PokemonFilter
import ru.laert.pokemons.domain.repository.PokemonRepository
import ru.laert.pokemons.domain.repository.SettingsRepository
import ru.laert.pokemons.domain.usecase.GetPokemonUseCase

class ListViewModel(
    private val settingsRepository: SettingsRepository,
    private val getPokemonUseCase: GetPokemonUseCase,
    private val pokemonRepository: PokemonRepository,
    badgeCache: BadgeCache
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    val isFilterActive: StateFlow<Boolean> = badgeCache.isFilterActive
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

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