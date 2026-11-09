package ru.laert.pokemons.domain.cache

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.laert.pokemons.domain.repository.SettingsRepository

class BadgeCache(
    private val settingsRepository: SettingsRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    private val _isFilterActive = MutableStateFlow(false)
    val isFilterActive: StateFlow<Boolean> = _isFilterActive.asStateFlow()

    init {
        scope.launch {
            settingsRepository.filterFlow.collect { filter ->
                _isFilterActive.value = !filter.isDefault
            }
        }
    }

    fun updateBadgeState(isActive: Boolean) {
        _isFilterActive.value = isActive
    }
}