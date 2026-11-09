package ru.laert.pokemons.ui.detail

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val pokemon: DetailUiModel) : DetailUiState
    data class Error(val message: String) : DetailUiState
}

data class DetailUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val types: List<TypeUi>,
    val height: String,
    val weight: String,
    val abilities: List<AbilityUi>
)

data class TypeUi(val name: String)

data class AbilityUi(
    val name: String,
    val isHiddenLabel: String?
)