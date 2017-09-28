package edu.orangecoastcollege.cs273.vnguyen468.todo2day;

/**
 * Created by vnguyen468 on 9/28/2017.
 */

public class Task {

    private int mId;
    private String mDescription;
    private boolean mIsDone;

    public Task()
    {
        this(-1, "", false);
    }

    public Task(int id, String description, boolean isDone)
    {
        mId = id;
        mDescription = description;
        mIsDone = isDone;
    }

    public Task(String description, boolean isDone)
    {
        this(-1, description, isDone);
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean getDone() {
        return mIsDone;
    }

    public void setDone(boolean isDone) {
        mIsDone = isDone;
    }

    public int getId() {
        return mId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "mId=" + mId +
                ", mDescription='" + mDescription + '\'' +
                ", mIsDone=" + mIsDone +
                '}';
    }
}
