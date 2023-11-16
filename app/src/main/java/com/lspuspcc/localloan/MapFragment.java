package com.lspuspcc.localloan;

import com.lspuspcc.localloan.databinding.FragmentMapBinding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.ContextWrapper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
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
        binding = FragmentMapBinding.inflate(getLayoutInflater());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        binding.osmMap.setTileSource(TileSourceFactory.MAPNIK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    private void locateDevice() {
        Context context = getContext().getApplicationContext();
        GpsMyLocationProvider myLocationProvider = new GpsMyLocationProvider(context);
        MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(myLocationProvider, binding.osmMap);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableFollowLocation();

        Bitmap icon = BitmapFactory.decodeResource(getResources(), org.osmdroid.library.R.drawable.person);
        myLocationOverlay.setPersonIcon(icon);
        binding.osmMap.getOverlays().add(myLocationOverlay);
        myLocationOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                binding.osmMap.getOverlays().clear();
                binding.osmMap.getOverlays().add(myLocationOverlay);

                IMapController mapController = binding.osmMap.getController();;
                mapController.animateTo(myLocationOverlay.getMyLocation());
            }
        });
    }
}