package edu.orangecoastcollege.cs273.vnguyen468.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Task> mAlltaskList = new ArrayList<>();
    public static final String TAG = MainActivity.class.getSimpleName();

    //Reference to the database:
    private DBHelper mDB;
    private EditText mDescriptionEditText;
    private ListView mTaskListView;
    //reference to the custom list adaptor.
    TaskListAdapter mTaskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new DBHelper(this);
        mDescriptionEditText = (EditText)findViewById(R.id.taskEditText);
        mTaskListView = (ListView)findViewById(R.id.taskListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //database related "stuff"
        //1. Populate List from database (using DBHelper)
        mAlltaskList = mDB.getAllTask();
        //2. Connect ListView
        mTaskListAdapter = new TaskListAdapter(this, R.layout.task_item, mAlltaskList);
        mTaskListView.setAdapter(mTaskListAdapter);
    }

    public void addTask(View v)
    {
        //Check to see if Description is Empty;
        String description = mDescriptionEditText.getText().toString();
        if (TextUtils.isEmpty(description))
            Toast.makeText(this, "Please enter a description.", Toast.LENGTH_LONG).show();
        else
        {
            //Create the task
            Task newTask = new Task(description, false);
            // Add to the database.
            mDB.addTask(newTask);
            // Add to the list
            mAlltaskList.add(newTask);
            //Notify that data set has been changed.
            mTaskListAdapter.notifyDataSetChanged();
            //Clear out the Edit Text;
            mDescriptionEditText.setText("");

        }
    }

    public void clearAllTasks(View v)
    {
        mDB.deleteAllTask();
        mAlltaskList.clear();
        mTaskListAdapter.notifyDataSetChanged();
    }

    public void changeTaskStatus(View v)
    {
        CheckBox selectedCheckBox = (CheckBox) v;
        Task selectedTask =(Task) selectedCheckBox.getTag();
        //update the task
        selectedTask.setDone(selectedCheckBox.isChecked());
        //Update the database
        mDB.updateTask(selectedTask);
    }
}
