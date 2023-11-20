package com.lspuspcc.localloan;

import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MapObjectWrapper {
    public ArrayList<OverlayItem> mapMarkerOverlay;
    public ArrayList<MapMarkerInfoWrapper> mapMarkerInfoWrapper;

    public MapObjectWrapper() {
        mapMarkerOverlay = new ArrayList<>();
        mapMarkerInfoWrapper = new ArrayList<>();
    }
}
