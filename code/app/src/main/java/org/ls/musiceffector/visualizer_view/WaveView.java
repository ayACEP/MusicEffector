package org.ls.musiceffector.visualizer_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by LS on 2016/9/18.
 * Try to draw a sound wave
 */
public class WaveView extends BaseVisualizerView {

    private byte[] prepareBuffer;
    private Paint paint;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
//        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(4);
    }

    @Override
    protected void onDrawFrame(Canvas canvas) {
        if (prepareBuffer == null) {
            return;
        }
        byte[] drawingBuffer = prepareBuffer;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < drawingBuffer.length; i++) {
//            int data = drawingBuffer[i] > 0 ? 128 - drawingBuffer[i] : - (128 + drawingBuffer[i]);
            int data = (drawingBuffer[i] & 0x00FF) - 128;
            sb.append(data).append(",");
            canvas.drawLine(i * 4, 128, i * 4, data + 128, paint);
        }
//        Log.i("test", sb.toString());
    }

    @Override
    public void pushBuffer(byte[] buffer) {
        prepareBuffer = Arrays.copyOf(buffer, buffer.length);
    }
}
