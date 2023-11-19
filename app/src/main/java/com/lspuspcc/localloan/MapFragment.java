package com.lspuspcc.localloan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MapView mapView;
    private MapOperation mapOperation;
    private MyLocationNewOverlay locationOverlay;
    private LocationTracking liveLocation;
    private LocationManager locationManager;
    private GeoPoint currentlocation = new GeoPoint(12.70000, 122.70000);;
    private double currentZoom = 7.5;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        Context context = requireContext().getApplicationContext();
        mapView = rootView.findViewById(R.id.osmMap);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        mapOperation = new MapOperation(context, mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapOperation.setMapCompass();
        mapOperation.enableMapControls();

        locateGpsBtnOnClick(context);
        mapOperation.setCustomMapOverlays(getOverlays());

        // FloatingActionButtons OnClickListeners
        FloatingActionButton locateGpsBtn = rootView.findViewById(R.id.locateGpsBtn);
        locateGpsBtn.setOnClickListener(view -> locateGpsBtnOnClick(context));

        FloatingActionButton zoomOutBtn = rootView.findViewById(R.id.zoomOutBtn);
        zoomOutBtn.setOnClickListener(view -> zoomOutBtnOnClick());

        FloatingActionButton zoomInBtn = rootView.findViewById(R.id.zoomInBtn);
        zoomInBtn.setOnClickListener(view -> zoomInBtnOnClick());

        return rootView;
    }

    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    public void locateGpsBtnOnClick(Context context) {
        // Check for permissions
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        { // Check if GPS is enabled
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                try {
                    liveLocation = new LocationTracking(context);
                    liveLocation.requestLocationUpdates();
                    liveLocation.getBestLocation();
                    currentlocation = new GeoPoint(liveLocation.currentLocation.getLatitude(), liveLocation.currentLocation.getLongitude());
                    currentZoom = 18.0;
                } catch (Exception e) {
                }
            }
        }

        if (currentlocation != null) {
            IMapController mapController = mapView.getController();
            mapController.animateTo(currentlocation, currentZoom, 500L);
            mapOperation.setCurrentLocationOverlay();
            currentlocation = null;
        }
        else {
            Toast.makeText(context, "Location is unavailable", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    public void zoomInBtnOnClick() {
        mapView.getController().zoomIn();
        currentZoom = mapView.getZoomLevelDouble();
    }

    public void zoomOutBtnOnClick() {
        mapView.getController().zoomOut();
        currentZoom = mapView.getZoomLevelDouble();
    }

    private ArrayList getOverlays() {
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("Home", "Jhondale's Location",
                  new GeoPoint(14.070013,121.324701)));

        return items;
    }
}