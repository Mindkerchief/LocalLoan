package com.lspuspcc.localloan;

import android.content.Context;

import androidx.fragment.app.FragmentTransaction;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MapOperation {
    private Context context;
    private MapView mapView;
    private MarkerViewFragment markerView;
    public MapOperation(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;
        this.markerView = new MarkerViewFragment();
    }
    public void setMapCompass() {
        CompassOverlay compassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);
    }

    public void enableMapControls() {
        RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(context, mapView);
        rotationGestureOverlay.setEnabled(true);

        mapView.setBuiltInZoomControls(false);
        mapView.setMultiTouchControls(true);
        mapView.getOverlays().add(rotationGestureOverlay);
    }

    public void setCurrentLocationOverlay() {
        MyLocationNewOverlay locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), mapView);
        locationOverlay.enableMyLocation();
        mapView.getOverlays().add(locationOverlay);
    }

    public void setCustomMapOverlays(ArrayList overlays) {
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(overlays,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        MainActivity mainActivity = (MainActivity) context;
                        if (markerView != null) {
                            mainActivity.closeFragment(markerView);
                        }

                        mainActivity.addFragment(markerView);
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, context);
        mOverlay.setFocusItemsOnTap(true);
        mapView.getOverlays().add(mOverlay);
    }
}
