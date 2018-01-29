package practice.takenotes.db.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import practice.takenotes.callbacks.TaskCallback;
import practice.takenotes.db.Note;

/**
 * Created by shishir on 1/23/2018.
 */

public class AddNotesToDbTask extends AsyncTask<Void, Void, Boolean> {
    private TaskListener taskListener;

    public interface TaskListener {
        public void toPerForm();
        public void onFinished(Boolean isCompleted);
    }

    public AddNotesToDbTask(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean isCompleted = false;
        try {
            taskListener.toPerForm();
            isCompleted = true;
        }
        catch (Exception e) {
            Log.d("check123", e.toString());
        }
        return isCompleted;
    }

    @Override
    protected void onPostExecute(Boolean isCompleted) {
        super.onPostExecute(isCompleted);
        if(taskListener != null) {
            taskListener.onFinished(isCompleted);
        }
    }
}
