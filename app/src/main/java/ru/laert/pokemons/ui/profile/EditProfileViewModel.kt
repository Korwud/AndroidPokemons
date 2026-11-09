package ru.laert.pokemons.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.laert.pokemons.domain.model.UserProfile
import ru.laert.pokemons.domain.repository.ProfileRepository

class EditProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow(UserProfile())
    val profileState: StateFlow<UserProfile> = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            profileRepository.profileFlow.collect { savedProfile ->
                _profileState.value = savedProfile
            }
        }
    }

    fun updateName(name: String) {
        _profileState.value = _profileState.value.copy(fullName = name)
    }

    fun updateRank(rank: String) {
        _profileState.value = _profileState.value.copy(rank = rank)
    }

    fun updateResumeUrl(url: String) {
        _profileState.value = _profileState.value.copy(resumeUrl = url)
    }

    fun updateAvatarUri(uri: String) {
        _profileState.value = _profileState.value.copy(avatarUri = uri)
    }

    fun saveProfile(onSaved: () -> Unit) {
        viewModelScope.launch {
            profileRepository.saveProfile(_profileState.value)
            onSaved()
        }
    }
}