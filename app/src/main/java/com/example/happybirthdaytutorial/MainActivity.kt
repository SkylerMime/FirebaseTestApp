package com.example.happybirthdaytutorial

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button

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
    }
}

