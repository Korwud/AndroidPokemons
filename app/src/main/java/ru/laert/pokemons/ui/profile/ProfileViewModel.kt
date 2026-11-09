package ru.laert.pokemons.ui.profile

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.laert.pokemons.domain.model.UserProfile
import ru.laert.pokemons.domain.repository.ProfileRepository
import java.io.File

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val context: Context
) : ViewModel() {

    val profile: StateFlow<UserProfile> = profileRepository.profileFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProfile()
        )

    fun downloadAndOpenResume(url: String) {
        if (url.isBlank()) return

        viewModelScope.launch {
            try {
                val file = profileRepository.downloadResume(url)
                openPdf(context, file)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun openPdf(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}