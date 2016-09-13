package org.ls.musiceffector;

/**
 * Created by LS on 2016/9/11.
 */
public class MusicEffector {

    static {
        System.loadLibrary("music_effector");
    }

    public MusicEffector() {
        init();
    }

    private native void init();
    public native void destory();

    public native boolean isBassboostEnable();
    public native void setBassboostEnable(boolean isEnable);
    public native boolean isStrengthSupported();
    public native int getBassboostRoundedStrength();
    public native void setBassboostStrength(int strength);

}
