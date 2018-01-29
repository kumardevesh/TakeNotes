package practice.takenotes.db;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shishir on 1/23/2018.
 */
@Module
public class DbModule {
    private NotesDatabase notesDatabase;

    public DbModule(Application mApplication) {
        notesDatabase = Room.databaseBuilder(mApplication, NotesDatabase.class, "notes-db").build();
    }

    @Singleton
    @Provides
    NotesDatabase providesRoomDatabase() {
        return notesDatabase;
    }

    @Singleton
    @Provides
    NoteDao providesNoteDao(NotesDatabase notesDatabase) {
        return notesDatabase.noteDao();
    }

    @Provides
    DBHelper getDbHelper() {
        return new DBHelper();
    }
}
