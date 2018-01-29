package practice.takenotes.db.tasks;

import android.os.AsyncTask;

import java.util.List;

import practice.takenotes.db.Note;

/**
 * Created by shishir on 1/23/2018.
 */

public class FetchNoteFromDbTask extends AsyncTask<Void, Void, Note> {

    public interface TaskListener {
        public Note toPerForm();
        public void onFinished(Note note);
    }
    private TaskListener taskListener;

    public FetchNoteFromDbTask(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    protected Note doInBackground(Void... voids) {
        Note notes = taskListener.toPerForm();
        return notes;
    }

    @Override
    protected void onPostExecute(Note note) {
        super.onPostExecute(note);
        if(taskListener != null) {
            taskListener.onFinished(note);
        }
    }
}
