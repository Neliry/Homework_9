package com.example.maria.homework_9

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maria.homework_9.adapters.CustomAdapter
import com.example.maria.homework_9.entities.Message
import com.example.maria.homework_9.viewmodels.MessageViewModel
import com.example.maria.homework_9.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var messageViewModel: MessageViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messages_recycler_view.layoutManager = LinearLayoutManager(this)
        messages_recycler_view.setHasFixedSize(true)
        users_radio_group.check(first_user_rb.id)

        val adapter = CustomAdapter()
        messages_recycler_view.adapter = adapter
        messages_recycler_view.addItemDecoration(
                SampleDecoration(
                        this
                )
        )
        messageViewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(MessageViewModel::class.java)

        messageViewModel!!.getAllMessages().observe(this, Observer { message ->
            adapter.setMessages(message)
        })

        btn_add.setOnClickListener {
            val text: String = message_edit_text.text.toString()
            var radioButtonID = users_radio_group.checkedRadioButtonId
            val radioButton: View = users_radio_group.findViewById(radioButtonID)
            val idx = users_radio_group.indexOfChild(radioButton)
            if (text != "") {
                val message: Message = Message(idx, text)
                messageViewModel!!.insert(message)
            }
        }

        adapter.setOnItenClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(message: Message, view: View) {
                showPopupMenu(view, message)
            }
        })

    }

    private fun showPopupMenu(view: View, message: Message) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.popupmenu)

        popupMenu
                .setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem): Boolean {
                        when (item.itemId) {
                            R.id.delete -> {
                                messageViewModel!!.delete(message)
                                return true
                            }
                            R.id.edit -> {
                                val mTextView: TextView = view.findViewById(R.id.textView)
                                val mEditText: EditText = view.findViewById(R.id.editText)
                                var text: String = mTextView.text.toString()
                                mEditText.setText(text)

                                mTextView.visibility = View.GONE
                                mEditText.visibility = View.VISIBLE
                                mEditText.requestFocus()
                                showKeyboard(mEditText)
                                mEditText.imeOptions = EditorInfo.IME_ACTION_DONE
                                mEditText.imeOptions = EditorInfo.IME_ACTION_DONE
                                mEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
                                mEditText.setOnEditorActionListener { v, actionId, event ->
                                    return@setOnEditorActionListener when (actionId) {
                                        EditorInfo.IME_ACTION_DONE -> {
                                            text=mEditText.text.toString()
                                            mTextView.text=mEditText.text
                                            mEditText.visibility=View.GONE
                                            mTextView.visibility = View.VISIBLE
                                            mEditText.setText("")
                                            val upd_message = Message(message.userId, text)
                                            upd_message.id= message.id
                                            messageViewModel!!.update(upd_message)
                                            true
                                        }
                                        else -> false
                                    }
                                }

                                return true
                            }
                            R.id.close -> {
                                return true
                            }
                            else -> return false
                        }
                    }
                })
        popupMenu.setOnDismissListener {
        }
        popupMenu.show()
    }

    private fun showKeyboard(mEditText: EditText){
        mEditText.postDelayed({
            val keyboard = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard!!.showSoftInput(mEditText, 0)
        }, 100)
    }
}
