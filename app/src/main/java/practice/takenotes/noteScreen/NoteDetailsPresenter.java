package practice.takenotes.noteScreen;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.Date;

import javax.inject.Inject;

import practice.takenotes.application.NotesApplication;
import practice.takenotes.callbacks.BitmapLoadTask;
import practice.takenotes.callbacks.TaskCallback;
import practice.takenotes.db.DBHelper;
import practice.takenotes.db.Note;
import practice.takenotes.db.NoteDao;
import practice.takenotes.db.tasks.FetchNoteFromDbTask;
import practice.takenotes.util.Util;

import static android.app.Activity.RESULT_OK;
import static practice.takenotes.noteScreen.NoteDetailActivity.CAMERA_PERMISSION_REQUEST_CODE;
import static practice.takenotes.noteScreen.NoteDetailActivity.CAMERA_REQUEST;
import static practice.takenotes.noteScreen.NoteDetailActivity.READ_WRITE_PERMISSION_REQUEST_CODE;

/**
 * Created by shishir on 1/28/2018.
 */

public class NoteDetailsPresenter implements NoteDetailContract.Presenter {
    @Inject
    NoteDao noteDao;
    @Inject
    DBHelper dbHelper;

    private NoteDetailContract.View view;
    private Bitmap image;
    private long noteId = 0;
    private Note currentNote = null;
    private Note prevNote = null;

    public NoteDetailsPresenter(NotesApplication application, NoteDetailContract.View view) {
        this.view = view;
        application.getAppComponent().inject(this);
    }

    @Override
    public void init(Intent intent) {
        noteId = intent.getLongExtra("id", 0);
        view.initView();
    }

    @Override
    public void fetchNodeWhenScreenIsEdit() {
        if(noteId != 0) {
            dbHelper.fetchNote(new FetchNoteFromDbTask.TaskListener() {
                @Override
                public Note toPerForm() {
                    return noteDao.getNote(noteId);
                }

                @Override
                public void onFinished(Note note) {
                    if (note != null) {
                        view.showNoteDelOption();
                        if(currentNote == null) {
                            prevNote = new Note();
                            prevNote.setTitle(note.getTitle());
                            prevNote.setDetail(note.getDetail());
                            prevNote.setImageUrl(note.getImageUrl());
                        }
                        currentNote = note;
                        view.setViewWithNote(currentNote);
                    }
                }
            });
        }
        else {
            prevNote = null;
        }
    }

    @Override
    public void deleteImage() {
        String oldFileName = currentNote.getImageUrl();
        Util.deleteImageInSdCard(oldFileName, new TaskCallback() {
            @Override
            public void onSuccess() {
                Log.d("check123", "image deleted");
            }

            @Override
            public void onFailure() {
                Log.d("check123", "image delete failed");
            }
        });
    }

    @Override
    public void deleteNote() {
        deleteImage();
        dbHelper.deleteNote(currentNote, noteDao, new TaskCallback() {
            @Override
            public void onSuccess() {
                Log.d("Check123", "note deleted");
                view.finishActivity();
            }

            @Override
            public void onFailure() {
                Log.d("Check123", "note not deleted");
                view.finishActivity();
            }
        });
    }

    @Override
    public void deleteImageFromPojo() {
        String oldFileName = currentNote.getImageUrl();
        Util.deleteImageInSdCard(oldFileName, new TaskCallback() {
            @Override
            public void onSuccess() {
                currentNote.setImageUrl("");
                view.removeImageInImageView();
            }

            @Override
            public void onFailure() {
                Log.d("check123", "image delete failed");
            }
        });
    }

    @Override
    public void loadImage(String fileName) {
        Util.getImageFileFromSDCard(fileName, new BitmapLoadTask() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                view.loadBitmapOnImageView(bitmap);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    @Override
    public void saveNote(String title, String content) {
        boolean checkStoragePermission = view.checkStoragePermssion();
        if (checkStoragePermission) {
            saveImage(title, content);
        }
        else {
            view.requestStoragePermission();
        }
    }

    @Override
    public void saveNoteDetails(Date date, String title, String content) {
        if (title.isEmpty()) {

        } else {
            if (currentNote == null) {
                String timeSign = Util.getReadableTime(date);
                currentNote = new Note();
                currentNote.setCreatedOn(timeSign);
            }

            currentNote.setTitle(title);
            currentNote.setDetail(content);
            if(image != null) {
                deleteImage();
                String fileName = Util.getImageFileName(Util.getCurrentTimeStamp(date));
                currentNote.setImageUrl(fileName);

            }
            if(noteId == 0) {
                dbHelper.saveNote(currentNote, noteDao, new TaskCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Check123", "saved");
                        view.finishActivity();
                    }

                    @Override
                    public void onFailure() {
                        Log.d("Check123", "not saved");
                        view.finishActivity();
                    }
                });
            }
            else {
                dbHelper.updateNote(currentNote, noteDao, new TaskCallback() {
                    @Override
                    public void onSuccess() {
                        view.finishActivity();
                    }

                    @Override
                    public void onFailure() {
                        Log.d("Check123", "not saved");
                        view.finishActivity();
                    }
                });
            }
        }
    }

    @Override
    public void saveImage(final String title, final String content) {
        if(image == null && currentNote.getTitle().equals(title) && currentNote.getDetail().equals(content)) {
            Log.d("check123", " not changed");
            view.finishActivity();
            return;
        }
        Log.d("check123", "changed");
        final Date date = new Date();
        String fileName = Util.getImageFileName(Util.getCurrentTimeStamp(date));
        if (image != null) {
            Util.storeCameraPhotoInSDCard(image, fileName, new TaskCallback() {
                @Override
                public void onSuccess() {
                    saveNoteDetails(date, title, content);
                }

                @Override
                public void onFailure() {
                    Log.d("check123", "save image error");
                }
            });
        }
        else {
            saveNoteDetails(date, title, content);
        }
    }

    @Override
    public void checkCameraRequest(int requestCode, int resultCode, Intent data) {
        if (CAMERA_REQUEST == requestCode && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image = bitmap;
            view.loadBitmapOnImageView(image);
        }
    }

    @Override
    public void checkReadWritePermissionRequest(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case READ_WRITE_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean checkPermission = view.checkStoragePermssion();
                    if (checkPermission) {
                        view.saveNote();
                    }

                }
            }
            case CAMERA_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    view.createOnclickOnAddImage();
                }
            }
        }
    }


}
