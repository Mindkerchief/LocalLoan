package com.mindkerchief.localloan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;

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

    private MainActivity mainActivity;

    private ComputeSavingsFragment computeSavingsFragment;
    private ComputeLoanFragment computeLoanFragment;
    private MapView mapView;
    private MapOperation mapOperation;
    private LocationTracking liveLocation;
    private LocationManager locationManager;
    private GeoPoint currentLocation;
    private DatabaseHelper databaseHelper;
    private double currentZoom = 18.0;

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
        Context context = getActivity();

        mapView = rootView.findViewById(R.id.osmMap);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        databaseHelper = new DatabaseHelper(context);

        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        mainActivity = (MainActivity) context;

        computeSavingsFragment = mainActivity.getComputeSavingFragment();
        computeLoanFragment = mainActivity.getComputeLoanFragment();

        mapOperation = new MapOperation(context, mapView, computeSavingsFragment, computeLoanFragment);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapOperation.setMapCompass();
        mapOperation.enableMapControls();

        try { databaseHelper.copyDatabaseFromAssets(); }
        catch (IOException e) { }

        if (currentLocation == null)
            currentLocation = databaseHelper.getLastLocation();

        IMapController mapController = mapView.getController();
        mapController.setCenter(currentLocation);
        mapController.setZoom(18.0);
        mapOperation.setCustomMapOverlays(databaseHelper.getMapMarkers());

        FloatingActionButton locateGpsBtn = rootView.findViewById(R.id.locateGpsBtn);
        FloatingActionButton zoomOutBtn = rootView.findViewById(R.id.zoomOutBtn);
        FloatingActionButton zoomInBtn = rootView.findViewById(R.id.zoomInBtn);

        locateGpsBtn.setOnClickListener(view -> locateGpsBtnOnClick(context));
        zoomOutBtn.setOnClickListener(view -> zoomOutBtnOnClick());
        zoomInBtn.setOnClickListener(view -> zoomInBtnOnClick());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        IMapController mapController = mapView.getController();
        mapController.setCenter(currentLocation);
        mapController.setZoom(currentZoom);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        currentLocation = (GeoPoint) mapView.getMapCenter();
        currentZoom = mapView.getZoomLevelDouble();
    }

    public void locateGpsBtnOnClick(Context context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                try {
                    liveLocation = new LocationTracking(context);
                    liveLocation.requestLocationUpdates();
                    liveLocation.getBestLocation();

                    currentLocation = new GeoPoint(liveLocation.currentLocation.getLatitude(), liveLocation.currentLocation.getLongitude());
                    databaseHelper.setLastLocation(currentLocation);
                    currentZoom = 18.0;
                } catch (Exception e) {
                }
            }
        }

        if (currentLocation != null) {
            IMapController mapController = mapView.getController();
            currentZoom = 18.0;
            mapController.animateTo(currentLocation, currentZoom, 500L);
            mapOperation.setCurrentLocationOverlay();
            currentLocation = null;
        }
        else {
            mainActivity.addFragment(new LocationPromptFragment());
        }
    }

    public void zoomInBtnOnClick() {
        mapView.getController().zoomIn(300L);
        currentZoom = mapView.getZoomLevelDouble();
    }

    public void zoomOutBtnOnClick() {
        mapView.getController().zoomOut(300L);
        currentZoom = mapView.getZoomLevelDouble();
    }
}