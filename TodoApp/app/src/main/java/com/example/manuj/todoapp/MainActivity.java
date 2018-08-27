package com.example.manuj.todoapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHelpler dbHelpler;
    ArrayAdapter mAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);
        dbHelpler=new DbHelpler(this);
        loadTask();
    }

    //ADD ICONN TO MMENU BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void loadTask(){

        ArrayList<String> taskList=dbHelpler.getTaskList();

        if(mAdapter==null){
            mAdapter=new ArrayAdapter<String>(this,R.layout.items,R.id.textView,taskList);
            listView.setAdapter(mAdapter);
        }else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.addTask:
                final EditText editText=new EditText(this);
                AlertDialog alertDialog=new AlertDialog.Builder(this)
                        .setTitle("Add new Task")
                        .setMessage("What's your Task")
                        .setView(editText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task=String.valueOf(editText.getText());
                                dbHelpler.insertNewTask(task);
                                loadTask();

                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                        alertDialog.show();
                        return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view){
            int index=listView.getPositionForView(view);
            String task= (String) mAdapter.getItem(index++);
        dbHelpler.deleteTask(task);
        loadTask();
    }
}
