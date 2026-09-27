package ru.laert.pokemons.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.laert.pokemons.ui.detail.DetailScreen
import ru.laert.pokemons.ui.list.ListScreen
import androidx.navigation.toRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ListRoute,
        modifier = modifier
    ) {
        composable<ListRoute> {
            ListScreen(
                onPokemonClick = { pokemonId ->
                    navController.navigate(DetailRoute(pokemonId))
                }
            )
        }

        composable<DetailRoute> { backStackEntry ->
            val detailRoute : DetailRoute = backStackEntry.toRoute()
            DetailScreen(
                pokemonId = detailRoute.pokemonId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<ProfileRoute> {
            PlaceholderScreen("Профиль — скоро")
        }

        composable<SettingsRoute> {
            PlaceholderScreen("Настройки — скоро")
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.titleLarge)
    }
}