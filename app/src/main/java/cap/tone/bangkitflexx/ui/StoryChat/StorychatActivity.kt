package cap.tone.bangkitflexx.ui.StoryChat

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import cap.tone.bangkitflexx.Model.UserPreference
import cap.tone.bangkitflexx.MotionLayout.MainActivity
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.helper.ViewModelFactory
import cap.tone.bangkitflexx.databinding.ActivityStorychatBinding


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class StorychatActivity : AppCompatActivity() {
    private lateinit var mainViewModel: StoryChatViewModel
    private lateinit var binding: ActivityStorychatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorychatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[StoryChatViewModel::class.java]

        mainViewModel.getUser().observe(this, { user ->
            if (user.isLogin){
                binding.nameTextView.text = getString(R.string.greeting, user.name)
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            mainViewModel.logout()
        }
    }
    private fun playAnimation() {

        val masage = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val logot = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(500)



        AnimatorSet().apply {
            playSequentially(name, masage, logot)
            startDelay = 500

        }.start()
    }
}