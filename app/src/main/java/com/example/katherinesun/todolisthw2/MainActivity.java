package com.example.katherinesun.todolisthw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity{

    public static final String LIST_NAME = "tasks.txt";
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateList();
        ListView listView = (ListView) findViewById(R.id.taskList);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w("deleting", "Position " + position);
                deleteTask(position);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            PrintStream out = new PrintStream(openFileOutput(LIST_NAME, MODE_PRIVATE));
            for (int i = 0; i < arrayList.size(); i++) {
                out.println(arrayList.get(i));
            }
            out.flush();
            out.close();
        }
        catch (Exception e) {
            Log.wtf("addTask", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Scanner scan = new Scanner(openFileInput(LIST_NAME));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                arrayList.add(line);
            }

            updateList();
        }
        catch(IOException ioe){
                Log.wtf("addTask", ioe);
            }

    }

    public void deleteTask(int position) {
//        for(int i = position; i < arrayList.size() - 2; i++){
//            arrayList.set(i, arrayList.get(i + 1));
//        }
        arrayList.remove(arrayList.size() - 1 - position);
        updateList();
    }

    public void addNewTask(View view) {
        EditText editText = (EditText) findViewById(R.id.addTask);
        String newTask = editText.getText().toString();


        arrayList.add(newTask);
        updateList();

    }

    private void updateList() {
        try {
//            arrayList.clear();
//            Scanner scan = new Scanner(openFileInput(LIST_NAME));
//            while (scan.hasNextLine()){
//                String line = scan.nextLine();
//                arrayList.add(line);
//            }
            ArrayList<String> dumpItBack = new ArrayList<>();
            for(int i = arrayList.size() - 1; i >= 0; i--){
                dumpItBack.add(arrayList.get(i));
            }

            ListView listView = (ListView) findViewById(R.id.taskList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dumpItBack);
            listView.setAdapter(adapter);

        }
        catch (Exception e) {
            Log.wtf("addTask", e);
        }
    }


}
