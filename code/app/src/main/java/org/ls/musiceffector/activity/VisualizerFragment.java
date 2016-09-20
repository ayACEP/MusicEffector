package org.ls.musiceffector.activity;

import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import org.ls.musiceffector.R;
import org.ls.musiceffector.visualizer_view.BaseVisualizerSurfaceView;
import org.ls.musiceffector.visualizer_view.WaveView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LS on 2016/9/14.
 * Visualizer fragment
 */
public class VisualizerFragment extends BaseFragment {

    @BindView(R.id.surface_visualizer)
    WaveView surfaceVisualizer;

    private Visualizer visualizer;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizer, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void onServiceConnectedF() {
        visualizer = effectorService.getVisualizer();
    }

    @Override
    protected void onWaveFormDataCaptureF(Visualizer visualizer, byte[] waveform, int samplingRate) {

    }
}
