package org.ls.musiceffector.visualizer_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by LS on 2016/9/18.
 * Bass class for visualizer.Using SurfaceView
 */
public abstract class BaseVisualizerSurfaceView extends SurfaceView {

    private RenderThread renderThread;

    public BaseVisualizerSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseVisualizerSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseVisualizerSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseVisualizerSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        getHolder().addCallback(callback);
    }

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            renderThread = new RenderThread();
            renderThread.start();
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            renderThread.stopThread();
        }
    };

    protected abstract void onDrawFrame(Canvas canvas);

    class RenderThread extends Thread {
        private boolean isRunning = true;
        public void stopThread() {
            isRunning = false;
        }
        @Override
        public void run() {
            while (isRunning) {
                Canvas canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);
                    onDrawFrame(canvas);
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
