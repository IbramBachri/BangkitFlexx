package cap.tone.bangkitflexx.ui.drawer.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cap.tone.bangkitflexx.ListUserAdapter
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.database.User
import cap.tone.bangkitflexx.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>? = null
    private val list = ArrayList<User>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvUser.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.setHasFixedSize(true)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUser.adapter = listUserAdapter
        list.addAll(listUser)
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}