package cap.tone.bangkitflexx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cap.tone.bangkitflexx.Login.LoginViewModel
import cap.tone.bangkitflexx.Model.UserPreference
import cap.tone.bangkitflexx.Signup.SignupViewModel
import cap.tone.bangkitflexx.StoryChat.StoryChatViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StoryChatViewModel::class.java) -> {
                StoryChatViewModel(pref) as T
            }
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