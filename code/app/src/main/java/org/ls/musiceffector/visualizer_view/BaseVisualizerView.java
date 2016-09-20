package org.ls.musiceffector.visualizer_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LS on 2016/9/18.
 * Bass class for visualizer.Using View
 */
public abstract class BaseVisualizerView extends View {

    public BaseVisualizerView(Context context) {
        super(context);
    }

    public BaseVisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseVisualizerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseVisualizerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(runDraw);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(runDraw);
    }

    Runnable runDraw = new Runnable() {
        @Override
        public void run() {
            invalidate();
            post(this);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        onDrawFrame(canvas);
    }

    protected abstract void onDrawFrame(Canvas canvas);
}
