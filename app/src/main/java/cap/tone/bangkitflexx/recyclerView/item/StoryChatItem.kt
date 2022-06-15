package cap.tone.bangkitflexx.recyclerView.item

import android.content.Context
import cap.tone.bangkitflexx.Model.User
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.glide.GlideApp
import cap.tone.bangkitflexx.util.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_chat_user.*

class StoryChatItem(val person: User,
                    val userId: String,
                    private val context: Context
)
    : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tv_story_name.text = person.name
        viewHolder.tv_story_email.text = person.email
        if (person.profilePicturePath != null)
            GlideApp.with(context)
                .load(StorageUtil.pathToReference(person.profilePicturePath))
                .placeholder(R.drawable.person)
                .into(viewHolder.tv_story_img)
    }

    override fun getLayout() = R.layout.item_chat_user

}