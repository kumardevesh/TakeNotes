package practice.takenotes.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by shishir on 1/23/2018.
 */

public class GetBitmapFromStorageTask extends AsyncTask<Void, Void, Bitmap> {
    private TaskListener taskListener;

    public interface TaskListener {
        public Bitmap toPerForm();
        public void onFinished(Bitmap bitmap);
    }

    public GetBitmapFromStorageTask(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = null;
        try {
            bitmap = taskListener.toPerForm();
        }
        catch (Exception e) {
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(taskListener != null) {
            taskListener.onFinished(bitmap);
        }
    }
}
