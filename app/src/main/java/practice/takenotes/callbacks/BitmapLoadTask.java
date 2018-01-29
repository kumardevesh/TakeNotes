package practice.takenotes.callbacks;

import android.graphics.Bitmap;

/**
 * Created by shishir on 1/26/2018.
 */

public interface BitmapLoadTask {
    void onSuccess(Bitmap bitmap);
    void onFailure();
}
