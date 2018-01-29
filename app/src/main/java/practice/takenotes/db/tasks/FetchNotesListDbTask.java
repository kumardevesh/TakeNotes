package practice.takenotes.db.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import practice.takenotes.db.Note;

/**
 * Created by shishir on 1/23/2018.
 */

public class FetchNotesListDbTask extends AsyncTask<Void, Void, List<Note>> {

    public interface TaskListener {
        public List<Note> toPerForm();
        public void onFinished(List<Note> result);
    }
    private TaskListener taskListener;

    public FetchNotesListDbTask(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    protected List<Note> doInBackground(Void... voids) {
        List<Note> notes = taskListener.toPerForm();
        return notes;
    }

    @Override
    protected void onPostExecute(List<Note> notes) {
        super.onPostExecute(notes);
        if(taskListener != null) {
            taskListener.onFinished(notes);
        }
    }
}
