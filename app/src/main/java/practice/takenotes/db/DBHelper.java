package practice.takenotes.db;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;

import practice.takenotes.util.Util;
import practice.takenotes.callbacks.TaskCallback;
import practice.takenotes.db.tasks.AddNotesToDbTask;
import practice.takenotes.db.tasks.FetchNoteFromDbTask;
import practice.takenotes.db.tasks.FetchNotesListDbTask;

/**
 * Created by shishir on 1/23/2018.
 */
@Singleton
public class DBHelper {
    public DBHelper() {
    }

    public void populateWithTestData(final NoteDao noteDao) {
        final ArrayList<Note> notes = new ArrayList<>();
        Date date = new Date();
        String time = Util.getCurrentTimeStamp(date);
        Note note = new Note();
        note.setTitle("Note 1");
        note.setDetail("This note contains Note 1");
        note.setCreatedOn(time);
        notes.add(note);

        note = new Note();
        note.setTitle("Note 2");
        note.setDetail("This note contains Note 2");
        note.setCreatedOn(time);
        notes.add(note);

        note = new Note();
        note.setTitle("Note 3");
        note.setDetail("This note contains Note 3");
        note.setCreatedOn(time);
        notes.add(note);

        note = new Note();
        note.setTitle("Note 4");
        note.setDetail("This note contains Note 4");
        note.setCreatedOn(time);
        notes.add(note);
        FetchNotesListDbTask.TaskListener taskListener = new FetchNotesListDbTask.TaskListener() {
            @Override
            public List<Note> toPerForm() {
                noteDao.insertAll(notes);
                return null;
            }

            @Override
            public void onFinished(List<Note> result) {

            }
        };
        FetchNotesListDbTask fetchNotesListDbTask = new FetchNotesListDbTask(taskListener);
        fetchNotesListDbTask.execute();
    }

    public void fetchAllNotes(FetchNotesListDbTask.TaskListener taskListener) {
        FetchNotesListDbTask fetchNotesListDbTask = new FetchNotesListDbTask(taskListener);
        fetchNotesListDbTask.execute();
    }

    public void saveNote(final Note note, final NoteDao noteDao, final TaskCallback taskCallback) {
        AddNotesToDbTask.TaskListener taskListener = new AddNotesToDbTask.TaskListener() {
            @Override
            public void toPerForm() {
                noteDao.insertNote(note);
            }

            @Override
            public void onFinished(Boolean isCompleted) {
                if(isCompleted) {
                    taskCallback.onSuccess();
                }
                else {
                    taskCallback.onFailure();
                }
            }
        };
        AddNotesToDbTask addNotesToDbTask = new AddNotesToDbTask(taskListener);
        addNotesToDbTask.execute();
    }

    public void updateNote(final Note note, final NoteDao noteDao, final TaskCallback taskCallback) {
        if(note == null) {
            return;
        }
        AddNotesToDbTask.TaskListener taskListener = new AddNotesToDbTask.TaskListener() {
            @Override
            public void toPerForm() {
                noteDao.updateNote(note);
            }

            @Override
            public void onFinished(Boolean isCompleted) {
                if(isCompleted) {
                    taskCallback.onSuccess();
                }
                else {
                    taskCallback.onFailure();
                }
            }
        };
        AddNotesToDbTask addNotesToDbTask = new AddNotesToDbTask(taskListener);
        addNotesToDbTask.execute();
    }

    public void deleteNote(final Note note, final NoteDao noteDao, final TaskCallback taskCallback) {
        if(note == null) {
            return;
        }
        AddNotesToDbTask.TaskListener taskListener = new AddNotesToDbTask.TaskListener() {
            @Override
            public void toPerForm() {
                noteDao.deleteNote(note);
            }

            @Override
            public void onFinished(Boolean isCompleted) {
                if(isCompleted) {
                    taskCallback.onSuccess();
                }
                else {
                    taskCallback.onFailure();
                }
            }
        };
        AddNotesToDbTask addNotesToDbTask = new AddNotesToDbTask(taskListener);
        addNotesToDbTask.execute();
    }

    public void fetchNote(final FetchNoteFromDbTask.TaskListener taskListener) {
        FetchNoteFromDbTask fetchNoteFromDbTask = new FetchNoteFromDbTask(taskListener);
        fetchNoteFromDbTask.execute();
    }




}
