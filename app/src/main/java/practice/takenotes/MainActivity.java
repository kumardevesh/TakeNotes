package practice.takenotes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import practice.takenotes.homeScreen.HomeScreenContract;
import practice.takenotes.homeScreen.HomeScreenPresenter;
import practice.takenotes.noteScreen.NoteDetailActivity;
import practice.takenotes.application.NotesApplication;
import practice.takenotes.db.DBHelper;
import practice.takenotes.db.Note;
import practice.takenotes.db.NoteDao;
import practice.takenotes.homeScreen.RvNotesAdapter;

public class MainActivity extends AppCompatActivity implements HomeScreenContract.View {
    HomeScreenPresenter homeScreenPresenter;

    private LinearLayout llNoNotes;
    private RecyclerView rvNotes;
    private RvNotesAdapter rvNotesAdapter;
    private FloatingActionButton fabAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeScreenPresenter = new HomeScreenPresenter(((NotesApplication)getApplication()), this);
        homeScreenPresenter.init();
        homeScreenPresenter.fetchNotes();
    }

    @Override
    public void goToAddNoteScreen() {
        Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToNoteDetailsScreen(long id) {
        Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(homeScreenPresenter != null) {
            homeScreenPresenter.fetchNotes();
        }
    }

    @Override
    public void init() {
        llNoNotes = findViewById(R.id.ll_no_notes);
        fabAddNote=findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddNoteScreen();
            }
        });

        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        rvNotes.setLayoutManager(layoutManager);
        ArrayList<Note> notes = new ArrayList<>();
        rvNotesAdapter = new RvNotesAdapter(notes, new RvNotesAdapter.NoteClickListener() {
            @Override
            public void onNoteClick(long id) {
                goToNoteDetailsScreen(id);
            }
        });
        rvNotesAdapter.setHasStableIds(true);
        rvNotes.setAdapter(rvNotesAdapter);    }

    @Override
    public void setUpNoNotesView() {
        rvNotes.setVisibility(View.GONE);
        llNoNotes.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshRecyclerView(List<Note> result) {
        llNoNotes.setVisibility(View.GONE);
        rvNotes.setVisibility(View.VISIBLE);
        rvNotesAdapter.setNotes(result);
    }
}
