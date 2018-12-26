package com.example.maria.homework_9.adapters

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.maria.homework_9.R
import com.example.maria.homework_9.entities.Message


class CustomAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var messages: ArrayList<Message> = ArrayList()
    private var listener: OnItemClickListener? = null

    private var user1: Int = 0
    private var user2: Int = 0

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(messages[position-1], view)
                }

            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(message: Message, view: View)
    }
    fun setOnItenClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    internal inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val user1Quantity: TextView = view.findViewById(R.id.user1_textView)
        val user2Quantity: TextView = view.findViewById(R.id.user2_textView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(viewGroup.context)
                    .inflate(getUserLayout(viewType), viewGroup, false)
            HeaderViewHolder(v)
        } else {
            val v = LayoutInflater.from(viewGroup.context)
                    .inflate(getUserLayout(viewType), viewGroup, false)
            MessageViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessageViewHolder -> {
                holder.textView.text = messages[position - 1].text
            }
            is HeaderViewHolder -> {
                holder.user1Quantity.text = "User1: " + user1.toString()
                holder.user2Quantity.text = "User2: " + user2.toString()
            }
        }
    }

    override fun getItemCount() = messages.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return TYPE_HEADER
        if (position > 0) {
            val currentMessage: Message = messages[position - 1]
            return when (currentMessage.userId) {
                0 -> {
                    1
                }
                else -> {
                    2
                }
            }
        }
        return 3
    }

    private fun getUserLayout(viewType: Int): Int {
        return when (viewType) {
            0 -> R.layout.header_item
            1 -> R.layout.first_user_row_item
            else -> R.layout.second_user_row_item
        }
    }

    fun setMessages(messages: List<Message>) {
        this.messages = messages as ArrayList<Message>
        user1=0
        user2=0
        for(i in messages.indices){
            if(messages[i].userId==0)
                user1++
            else
                user2++
        }
        notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_HEADER = 0
    }
}
