package com.example.firebasetestapp

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import com.example.firebasetestapp.R

class MainActivity : Activity() {
    private var numberToSend: Int = 0

    private fun updateNumber(increment: Boolean) {
        if (increment) {
            numberToSend++
        }
        else {
            numberToSend--
        }
        val messageBox: TextView = findViewById(R.id.countMessage)
        messageBox.text = numberToSend.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val incrementButton: Button = findViewById(R.id.incrementButton)
        incrementButton.setOnClickListener {
            updateNumber(true)
        }

        val decrementButton: Button = findViewById(R.id.decrementButton)
        decrementButton.setOnClickListener {
            updateNumber(false)
        }

        val sendButton: Button = findViewById(R.id.databaseSendButton)
        sendButton.setOnClickListener {
            val text = getString(R.string.message_sent)
            val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}

