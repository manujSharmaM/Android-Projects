package com.example.manuj.todos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomDialog extends Activity {
    EditText editText;
    Button saveButton;

    ArrayList<String> arrayList;


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    DatabaseReference dataRef = rootRef.child("Todo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_custom_dialog);

        editText = findViewById(R.id.etTask);

        saveButton = findViewById(R.id.saveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String task=editText.getText().toString();
                HashMap<String,String> todoMap=new HashMap<String, String>();
                todoMap.put("Task",task);
                dataRef.push().setValue(todoMap);
                Intent intent=new Intent(CustomDialog.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }

        });
    }

}


