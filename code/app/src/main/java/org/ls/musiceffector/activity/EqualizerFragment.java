package org.ls.musiceffector.activity;

import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import org.ls.musiceffector.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LS on 2016/9/14.
 * Equalizer fragment
 */
public class EqualizerFragment extends BaseFragment {

    @BindView(R.id.seek_0)
    SeekBar seek0;
    @BindView(R.id.seek_1)
    SeekBar seek1;
    @BindView(R.id.seek_2)
    SeekBar seek2;
    @BindView(R.id.seek_3)
    SeekBar seek3;
    @BindView(R.id.seek_4)
    SeekBar seek4;
    @BindView(R.id.seek_bass_boost)
    SeekBar seekBassBoost;
    @BindView(R.id.switch_equalizer)
    CompoundButton switchEqualizer;

    private Equalizer equalizer;
    private BassBoost bassBoost;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equalizer, container, false);
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
        equalizer = effectorService.getEqualizer();
        // 并非5段
        seek0.setMax(equalizer.getBandLevelRange()[1] - equalizer.getBandLevelRange()[0]);
        seek1.setMax(equalizer.getBandLevelRange()[1] - equalizer.getBandLevelRange()[0]);
        seek2.setMax(equalizer.getBandLevelRange()[1] - equalizer.getBandLevelRange()[0]);
        seek3.setMax(equalizer.getBandLevelRange()[1] - equalizer.getBandLevelRange()[0]);
        seek4.setMax(equalizer.getBandLevelRange()[1] - equalizer.getBandLevelRange()[0]);

        seek0.setOnSeekBarChangeListener(onSeekEqualizerChangeListener);
        seek1.setOnSeekBarChangeListener(onSeekEqualizerChangeListener);
        seek2.setOnSeekBarChangeListener(onSeekEqualizerChangeListener);
        seek3.setOnSeekBarChangeListener(onSeekEqualizerChangeListener);
        seek4.setOnSeekBarChangeListener(onSeekEqualizerChangeListener);

        switchEqualizer.setChecked(equalizer.getEnabled());
        switchEqualizer.setOnCheckedChangeListener(onCheckedChangeListener);

        bassBoost = effectorService.getBassBoost();
        seekBassBoost.setEnabled(bassBoost.getStrengthSupported());
        seekBassBoost.setOnSeekBarChangeListener(onSeekBassBoostChangeListener);
    }

    @Override
    protected void onFftDataCaptureF(Visualizer visualizer, byte[] fft, int samplingRate) {

    }

    SeekBar.OnSeekBarChangeListener onSeekBassBoostChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            bassBoost.setStrength((short) progress);
        }
        @Override public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    SeekBar.OnSeekBarChangeListener onSeekEqualizerChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            short value = (short) (equalizer.getBandLevelRange()[0] + progress);
            switch (seekBar.getId()) {
                case R.id.seek_0:
                    equalizer.setBandLevel((short) 0, value);
                    break;
                case R.id.seek_1:
                    equalizer.setBandLevel((short) 1, value);
                    break;
                case R.id.seek_2:
                    equalizer.setBandLevel((short) 2, value);
                    break;
                case R.id.seek_3:
                    equalizer.setBandLevel((short) 3, value);
                    break;
                case R.id.seek_4:
                    equalizer.setBandLevel((short) 4, value);
                    break;
            }
        }
        @Override public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            equalizer.setEnabled(isChecked);
            equalizer.setBandLevel((short) 0, (short) seek0.getProgress());
            equalizer.setBandLevel((short) 1, (short) seek1.getProgress());
            equalizer.setBandLevel((short) 2, (short) seek2.getProgress());
            equalizer.setBandLevel((short) 3, (short) seek3.getProgress());
            equalizer.setBandLevel((short) 4, (short) seek4.getProgress());

            bassBoost.setEnabled(isChecked);
        }
    };
}
