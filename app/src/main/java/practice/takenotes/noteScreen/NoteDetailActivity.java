package practice.takenotes.noteScreen;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import practice.takenotes.R;
import practice.takenotes.application.NotesApplication;
import practice.takenotes.util.CustomImageView;
import practice.takenotes.db.Note;
import practice.takenotes.util.PermissionUtil;

/**
 * Created by shishir on 1/25/2018.
 */

public class NoteDetailActivity extends AppCompatActivity implements NoteDetailContract.View {
    private CustomImageView ivNote;
    private TextView etTitle;
    private TextView etContent;
    private ImageButton ibAddImage;
    private ImageButton ibDelImage;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabDel;

    public static final int CAMERA_REQUEST = 0;
    public static final int READ_WRITE_PERMISSION_REQUEST_CODE = 1;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 2;


    private NoteDetailsPresenter noteDetailsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        noteDetailsPresenter = new NoteDetailsPresenter(((NotesApplication)getApplication()), this);
        noteDetailsPresenter.init(getIntent());
    }

    @Override
    public void initView() {
        ivNote = findViewById(R.id.iv_note);
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        fabSave = findViewById(R.id.fab_save);
        fabDel = findViewById(R.id.fab_del);
        ibAddImage = findViewById(R.id.ib_add_image);
        ibDelImage = findViewById(R.id.ib_del_image);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
        fabDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDetailsPresenter.deleteNote();
            }
        });
        ibAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
        ibDelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDetailsPresenter.deleteImageFromPojo();
            }
        });
        noteDetailsPresenter.fetchNodeWhenScreenIsEdit();
    }

    @Override
    public void saveNote() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        noteDetailsPresenter.saveNote(title, content);

    }

    @Override
    public void removeImageInImageView() {
        ivNote.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
    }

    @Override
    public void setViewWithNote(Note note) {
        etTitle.setText(note.getTitle());
        etContent.setText(note.getDetail());
        String fileName = note.getImageUrl();
        if (fileName != null && !fileName.isEmpty()) {
            noteDetailsPresenter.loadImage(fileName);
        }
    }

    @Override
    public void loadBitmapOnImageView(Bitmap bitmap) {
        ivNote.setImageBitmap(bitmap);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void createOnclickOnAddImage() {
        ivNote.callOnClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        noteDetailsPresenter.checkCameraRequest(requestCode, resultCode, data);
    }

    @Override
    public void startCamera() {
        if (checkCameraPermission()) {
            Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(photoCaptureIntent, CAMERA_REQUEST);
        }
        else {
            PermissionUtil.requestCameraPermission(this, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public boolean checkStoragePermssion() {
        return PermissionUtil.checkIfPermissionGranted(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    @Override
    public void requestStoragePermission() {
        PermissionUtil.requestStoragePermission(this, READ_WRITE_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void showNoteDelOption() {
        fabDel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        noteDetailsPresenter.checkReadWritePermissionRequest(requestCode, permissions, grantResults);
    }

    @Override
    public boolean checkCameraPermission() {
        return PermissionUtil.checkIfPermissionGranted(this, new String[]{Manifest.permission.CAMERA});
    }
}
