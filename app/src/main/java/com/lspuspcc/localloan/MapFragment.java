package com.lspuspcc.localloan;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

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
    private LocationTracking liveLocation;
    private GeoPoint currentlocation;
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = rootView.findViewById(R.id.osmMap);

        Context context = requireContext().getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));


        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(false);
        mapView.setMultiTouchControls(true);

        locateGpsBtnOnClick(context);


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
        liveLocation = new LocationTracking(context);
        liveLocation.requestLocationUpdates();
        liveLocation.getBestLocation();
        currentlocation = new GeoPoint(liveLocation.currentLocation.getLatitude(), liveLocation.currentLocation.getLongitude());

        IMapController mapController = mapView.getController();
        mapController.setZoom(currentZoom);
        mapController.animateTo(currentlocation, currentZoom, 500L);
    }

    public void zoomInBtnOnClick() {
        mapView.getController().zoomIn();
        currentZoom = mapView.getZoomLevelDouble();
    }

    public void zoomOutBtnOnClick() {
        mapView.getController().zoomOut();
        currentZoom = mapView.getZoomLevelDouble();
    }
}