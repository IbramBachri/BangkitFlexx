package cap.tone.bangkitflexx.ui.Intro

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import cap.tone.bangkitflexx.databinding.ActivityMainBinding
import cap.tone.bangkitflexx.ui.Login.LoginActivity
import cap.tone.bangkitflexx.ui.Signup.SignupActivity
import cap.tone.bangkitflexx.ui.signIn.SignInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        setupAction()
        setupView()
    }
    private fun setupAction() {
         binding.button.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
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
}