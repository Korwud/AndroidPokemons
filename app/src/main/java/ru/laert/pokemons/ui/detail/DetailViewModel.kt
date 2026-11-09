package ru.laert.pokemons.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.domain.repository.FavoriteRepository
import ru.laert.pokemons.domain.usecase.GetPokemonDetailsUseCase

class DetailViewModel(
    private val pokemonId: Int,
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

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
                val cached = favoriteRepository.getFavoriteById(pokemonId)
                val pokemon = cached ?: getPokemonDetailsUseCase(pokemonId)
                _uiState.value = DetailUiState.Success(pokemon.toUiModel())
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun toggleFavorite(pokemon: DetailUiModel) {
        viewModelScope.launch {
            if (isFavorite.value) {
                favoriteRepository.removeFavorite(pokemon.id)
            } else {
                favoriteRepository.addFavorite(pokemon.toDomain())
            }
        }
    }
}

private fun Pokemon.toUiModel() = DetailUiModel(
    id = id,
    name = name.replaceFirstChar { it.uppercase() },
    imageUrl = imageUrl,
    types = types.map { TypeUi(it.replaceFirstChar { c -> c.uppercase() }) },
    height = "${height / 10.0} м",
    weight = "${weight / 10.0} кг",
    abilities = abilities.map {
        AbilityUi(
            name = it.name.replaceFirstChar { c -> c.uppercase() },
            isHiddenLabel = if (it.isHidden) "скрытая" else null
        )
    }
)

private fun DetailUiModel.toDomain() = Pokemon(
    id = id,
    name = name,
    imageUrl = imageUrl ?: "",
    types = types.map { it.name.lowercase() },
    height = (height.removeSuffix(" м").toDouble() * 10).toInt(),
    weight = (weight.removeSuffix(" кг").toDouble() * 10).toInt(),
    abilities = abilities.map {
        ru.laert.pokemons.domain.model.Ability(
            name = it.name,
            isHidden = it.isHiddenLabel != null
        )
    }
)