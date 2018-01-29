package practice.takenotes.util;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by shishir on 1/23/2018.
 */

public class SaveOrDeleteImageTask extends AsyncTask<Void, Void, Boolean> {
    private TaskListener taskListener;

    public interface TaskListener {
        public void toPerForm();
        public void onFinished(Boolean isCompleted);
    }

    public SaveOrDeleteImageTask(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean isCompleted = false;
        try {
            taskListener.toPerForm();
            isCompleted= true;
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
