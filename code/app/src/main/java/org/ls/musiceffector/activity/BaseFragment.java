package org.ls.musiceffector.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.audiofx.Visualizer;
import android.os.IBinder;
import android.support.v4.app.Fragment;

/**
 * Created by LS on 2016/9/16.
 */
public class BaseFragment extends Fragment {

    protected EffectorService effectorService;

    @Override
    public void onDestroy() {
        super.onDestroy();
        effectorService.removeOnDataCaptureListener(onDataCaptureListener);
    }

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            effectorService = ((EffectorService.EffectorBinder) service).getService();
            effectorService.addOnDataCaptureListener(onDataCaptureListener);
            onServiceConnectedF();
        }
        @Override public void onServiceDisconnected(ComponentName name) {
            onServiceDisconnectedF();
        }
    };

    Visualizer.OnDataCaptureListener onDataCaptureListener = new Visualizer.OnDataCaptureListener() {
        @Override
        public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
            onWaveFormDataCaptureF(visualizer, waveform, samplingRate);
        }
        @Override
        public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
            onFftDataCaptureF(visualizer, fft, samplingRate);
        }
    };

    /** if you override this method, you MUST call super.onServiceConnectedF() before you do something!*/
    protected void onServiceConnectedF() {}

    protected void onServiceDisconnectedF() {}

    protected void onWaveFormDataCaptureF(Visualizer visualizer, byte[] waveform, int samplingRate) {}

    protected void onFftDataCaptureF(Visualizer visualizer, byte[] fft, int samplingRate){}
}
