package practice.takenotes.homeScreen;

import java.util.ArrayList;
import java.util.List;

import practice.takenotes.db.Note;

/**
 * Created by shishir on 1/28/2018.
 */

public class HomeScreenContract {
    public interface View {
        void init();
        void setUpNoNotesView();
        void refreshRecyclerView(List<Note> notes);
        void goToAddNoteScreen();
        void goToNoteDetailsScreen(long id);
    }

    public interface Presenter {
       void init();
       void fetchNotes();
    }
}
