package cap.tone.bangkitflexx.ui.drawer

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.databinding.ActivityNavDrawBinding
import cap.tone.bangkitflexx.glide.GlideApp
import cap.tone.bangkitflexx.ui.signIn.SignInActivity
import cap.tone.bangkitflexx.util.FirestoreUtil
import cap.tone.bangkitflexx.util.StorageUtil
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.nav_header_nav_draw.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class NavDrawActivity : AppCompatActivity() {

    private val context: Context? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavDrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        FirestoreUtil.getCurrentUser { user ->
            tv_navbar_name.text = user.name
            tv_navbar_email.text = user.email
            if (user.profilePicturePath != null)
                GlideApp.with(this)
                    .load(StorageUtil.pathToReference(user.profilePicturePath))
                    .placeholder(R.drawable.person)
                    .into(tv_profile_img)

        }
        binding = ActivityNavDrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavDraw.toolbar)

        binding.appBarNavDraw.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navView.itemIconTintList = null;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_project_manager, R.id.nav_story_chat, R.id.nav_saved_msg, R.id.nav_settings, R.id.nav_about
            ), drawerLayout
        )

        val navController = findNavController(R.id.nav_host_fragment_content_nav_draw)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.itemIconTintList = null;

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav_draw, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logOut -> {
                AuthUI.getInstance()
                    .signOut(this@NavDrawActivity.context!!)
                    .addOnCompleteListener {
                        startActivity(intentFor<SignInActivity>().newTask().clearTask())
                    }
                true
            } else -> false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_draw)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}