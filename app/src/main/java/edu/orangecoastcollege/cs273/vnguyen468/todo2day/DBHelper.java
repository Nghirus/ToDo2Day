package edu.orangecoastcollege.cs273.vnguyen468.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vnguyen468 on 9/28/2017.
 */

class DBHelper extends SQLiteOpenHelper{

    // Create some useful database constants.
    public static final String DATABASE_NAME = "ToDo2Day";
    public static final String DATABASE_TABLE = "Task";
    public static final int DATABASE_VERSION = 1;

    //Create some useful table constants
    public static final String KEY_FIELD_ID = "_id";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DONE = "done";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Generate an SQL statement for creating a new table
        // CREATE TABLE Task (_id INTEGER PRIMARY KEY, description TEXT, done Integer.
        String createTable = "CREATE TABLE " + DATABASE_TABLE + " (" +
                KEY_FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_DESCRIPTION + " TEXT, " +
                FIELD_DONE + " INTEGER" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //1. Drop existing table
        db.execSQL("DROP TABLE IF EXIST" + DATABASE_TABLE);
        //2. Build (create) the new one.
        onCreate(db);
    }

    public void addTask(Task newTask)
    {
        SQLiteDatabase db = getWritableDatabase();
        //Specify the value (fields) to insert into the database.
        //Everything except the primary key _id (auto assigned)
        ContentValues values = new ContentValues();
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_DONE,newTask.getDone() ? 1 : 0);
        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public List<Task> getAllTask()
    {
        List<Task> allTaskList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        //To retrieve data from a database table, we use a Cursor
        Cursor cursor = db.query(DATABASE_TABLE,
                new String[] {KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_DONE},null,null, null, null,
        null);

        if(cursor.moveToFirst())
        {
            //guaranteed at least one result from query.
            do {
                Task task = new Task(cursor.getInt(0),cursor.getString(1),cursor.getInt(2) == 1);
                allTaskList.add(task);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allTaskList;
    }

    public void deleteTask(Task taskToDelete)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, KEY_FIELD_ID + "=" + taskToDelete.getId(),null);
        db.close();
    }

    public void updateTask(Task taskToEdit)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_DESCRIPTION, taskToEdit.getDescription());
        values.put(FIELD_DONE, taskToEdit.getDone() ? 1 : 0);

        db.update(DATABASE_TABLE, values, KEY_FIELD_ID + "=" + taskToEdit.getId(),null);
        db.close();
    }

    public Task getSingleTask(int id)
    {
        Task singleTask = null;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE,
                new String[] {KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_DONE},KEY_FIELD_ID +
                        "=" + id,null, null, null,
                null);

        if(cursor.moveToFirst())
        {
            //guaranteed at least one result from query.
            singleTask = new Task(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)==1);
        }
        cursor.close();
        db.close();

        return singleTask;
    }
}
