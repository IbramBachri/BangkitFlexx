package cap.tone.bangkitflexx.ui.Login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import cap.tone.bangkitflexx.Model.UserModel
import cap.tone.bangkitflexx.Model.UserPreference
import cap.tone.bangkitflexx.databinding.ActivityLoginBinding
import cap.tone.bangkitflexx.helper.ViewModelFactory
import cap.tone.bangkitflexx.ui.Signup.SignupActivity
import cap.tone.bangkitflexx.ui.drawer.NavDrawActivity
import com.google.firebase.auth.FirebaseAuth

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: UserModel
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        setupView()
        //setupViewModel()
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
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this, { user ->
            this.user = user
        })
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                email != user.email -> {
                    binding.emailEditTextLayout.error = "Email tidak sesuai"
                }
                password != user.password -> {
                    binding.passwordEditTextLayout.error = "Password tidak sesuai"
                }
                else -> {
                    firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task->
                            if(task.isSuccessful) {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yeah!")
                                    setMessage("Anda berhasil login?")
                                    setPositiveButton("Lanjut") { _, _ ->
                                        val intent = Intent(context, NavDrawActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        //finish()
                                    }
                                    create()
                                    show()
                                }
                                Log.d("berhasil login", task.isSuccessful.toString())
                            } else {
                                Log.e("gagal login", task.exception.toString())
                                Toast.makeText(this, "Gagal Login : ${task.exception.toString()}",
                                Toast.LENGTH_SHORT).show()
                            }
                        }
                    //loginViewModel.login()

                }
            }
        }
        binding.createButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val crea = ObjectAnimator.ofFloat(binding.createButton, View.ALPHA, 1f).setDuration(500)
        val pw = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val pwedit =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailedit =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title,   email, emailedit, pw, pwedit, login, crea)
            startDelay = 500

        }.start()
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null) {
            val intent = Intent(this, NavDrawActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}