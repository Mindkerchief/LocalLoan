package com.lspuspcc.localloan;

import com.lspuspcc.localloan.databinding.FragmentMapBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
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

    private FragmentMapBinding binding;
    private MapView mapView;

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
        binding = FragmentMapBinding.inflate(getLayoutInflater());
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
        mapView.setMultiTouchControls(true);

        GeoPoint targetPoint = new GeoPoint(14.070013, 121.325701);
        IMapController mapController = mapView.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(targetPoint);

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

    private void locateDevice() {
        Context context = this.getContext();
        GpsMyLocationProvider myLocationProvider = new GpsMyLocationProvider(context);
        MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(myLocationProvider, mapView);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableFollowLocation();

        Bitmap icon = BitmapFactory.decodeResource(getResources(), org.osmdroid.library.R.drawable.person);
        myLocationOverlay.setPersonIcon(icon);
        mapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                mapView.getOverlays().clear();
                mapView.getOverlays().add(myLocationOverlay);

                IMapController mapController = mapView.getController();
                mapController.animateTo(myLocationOverlay.getMyLocation());
            }
        });
    }

    public void zoomIn(FloatingActionButton v) {
        IMapController mapController = mapView.getController();
        mapController.setZoom(mapView.getZoomLevel() + 1);
        Toast.makeText(getContext(), "Zoom In", Toast.LENGTH_SHORT).show();
    }

    public void zoomOut(FloatingActionButton v) {
        IMapController mapController = mapView.getController();
        mapController.setZoom(mapView.getZoomLevel() - 1);
        Toast.makeText(getContext(), "Zoom Out", Toast.LENGTH_SHORT).show();
    }
}