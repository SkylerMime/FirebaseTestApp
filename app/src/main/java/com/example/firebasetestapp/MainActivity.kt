package com.example.firebasetestapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : Activity() {
    private val TAG = "MainActivity"
    private var numberToSend: Long = 0

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

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("currentCount")

        // read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // this method is called once with the initial value and again
                // whenever data at this location is updated.
                numberToSend = dataSnapshot.getValue() as Long
                val messageBox: TextView = findViewById(R.id.countMessage)
                messageBox.text = numberToSend.toString()
                val logMessage = numberToSend.toString()
                Log.d(TAG, "Value is: $logMessage")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

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
            // send the current count to the database
            myRef.setValue(numberToSend)

            val text = getString(R.string.message_sent)
            val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}

