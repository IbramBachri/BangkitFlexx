package cap.tone.bangkitflexx.ui.splash

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import cap.tone.bangkitflexx.model.PreferencesModelFactory
import cap.tone.bangkitflexx.model.UserPreference
import cap.tone.bangkitflexx.ui.drawer.NavDrawActivity
import cap.tone.bangkitflexx.ui.signIn.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = UserPreference.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, PreferencesModelFactory(pref))[SplashViewModel::class.java]
        viewModel.getThemeSettings().observe(this@SplashActivity) { isDarkModeActive->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        if (FirebaseAuth.getInstance().currentUser == null)
            startActivity<SignInActivity>()
        else
            startActivity<NavDrawActivity>()
        finish()
    }
}