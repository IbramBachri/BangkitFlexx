package cap.tone.bangkitflexx.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import cap.tone.bangkitflexx.databinding.ActivitySplashBinding
import cap.tone.bangkitflexx.model.PreferencesModelFactory
import cap.tone.bangkitflexx.model.UserPreference
import cap.tone.bangkitflexx.ui.Intro.MainActivity
import cap.tone.bangkitflexx.ui.drawer.NavDrawActivity
import cap.tone.bangkitflexx.ui.signIn.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val handler = Handler(mainLooper)
        val pref = UserPreference.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, PreferencesModelFactory(pref))[SplashViewModel::class.java]
        handler.postDelayed({
            viewModel.getThemeSettings().observe(this@SplashActivity) { isDarkModeActive->
                if (isDarkModeActive) {
                    moveActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    moveActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }, 1000)
    }
    private fun moveActivity() {
        if (FirebaseAuth.getInstance().currentUser == null)
            startActivity<MainActivity>()
        else
            startActivity<NavDrawActivity>()
        finish()
    }
}