package ru.laert.pokemons.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: Any,
    val icon: ImageVector,
    val label: String
)

val bottomNavItems = listOf(
    BottomNavItem(ListRoute, Icons.AutoMirrored.Filled.List, "Покемоны"),
    BottomNavItem(ProfileRoute, Icons.Default.AccountCircle, "Профиль"),
    BottomNavItem(SettingsRoute, Icons.Default.Settings, "Настройки")
)