package com.example.maria.homework_9.viewmodels

import android.app.Application
import android.content.Context
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.example.maria.homework_9.R
import com.example.maria.homework_9.adapters.CustomAdapter
import com.example.maria.homework_9.entities.Message
import com.example.maria.homework_9.repositories.MessageRepository


class MessageViewModel(application: Application) : BaseViewModel(application) {

    private val repository: MessageRepository = MessageRepository(application)
    private var allMessages: LiveData<List<Message>>
    val adapter = CustomAdapter()

    init {
        allMessages = repository.getAllMessages()
    }

    fun insert(idx: Int, text: String) {
        if (text != "") {
            val message: Message = Message(idx, text)
            repository.insert(message)
        }
    }

    private fun update(view: View, message: Message, context: Context) {
        val mTextView: TextView = view.findViewById(R.id.textView)
        val mEditText: EditText = view.findViewById(R.id.editText)
        var text: String = mTextView.text.toString()
        mEditText.setText(text)

        mTextView.visibility = View.GONE
        mEditText.visibility = View.VISIBLE
        mEditText.requestFocus()
        showKeyboard(mEditText, context)
        mEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        mEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        mEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)

        mEditText.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    text = mEditText.text.toString()
                    mTextView.text = mEditText.text
                    mEditText.visibility = View.GONE
                    mTextView.visibility = View.VISIBLE
                    mEditText.setText("")
                    val upd_message = Message(message.userId, text)
                    upd_message.id = message.id
                    repository.update(upd_message)
                    true
                }
                else -> false
            }
        }
    }

    private fun delete(message: Message) {
        repository.delete(message)
    }

    fun getAllMessages(): LiveData<List<Message>> {
        return allMessages
    }

    fun updateUsersValue() {
        var user1 = 0
        var user2 = 0
        for (i in adapter.messages.indices) {
            if (adapter.messages[i].userId == 0)
                user1++
            else
                user2++
        }
        adapter.user1 = "User1: " + user1.toString()
        adapter.user2 = "User2: " + user2.toString()
    }

    fun showPopupMenu(view: View, message: Message, context: Context) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.popupmenu)

        popupMenu
                .setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> {
                            delete(message)
                            true
                        }
                        R.id.edit -> {
                            update(view, message, context)
                            true
                        }
                        R.id.close -> {
                            true
                        }
                        else -> false
                    }
                }
        popupMenu.setOnDismissListener {
        }
        popupMenu.show()
    }

    private fun showKeyboard(mEditText: EditText, context: Context) {
        mEditText.postDelayed({
            val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard!!.showSoftInput(mEditText, 0)
        }, 100)
    }
}