package ru.laert.pokemons.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.laert.pokemons.domain.model.UserProfile
import ru.laert.pokemons.domain.repository.ProfileRepository
import java.io.File

sealed interface ProfileEvent {
    data class OpenPdf(val file: File) : ProfileEvent
    data class ShowError(val message: String) : ProfileEvent
}

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val profile: StateFlow<UserProfile> = profileRepository.profileFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProfile()
        )

    private val _events = Channel<ProfileEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun downloadAndOpenResume(url: String) {
        if (url.isBlank()) return

        viewModelScope.launch {
            try {
                val file = profileRepository.downloadResume(url)
                _events.send(ProfileEvent.OpenPdf(file))
            } catch (e: Exception) {
                e.printStackTrace()
                _events.send(
                    ProfileEvent.ShowError(
                        "Не удалось открыть резюме: ${e.message ?: "неизвестная ошибка"}"
                    )
                )
            }
        }
    }
}