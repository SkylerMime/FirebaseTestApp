package com.example.firebasetestapp;

import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
// import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    public static final String COUNT_KEY = "currentCount";
    private String TAG = "MainActivity";
    private long numberToSend = 0;

    // Access an instance of a Cloud Firestore document
    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("testData/numberToShow");

    private void updateNumber(Boolean increment) {
        if (increment) {
            numberToSend++;
        }
        else {
            numberToSend--;
        }
        TextView messageBox = findViewById(R.id.countMessage);
        messageBox.setText(String.valueOf(numberToSend));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the data to initialize the text view.
        mDocRef.get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    numberToSend = (Long) documentSnapshot.get(COUNT_KEY);
                    TextView messageBox = findViewById(R.id.countMessage);
                    messageBox.setText(String.valueOf(numberToSend));
                }
            })
            .addOnFailureListener(e -> Log.w(TAG, "Error getting documents.", e));

        // Prepare the collection for reading
        Map<String, Object> dataToSave = new HashMap<>();
        dataToSave.put(COUNT_KEY, numberToSend);

        Button incrementButton = findViewById(R.id.incrementButton);
        incrementButton.setOnClickListener(v -> updateNumber(true));

        Button decrementButton = findViewById(R.id.decrementButton);
        decrementButton.setOnClickListener(v -> updateNumber(false));

        Button sendButton  = findViewById(R.id.databaseSendButton);
        sendButton.setOnClickListener (v -> {
            dataToSave.put("currentCount", numberToSend);
            // open or create that document
            mDocRef.set(dataToSave)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Document saved");
                    String text = getString(R.string.message_sent);
                    Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                    toast.show();
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
        });
    }
}