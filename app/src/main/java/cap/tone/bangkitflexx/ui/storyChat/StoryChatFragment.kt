package cap.tone.bangkitflexx.ui.storyChat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.util.FirestoreUtil
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_story_chat.*

class StoryChatFragment : Fragment() {

    private lateinit var userListenerRegistration: ListenerRegistration
    private var shouldInitRecyclerView = true
    private lateinit var storyChatSection: Section

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userListenerRegistration =
            FirestoreUtil.addUsersListener(this.requireActivity(), this::updateRecyclerView)

        return inflater.inflate(R.layout.fragment_story_chat, container, false)
    }
    private fun updateRecyclerView(items: List<Item>) {

        fun init() {
            rv_story.apply {
                layoutManager = LinearLayoutManager(this@StoryChatFragment.context)
                adapter = GroupAdapter<ViewHolder>().apply {
                    storyChatSection = Section(items)
                    add(storyChatSection)
                    //setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = storyChatSection.update(items)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

    }
    //private val onItemClick = OnItemClickListener { item, view ->
        //if (item is StoryChatItem) {
            //startActivity<PrivateChatActivity>(
            //    AppConstants.USER_NAME to item.person.name,
            //    AppConstants.USER_ID to item.userId
         //   )
        //}
    //}

}