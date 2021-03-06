package cap.tone.bangkitflexx.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cap.tone.bangkitflexx.ui.Login.LoginViewModel
import cap.tone.bangkitflexx.model.UserPreference
import cap.tone.bangkitflexx.ui.Signup.SignupViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}