package org.ls.musiceffector.service;

import android.app.Service;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by LS on 2016/9/15.
 * Background service, holding every effect here
 */
public class EffectorService extends Service {

    public static final String TAG = EffectorService.class.getSimpleName();

    private Visualizer visualizer;
    private Equalizer equalizer;
    private BassBoost bassBoost;

    private ArrayList<Visualizer.OnDataCaptureListener> onDataCaptureListeners;

    public class EffectorBinder extends Binder {
        public EffectorService getService() {
            return EffectorService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new EffectorBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        equalizer = new Equalizer(0, 0);
        bassBoost = new BassBoost(0, 0);
        visualizer = new Visualizer(0);
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
        visualizer.setDataCaptureListener(onDataCaptureListener, Visualizer.getMaxCaptureRate(), true, true);
        onDataCaptureListeners = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        visualizer.release();
        bassBoost.release();
        equalizer.release();
    }

    public Visualizer getVisualizer() {
        return visualizer;
    }

    public Equalizer getEqualizer() {
        return equalizer;
    }

    public BassBoost getBassBoost() {
        return bassBoost;
    }

    public void addOnDataCaptureListener(Visualizer.OnDataCaptureListener listener) {
        onDataCaptureListeners.add(listener);
    }

    public void removeOnDataCaptureListener(Visualizer.OnDataCaptureListener listener) {
        onDataCaptureListeners.remove(listener);
    }

    public void startCapture() {
        visualizer.setEnabled(true);
    }

    public void stopCapture() {
        visualizer.setEnabled(false);
    }

    Visualizer.OnDataCaptureListener onDataCaptureListener = new Visualizer.OnDataCaptureListener() {
        @Override
        public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
            for (Visualizer.OnDataCaptureListener listener: onDataCaptureListeners) {
                listener.onWaveFormDataCapture(visualizer, waveform, samplingRate);
            }
        }
        @Override
        public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
            for (Visualizer.OnDataCaptureListener listener: onDataCaptureListeners) {
                listener.onFftDataCapture(visualizer, fft, samplingRate);
            }
        }
    };
}
