package practice.takenotes.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shishir on 1/23/2018.
 */
@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE id = :userId")
    Note getNote(long userId);

    @Insert
    void insertAll(ArrayList<Note> notes);

    @Delete
    void deleteNote(Note note);

    @Delete
    void deleteNotes(ArrayList<Note> notes);

    @Insert
    void insertNote(Note note);

    @Update
    public void updateNotes(ArrayList<Note> notes);

    @Update
    public void updateNote(Note note);
}
