package practice.takenotes.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import practice.takenotes.callbacks.BitmapLoadTask;
import practice.takenotes.callbacks.TaskCallback;

/**
 * Created by shishir on 1/25/2018.
 */

public class Util {
    public static String getCurrentTimeStamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String getReadableTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM YYYY, HH:MM a");
        String dateString = dateFormat.format(date);
        dateString += getDayOfMonthSuffix(Integer.parseInt(dateString));
        String formattedDate = monthYearFormat.format(date);
        return dateString + formattedDate;
    }

    public static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th ";
        }
        switch (n % 10) {
            case 1:  return "st ";
            case 2:  return "nd ";
            case 3:  return "rd ";
            default: return "th";
        }
    }

    public static void getImageFileFromSDCard(String fileName, final BitmapLoadTask bitmapLoadTask){
        final String filePath = getDirectory() + "/" + fileName;
        GetBitmapFromStorageTask.TaskListener taskListener = new GetBitmapFromStorageTask.TaskListener() {
            @Override
            public Bitmap toPerForm() {
                Bitmap bitmap = null;
                File imageFile = new File(filePath);
                try {
                    FileInputStream fis = new FileInputStream(imageFile);
                    bitmap = BitmapFactory.decodeStream(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            public void onFinished(Bitmap bitmap) {
                if(bitmap != null) {
                    bitmapLoadTask.onSuccess(bitmap);
                }
                else {
                    bitmapLoadTask.onFailure();
                }
            }
        };
        GetBitmapFromStorageTask getBitmapFromStorageTask = new GetBitmapFromStorageTask(taskListener);
        getBitmapFromStorageTask.execute();
    }

    public static void storeCameraPhotoInSDCard(final Bitmap bitmap, final String fileName, final TaskCallback taskCallback){
        SaveOrDeleteImageTask.TaskListener taskListener = new SaveOrDeleteImageTask.TaskListener() {
            @Override
            public void toPerForm() {
                File outputFile = new File(getDirectory(), fileName);
                if(!outputFile.exists()) {
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile, false);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished(Boolean isCompleted) {
                if(isCompleted) {
                    taskCallback.onSuccess();
                }
                else {
                    taskCallback.onFailure();
                }
            }
        };
        SaveOrDeleteImageTask saveOrDeleteImageTask = new SaveOrDeleteImageTask(taskListener);
        saveOrDeleteImageTask.execute();
    }

    public static void deleteImageInSdCard(final String fileName, final TaskCallback taskCallback){
        SaveOrDeleteImageTask.TaskListener taskListener = new SaveOrDeleteImageTask.TaskListener() {
            @Override
            public void toPerForm() {
                File outputFile = new File(getDirectory(), fileName);
                if(outputFile.exists()) {
                    outputFile.delete();
                }
            }

            @Override
            public void onFinished(Boolean isCompleted) {
                if(isCompleted) {
                    taskCallback.onSuccess();
                }
                else {
                    taskCallback.onFailure();
                }
            }
        };
        SaveOrDeleteImageTask saveOrDeleteImageTask = new SaveOrDeleteImageTask(taskListener);
        saveOrDeleteImageTask.execute();
    }


    public static String getImageFileName(String timeStamp) {
        timeStamp = timeStamp.replace('/', '_');
        timeStamp = timeStamp.replace(' ', '_');
        return "note_" + timeStamp + ".jpg";
    }

    public static String getDirectory() {
        String folderPath = Environment.getExternalStorageDirectory() + "/takeNote";
        File file = new File(folderPath);
        if(!file.exists()) {
            file.mkdirs();
        }
        return folderPath;
    }
}
