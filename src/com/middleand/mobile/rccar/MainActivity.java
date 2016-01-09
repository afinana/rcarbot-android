package com.middleand.mobile.rccar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.middleand.mobile.rccar.MainApplication.TrackerName;
import com.middleand.mobile.rccar.framents.CameraFragment;
import com.middleand.mobile.rccar.framents.DeviceListFragment;
import com.middleand.mobile.rccar.framents.JoystickFragment;
import com.middleand.mobile.rccar.framents.MapFragment;
import com.middleand.mobile.rccar.logger.LogFragment;

public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To support back button is not apropiated for this version because
        // it's use framents instead activity
        //getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_HOME);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new JoystickFragment()).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_search:

                openSearch();
                return true;

            case R.id.action_settings:
                openSettings();
                return true;

            case R.id.action_main:
                openMain();
                return true;

            case R.id.action_map:
                openMap();
                return true;

            case R.id.action_camera:
                actionCamera();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void actionCamera() {

        trackingOption(R.string.action_camera);

        // Create fragment and give it an argument specifying the article it
        // should show
        CameraFragment cameraFragment = new CameraFragment();
        // Bundle args = new Bundle();
        // args.putInt(ArticleFragment.ARG_POSITION, position);
        // newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack so the user can navigate
        // back
        transaction.replace(R.id.container, cameraFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void openMap() {

        trackingOption(R.string.action_map);

        // Create fragment and give it an argument specifying the article it
        // should show
        MapFragment mapFragment = new MapFragment();
        // Bundle args = new Bundle();
        // args.putInt(ArticleFragment.ARG_POSITION, position);
        // newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack so the user can navigate
        // back
        transaction.replace(R.id.container, mapFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void openMain() {

        trackingOption(R.string.action_joystick);

        // Create fragment and give it an argument specifying the article it
        // should show
        JoystickFragment mainFragment = new JoystickFragment();
        // Bundle args = new Bundle();
        // args.putInt(ArticleFragment.ARG_POSITION, position);
        // newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack so the user can navigate
        // back
        transaction.replace(R.id.container, mainFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void openSettings() {

        trackingOption(R.string.action_settings);

        // Create fragment and give it an argument specifying the article it
        // should show
        DeviceListFragment settingFragment = new DeviceListFragment();
        // Bundle args = new Bundle();
        // args.putInt(ArticleFragment.ARG_POSITION, position);
        // newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack so the user can navigate
        // back
        transaction.replace(R.id.container, settingFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void openSearch() {

        trackingOption(R.string.action_search);

        // Create fragment and give it an argument specifying the article it
        // should show
        // BTConsoleFragment consoleFragment = new BTConsoleFragment();
        LogFragment consoleFragment = new LogFragment();
        // Bundle args = new Bundle();
        // args.putInt(ArticleFragment.ARG_POSITION, position);
        // newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack so the user can navigate
        // back
        transaction.replace(R.id.container, consoleFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void trackingOption(int strMenuId) {
        Tracker t = ((MainApplication) getApplication()).getTracker(
                TrackerName.APP_TRACKER);
        t.setScreenName(getString(strMenuId));
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    private void saveAppContext(String key, Object value) {
        ((MainApplication) getApplication()).saveAppContext(key, value);
    }

}
