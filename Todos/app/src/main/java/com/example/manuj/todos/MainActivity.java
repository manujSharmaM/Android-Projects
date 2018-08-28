package com.example.manuj.todos;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    Button button;


    String tasks;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    DatabaseReference dataRef = rootRef.child("Todo");
    ArrayList<String>arrayList=new ArrayList<>();
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.delButton);
        textView=findViewById(R.id.taskText);
        listView=findViewById(R.id.listView);


        arrayAdapter=new ArrayAdapter<String>(this,R.layout.list_items,R.id.taskText,arrayList);
        listView.setAdapter(arrayAdapter);

        dataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue().toString();
                try {
                    JSONObject object = new JSONObject(value);
                    tasks = object.getString("Task");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.add(tasks);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(MainActivity.this,"sffad",Toast.LENGTH_SHORT).show();

            }

        });


        //Get the floating action button and set onClickLiter
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CustomDialog.class);
                startActivity(intent);
            }
        })
            
    public void deleteTask(final View view) {

        int index=listView.getPositionForView(view);
        String str= (String) arrayAdapter.getItem(index++);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query taskQuery = ref.child("Todo").orderByChild("Task").equalTo(str);
        taskQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    arrayList.remove(listView.getPositionForView(view));
                    arrayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });


    }

}
