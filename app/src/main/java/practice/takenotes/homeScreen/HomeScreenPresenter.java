package practice.takenotes.homeScreen;

import android.app.Application;

import java.util.List;

import javax.inject.Inject;

import practice.takenotes.application.NotesApplication;
import practice.takenotes.db.DBHelper;
import practice.takenotes.db.Note;
import practice.takenotes.db.NoteDao;
import practice.takenotes.db.tasks.FetchNotesListDbTask;

/**
 * Created by shishir on 1/28/2018.
 */

public class HomeScreenPresenter implements HomeScreenContract.Presenter {
    @Inject DBHelper dbHelper;
    @Inject NoteDao noteDao;

    private HomeScreenContract.View view;
    private boolean isRefreshHit = false;

    @Inject
    public HomeScreenPresenter(NotesApplication application, HomeScreenContract.View view) {
        this.view = view;
        application.getAppComponent().inject(this);
    }

    @Override
    public void init() {
        view.init();
    }

    @Override
    public void fetchNotes() {
        if(!isRefreshHit) {
            isRefreshHit = true;
            dbHelper.fetchAllNotes(new FetchNotesListDbTask.TaskListener() {
                @Override
                public List<Note> toPerForm() {
                    return noteDao.getAllNotes();
                }

                @Override
                public void onFinished(List<Note> result) {
                    isRefreshHit = false;
                    if (result.size() < 1) {
                        view.setUpNoNotesView();
                    } else {
                        view.refreshRecyclerView(result);
                    }
                }
            });
        }
    }
}
