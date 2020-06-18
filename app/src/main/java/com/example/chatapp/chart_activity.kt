package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chart_activity.*


class chart_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_activity)

        button3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        button2.setOnClickListener {
            val msg = editTextTextPersonName.text.toString()
            val email = FirebaseAuth.getInstance().currentUser!!.email // !! is to tell the compiler/IDE that this thing won't be NULL

            FirebaseDatabase.getInstance().reference.child("messages")
                .push()
                .setValue(
                    Message(email!!, msg)
                )
        }

        val query = FirebaseDatabase.getInstance().reference.child("messages")
        val options = FirebaseListOptions.Builder<Message>()
            .setLayout(android.R.layout.simple_list_item_1)
            .setQuery(query, Message::class.java)
            .build()

        val adapter = object : FirebaseListAdapter<Message>(options) {
            override fun populateView(v: View, model: Message, position: Int) {
                (v as TextView).text = model.email + "\n" + model.msg
            }
        }
        adapter.startListening()

        messages.adapter = adapter
    }
}


class Message(var email: String, var msg: String) {
    constructor() : this("", "")
}