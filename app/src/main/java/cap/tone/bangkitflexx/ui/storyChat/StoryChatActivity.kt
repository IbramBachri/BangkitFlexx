package cap.tone.bangkitflexx.ui.storyChat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cap.tone.bangkitflexx.ListUserAdapter
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.database.User
import cap.tone.bangkitflexx.databinding.ActivityStorychatBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class StoryChatActivity : AppCompatActivity() {
    private lateinit var mainViewModel: StoryChatViewModel
    private lateinit var binding: ActivityStorychatBinding
    private lateinit var rvUser: RecyclerView
    private val list = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storychat)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        list.addAll(listUser)
        showRecylerlist()
    }

    private val listUser: ArrayList<User>
        get(){
            val dataName = resources.getStringArray(R.array.data_name)
            val dataLastChat = resources.getStringArray(R.array.data_lastchat)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listUsers = ArrayList<User>()
            for (i in dataName.indices) {
                val user = User(dataName[i], dataLastChat[i], dataPhoto.getResourceId(i, -1))
                listUsers.add(user)
            }
            return listUsers
        }

    private fun showRecylerlist() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        rvUser.adapter = listUserAdapter
    }
}