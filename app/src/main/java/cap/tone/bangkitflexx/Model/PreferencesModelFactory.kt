package cap.tone.bangkitflexx.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cap.tone.bangkitflexx.ui.settings.SettingsViewModel
import cap.tone.bangkitflexx.ui.splash.SplashViewModel

class PreferencesModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}