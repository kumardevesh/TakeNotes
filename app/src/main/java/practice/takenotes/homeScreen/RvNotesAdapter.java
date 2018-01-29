package practice.takenotes.homeScreen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import practice.takenotes.R;
import practice.takenotes.db.Note;

/**
 * Created by shishir on 1/25/2018.
 */

public class RvNotesAdapter extends RecyclerView.Adapter<RvNotesAdapter.ViewHolder> {
    private List<Note> notes;
    private NoteClickListener noteClickListener;

    public interface NoteClickListener {
        void onNoteClick(long id);
    }

    public RvNotesAdapter(List<Note> notes, NoteClickListener noteClickListener) {
        this.notes = notes;
        this.noteClickListener = noteClickListener;
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public RvNotesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_notes_item_view, viewGroup, false);
        return new ViewHolder(view, noteClickListener);
    }

    @Override
    public void onBindViewHolder(RvNotesAdapter.ViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.bindData(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvCreatedOn;
        public ViewHolder(View view, final NoteClickListener noteClickListener) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvContent = view.findViewById(R.id.tv_content);
            tvCreatedOn = view.findViewById(R.id.tv_creation_time);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long id = getItemId();
                    noteClickListener.onNoteClick(id);
                }
            });
        }

        public void bindData(Note note) {
            tvTitle.setText(note.getTitle());
            tvContent.setText(note.getDetail());
            tvCreatedOn.setText(note.getCreatedOn());
        }
    }

}