package practice.takenotes.application;

import javax.inject.Singleton;

import dagger.Component;
import practice.takenotes.homeScreen.HomeScreenPresenter;
import practice.takenotes.db.DbModule;
import practice.takenotes.noteScreen.NoteDetailsPresenter;

/**
 * Created by shishir on 1/23/2018.
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class, DbModule.class})
public interface AppComponent {
    void inject(NotesApplication notesApplication);
    void inject(HomeScreenPresenter homeScreenPresenter);
    void inject(NoteDetailsPresenter noteDetailsPresenter);
}
