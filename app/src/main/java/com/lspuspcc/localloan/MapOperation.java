package com.lspuspcc.localloan;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

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
    private MapObjectWrapper mapObjectWrapper;
    public MapOperation(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;
        this.markerView = new MarkerViewFragment();
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.addFragment(markerView);
        mainActivity.hideFragment(markerView);

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

    public void setCustomMapOverlays(MapObjectWrapper mapObjectWrapper) {
        this.mapObjectWrapper = mapObjectWrapper;
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(mapObjectWrapper.mapMarkerOverlay,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        MainActivity mainActivity = (MainActivity) context;
                        if (markerView.isVisible())
                            mainActivity.hideFragment(markerView);

                        mainActivity.showFragment(markerView);

                        // Look for matching index in the arraylist
                        MapMarkerInfoWrapper markerInfoWrapper = mapObjectWrapper.mapMarkerInfoWrapper.get(index);

                        markerView.establishmentNameText.setText(markerInfoWrapper.establishmentName);
                        markerView.establishmentBusinessTimeText.setText("Opens " + markerInfoWrapper.businessDay + " " + markerInfoWrapper.businessHour);
                        markerView.savingDetailsText.setText("Minimum initial deposit of " + markerInfoWrapper.minimumInitialDeposit);
                        markerView.loanDetailsText.setText("Minimum loan amount of " + markerInfoWrapper.minimumLoanAmount);
                        markerView.savingsRedirectBtn.setText("Save for " + markerInfoWrapper.savingsInterestRate);
                        markerView.loanRedirectBtn.setText("Loan for " + markerInfoWrapper.monthlyAddonInterestRate);

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
