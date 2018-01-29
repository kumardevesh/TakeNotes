package practice.takenotes.util;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by shishir on 1/27/2018.
 */

public class CustomImageView extends AppCompatImageView {

    private final String TAG = CustomImageView.class.getSimpleName();
    private boolean isBAndW = false;


    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(!isBAndW) {
                    isBAndW = true;
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.setSaturation(0);
                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                    if (getDrawable() != null) {
                        getDrawable().setColorFilter(filter);
                    } else {
                        Log.e(TAG, "No image View Set");
                    }
                }
                break;
            default: {
                if(isBAndW) {
                    isBAndW = false;
                    ColorMatrix colorMatrix = new ColorMatrix();
                    ColorMatrixColorFilter colorfilter = new ColorMatrixColorFilter(colorMatrix);
                    if (getDrawable() != null) {
                        getDrawable().setColorFilter(colorfilter);
                    } else {
                        Log.e(TAG, "No image View Set");
                    }
                }
            }
        }
        return true;
    }
}