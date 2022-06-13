package cap.tone.bangkitflexx.ui.chat.private

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cap.tone.bangkitflexx.Model.MessageModel
import cap.tone.bangkitflexx.R

class AdapterPrivateChat constructor(private val listViewType: List<Int>, private val listChat: List<MessageModel>) : RecyclerView.Adapter<AdapterPrivateChat.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_DATE = 0
        const val VIEW_TYPE_SELF = 1
        const val VIEW_TYPE_OTHER = 2
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_DATE -> {
                val view = layoutInflater.inflate(R.layout.item_chat_date, null)
                ViewHolderChatItemDate(view)
            }VIEW_TYPE_SELF -> {
                val view = layoutInflater.inflate(R.layout.item_chat_self, null)
                ViewHolderChatItemSelf(view)
            }else -> {
                val view = layoutInflater.inflate(R.layout.item_chat_other_private, null)
                ViewHolderChatItemOther(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = listChat[position]
        listViewType[position].let {
            when (it) {
                VIEW_TYPE_DATE -> {
                    val viewHolderChatItemDate = holder as ViewHolderChatItemDate
                    viewHolderChatItemDate.textViewDate.text = chat.date
                }VIEW_TYPE_SELF -> {
                    val viewHolderChatItemSelf = holder as ViewHolderChatItemSelf
                    viewHolderChatItemSelf.textViewTime.text = chat.time
                    viewHolderChatItemSelf.textViewMessage.text = chat.message
                }else -> {
                    val viewHolderChatUser = holder as ViewHolderChatItemOther
                    viewHolderChatUser.textViewTime.text = chat.time
                    viewHolderChatUser.textViewMessage.text = chat.message
                }
            }
        }
    }

    override fun getItemCount(): Int = listChat.size

    override fun getItemViewType(position: Int): Int = listViewType[position]

    open inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ViewHolderChatItemDate constructor(itemView: View) : ViewHolder(itemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.chat_date)
    }

    inner class ViewHolderChatItemSelf constructor(itemView: View) : ViewHolder(itemView) {
        val textViewTime: TextView = itemView.findViewById(R.id.time_chat_self)
        val textViewMessage: TextView = itemView.findViewById(R.id.message_chat_self)
    }

    inner class ViewHolderChatItemOther constructor(itemView: View) : ViewHolder(itemView) {
        val textViewTime: TextView = itemView.findViewById(R.id.time_chat_other)
        val textViewMessage: TextView = itemView.findViewById(R.id.message_chat_other)
    }
}