package practice.takenotes.noteScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import java.util.Date;

import javax.inject.Inject;

import practice.takenotes.db.Note;

/**
 * Created by shishir on 1/28/2018.
 */

public class NoteDetailContract {
    public interface View {
        void initView();
        void setViewWithNote(Note note);
        void loadBitmapOnImageView(Bitmap bitmap);
        void finishActivity();
        void createOnclickOnAddImage();
        void startCamera();
        void saveNote();
        void removeImageInImageView();
        boolean checkStoragePermssion();
        void requestStoragePermission();
        void showNoteDelOption();
        boolean checkCameraPermission();
    }
    public interface Presenter{
        void init(Intent intent);
        void fetchNodeWhenScreenIsEdit();
        void deleteImage();
        void deleteNote();
        void deleteImageFromPojo();
        void loadImage(String fileName);
        void saveNote(String title, String content);
        void saveNoteDetails(Date date, String title, String content);
        void saveImage(String title, String content);
        void checkCameraRequest(int requestCode, int resultCode, Intent data);
        void checkReadWritePermissionRequest(int requestCode, String permissions[], int[] grantResults);
    }
}

