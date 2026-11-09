package ru.laert.pokemons.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.laert.pokemons.data.model.Pokemon
import ru.laert.pokemons.data.repository.PokemonRepository

class DetailViewModel(pokemonId: Int) : ViewModel() {
    private val repository = PokemonRepository()

    private val _pokemon = MutableStateFlow<DetailUiModel?>(null)
    val pokemon: StateFlow<DetailUiModel?> = _pokemon.asStateFlow()

    init {
        _pokemon.value = repository.getPokemonById(pokemonId)?.toUiModel()
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