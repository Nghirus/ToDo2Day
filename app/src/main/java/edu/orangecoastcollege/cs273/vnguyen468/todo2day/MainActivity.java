package edu.orangecoastcollege.cs273.vnguyen468.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Task> mAlltaskList = new ArrayList<>();
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //clear the database
        deleteDatabase(DBHelper.DATABASE_NAME);

        //Pre-populate the list with 4 task.
        mAlltaskList.add(new Task("Study for CS 273 Midterm", false));
        mAlltaskList.add(new Task("Study for CS 250 Midterm", false));
        mAlltaskList.add(new Task("Study for CS 220 Midterm", true));
        mAlltaskList.add(new Task("Study for CS 262 Midterm", false));

        //Lets  instantiate a new DBHelper
        DBHelper db = new DBHelper(this);

        //Let's Loop through the list and add each one to the database:
        for (Task t : mAlltaskList)
            db.addTask(t);

        //Let's clear the list, then rebuild it from the database;
        mAlltaskList.clear();

        //Retrieve the task from database;
        mAlltaskList = db.getAllTask();

        //Loop and print to log.i
        for(Task t: mAlltaskList)
            Log.i(TAG,t.toString());

        Log.i(TAG,"After deleting task 4");
        db.deleteTask(mAlltaskList.get(3));
        mAlltaskList = db.getAllTask();
        for (Task t : mAlltaskList)
            Log.i(TAG,t.toString());
    }
}
