package org.ls.musiceffector.visualizer_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by LS on 2016/9/18.
 * Try to draw a sound wave
 */
public class WaveView extends BaseVisualizerView {

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDrawFrame(Canvas canvas) {
        canvas.drawText("窝草", 0, 100, new Paint());
    }
}
