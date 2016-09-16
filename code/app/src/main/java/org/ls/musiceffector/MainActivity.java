package org.ls.musiceffector;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LS on 2016/9/14.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager_main)
    ViewPager pagerMain;
    @BindView(R.id.tab_main)
    TabLayout tabMain;

    private EqualizerFragment equalizerFragment;
    private VisualizerFragment visualizerFragment;

    private EffectorService effectorService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        equalizerFragment = new EqualizerFragment();
        visualizerFragment = new VisualizerFragment();

        pagerMain.setAdapter(new PagerMainAdapter(getSupportFragmentManager()));
        tabMain.setupWithViewPager(pagerMain);

        setSupportActionBar(toolbar);

        Intent serviceIntent = new Intent(this, EffectorService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent serviceIntent = new Intent(this, EffectorService.class);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(serviceConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            effectorService = ((EffectorService.EffectorBinder) service).getService();
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    List<Fragment> fragments = getSupportFragmentManager().getFragments();
                    for (Fragment fragment: fragments) {
                        ((BaseFragment) fragment).serviceConnection.onServiceConnected(name, service);
                    }
                }
            });
        }
        @Override public void onServiceDisconnected(ComponentName name) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment: fragments) {
                ((BaseFragment) fragment).serviceConnection.onServiceDisconnected(name);
            }
        }
    };

    class PagerMainAdapter extends FragmentPagerAdapter {

        public PagerMainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return equalizerFragment;
                case 1:
                    return visualizerFragment;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.equalizer);
                case 1:
                    return getString(R.string.visualizer);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
