package ru.laert.pokemons.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.AsyncImage
import ru.laert.pokemons.domain.model.Ability
import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.ui.theme.colorForType
import kotlin.div

@Composable
fun DetailScreen(
    pokemonId: Int,
    onBackClick: () -> Unit
) {
    val viewModel: DetailViewModel = viewModel(
        factory = viewModelFactory {
            initializer { DetailViewModel(pokemonId) }
        }
    )
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is DetailUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is DetailUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(state.message, color = MaterialTheme.colorScheme.error)
            }
        }
        is DetailUiState.Success -> {
            DetailContent(state.pokemon, onBackClick)
        }
    }
}

@Composable
private fun DetailContent(pokemon: Pokemon, onBackClick: () -> Unit) {
    val typeColor = colorForType(pokemon.types.firstOrNull() ?: "normal")
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(typeColor.copy(alpha = 0.25f))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))

            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(200.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pokemon.types.forEach { type ->
                    TypeChip(type)
                }
            }

            Spacer(Modifier.height(24.dp))

            InfoRow("Рост", "${pokemon.height / 10.0} м")
            InfoRow("Вес", "${pokemon.weight / 10.0} кг")

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Способности",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            pokemon.abilities.forEach { ability ->
                AbilityRow(ability)
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
        }
    }
}

@Composable
private fun TypeChip(type: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(colorForType(type))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = type.replaceFirstChar { it.uppercase() },
            color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun AbilityRow(ability: Ability) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = ability.name.replaceFirstChar { it.uppercase() })
        if (ability.isHidden) {
            Text(
                text = "скрытая",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}