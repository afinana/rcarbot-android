package com.middleand.mobile.rccar.framents;

import android.app.Dialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.middleand.mobile.rccar.R;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment implements OnClickListener
// implements ConnectionCallbacks,OnConnectionFailedListener
{

    private static final boolean D = true;
    private static final float ZOOM_LEVEL = 20;
    private static final float ACCURACY = 0.0001F;

    private static final String TAG = "MapFragment";
    MapView mMapView;
    Location mLastLocation = null;
    private GoogleMap googleMap;
    // private GoogleApiClient mGoogleApiClient;
    private OnMyLocationChangeListener listener;
    private PolygonOptions rectOptions;
    private boolean saveRoute = false;
    private Button saveRouteButton;

    public MapFragment() {
        super();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        if (D)
            Log.d(TAG, "onCreateView fragement");
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_location_info, container,
                false);
        saveRouteButton = (Button) v.findViewById(R.id.saveRoute);
        saveRouteButton.setOnClickListener(this);
        saveRouteButton.setBackgroundColor(Color.LTGRAY);

        saveRoute = false;

        // buildGoogleApiClient();
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity().getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
            // not available
            Log.w(TAG, "GooglePlay is not available");

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,
                    getActivity(), requestCode);
            dialog.show();

        } else { // Google Play Services are available

            mMapView = (MapView) v.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();// needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity());
            } catch (Exception e) {
                Log.e(TAG, "Maps initialization error:" + e.toString());
            }

            googleMap = mMapView.getMap();
            googleMap.setMyLocationEnabled(true);

            listener = new GmsLocationListener();
            googleMap.setOnMyLocationChangeListener(listener);

            // Instantiates a new Polyline object and adds points to define a
            // rectangle
            if (rectOptions == null) {
                initPolygon();
            }

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(37.35, -122.0)).zoom(7).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            // Instantiates a new Polyline object and adds points to define a
            // rectangle
            // rectOptions = new PolygonOptions()
            // .add(new LatLng(37.35, -122.0))
            // .add(new LatLng(37.45, -122.0)) // North of the previous point,
            // but at the same longitude
            // .add(new LatLng(37.45, -122.2)) // Same latitude, and 30km to the
            // west
            // .add(new LatLng(37.35, -122.2)) // Same longitude, and 16km to
            // the south
            // .add(new LatLng(37.35, -122.0)); // Closes the polyline.
            //
            // rectOptions.strokeColor(Color.MAGENTA);
            // rectOptions.strokeWidth(5);

            // // Get back the mutable Polyline
            // Polygon polyline = googleMap.addPolygon(rectOptions);

            // // latitude and longitudes
            // double latitude = 17.385044;
            // double longitude = 78.486671;
            //
            // // create marker
            // MarkerOptions marker = new MarkerOptions().position(
            // new LatLng(latitude, longitude)).title("Carbot");
            //
            // // Changing marker icon
            // marker.icon(BitmapDescriptorFactory
            // .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            //
            // // adding marker
            // googleMap.addMarker(marker);
            // CameraPosition cameraPosition = new CameraPosition.Builder()
            // .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
            // googleMap.animateCamera(CameraUpdateFactory
            // .newCameraPosition(cameraPosition));
        }
        // Perform any camera updates here
        return v;
    }

    private void initPolygon() {
        rectOptions = new PolygonOptions();
        rectOptions.strokeColor(Color.MAGENTA);
        rectOptions.strokeWidth(5);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_route:
                if (D)
                    Log.d(TAG, "Option Route selected");
                saveRoute();
                return true;
            default:
                return getActivity().onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.location, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null)
            mMapView.onLowMemory();
    }

    // Starts / Stop Saving Route
    private void saveRoute() {
        saveRoute = !saveRoute;
        if (saveRoute) {
            saveRouteButton.setBackgroundColor(Color.RED);
        } else {
            // Clears actual route
            initPolygon();
            saveRouteButton.setBackgroundColor(Color.LTGRAY);

        }
        if (D)
            Log.d(TAG, "saveRoute=" + saveRoute);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveRoute:

                saveRoute();
                break;
        }
    }

    private class GmsLocationListener implements OnMyLocationChangeListener {

        @Override
        public void onMyLocationChange(Location newLocation) {

            if (mLastLocation != null) {
                if (mLastLocation.distanceTo(newLocation) < ACCURACY) {
                    mLastLocation = newLocation;
                    rectOptions.add(new LatLng(newLocation.getLatitude(),
                            newLocation.getLongitude()));
                    paintNewPoint();
                }

            } else {
                mLastLocation = newLocation;
                rectOptions.add(new LatLng(newLocation.getLatitude(),
                        newLocation.getLongitude()));
                paintNewPoint();
            }

        }

        private void paintNewPoint() {
            // mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            // mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            if (D)
                Log.d(TAG,
                        "Last location latitude="
                                + String.valueOf(mLastLocation.getLatitude()));
            if (D)
                Log.d(TAG,
                        "Last location Longitude="
                                + String.valueOf(mLastLocation.getLongitude()));

            googleMap.clear();
            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(mLastLocation.getLatitude(), mLastLocation
                            .getLongitude())).title("Carbot");

            marker.icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_launcher));
            // adding marker
            googleMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(mLastLocation.getLatitude(),
                            mLastLocation.getLongitude())).zoom(ZOOM_LEVEL)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            @SuppressWarnings("unused")
            Polygon polyline = googleMap.addPolygon(rectOptions);

        }

    }

}