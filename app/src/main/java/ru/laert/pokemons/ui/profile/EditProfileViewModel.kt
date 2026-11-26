package ru.laert.pokemons.ui.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.laert.pokemons.domain.model.UserProfile
import ru.laert.pokemons.domain.repository.ProfileRepository
import java.io.File

class EditProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val appContext: Context
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

    fun updateAvatarUri(uri: Uri) {
        viewModelScope.launch {
            val path = copyUriToInternalStorage(appContext, uri)
            if (path != null) {
                _profileState.update { it.copy(avatarUri = path) }
            }
        }
    }

    fun saveProfile(onSaved: () -> Unit) {
        viewModelScope.launch {
            profileRepository.saveProfile(_profileState.value)
            onSaved()
        }
    }

    suspend fun copyUriToInternalStorage(
        context: Context,
        uri: Uri,
        fileName: String = "avatar_${System.currentTimeMillis()}.jpg"
    ): String? = withContext(Dispatchers.IO) {
        runCatching {
            val destDir = File(context.filesDir, "avatars").apply { mkdirs() }
            val destFile = File(destDir, fileName)
            context.contentResolver.openInputStream(uri)?.use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            } ?: return@runCatching null
            destFile.absolutePath
        }.getOrNull()
    }
}