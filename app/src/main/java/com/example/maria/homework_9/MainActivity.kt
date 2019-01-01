package com.example.maria.homework_9

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maria.homework_9.adapters.CustomAdapter
import com.example.maria.homework_9.entities.Message
import com.example.maria.homework_9.viewmodels.MessageViewModel
import com.example.maria.homework_9.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var messageViewModel: MessageViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messages_recycler_view.layoutManager = LinearLayoutManager(this)
        messages_recycler_view.setHasFixedSize(true)
        users_radio_group.check(first_user_rb.id)

        messageViewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(MessageViewModel::class.java)

        val adapter = messageViewModel!!.adapter
        messages_recycler_view.adapter = adapter
        messages_recycler_view.addItemDecoration(
                SampleDecoration(
                        this
                )
        )

        messageViewModel!!.getAllMessages().observe(this, Observer { message ->
            adapter.setMessages(message)
            messageViewModel!!.updateUsersValue()
        })

        btn_add.setOnClickListener {
            val text: String = message_edit_text.text.toString()
            var radioButtonID = users_radio_group.checkedRadioButtonId
            val radioButton: View = users_radio_group.findViewById(radioButtonID)
            val idx = users_radio_group.indexOfChild(radioButton)
            messageViewModel!!.insert(idx, text)
        }

        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(message: Message, view: View) {
                messageViewModel!!.showPopupMenu(view, message, this@MainActivity)
            }
        })
    }
}
