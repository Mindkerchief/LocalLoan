package com.lspuspcc.localloan;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

public class LocationTracking {
    private Context context;
    public Location currentLocation;
    private LocationManager locationManager;
    private LocationListener gpsLocationListener;
    private LocationListener networkLocationListener;
    private static final int MY_PERMISSION_REQUEST = 2;

    public double longitude;
    public double latitude;

    public LocationTracking(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        gpsLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle GPS location changes
                currentLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        networkLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle network location changes
                currentLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    // Method for requesting location updates
    public void requestLocationUpdates() {
        boolean hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (hasGps) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((MainActivity) context,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_REQUEST);
                return;
            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0F,
                    gpsLocationListener
            );
        }

        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,
                    0F,
                    networkLocationListener
            );
        }
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(gpsLocationListener);
        locationManager.removeUpdates(networkLocationListener);
    }

    public void getBestLocation() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((MainActivity) context,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_REQUEST);
            return;
        }

        Location locationByGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locationByGps != null) {
            currentLocation = locationByGps;
            // Use latitude and longitude as needed
        }

        Location locationByNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (locationByNetwork != null) {
            if (currentLocation == null || locationByNetwork.getAccuracy() < currentLocation.getAccuracy()) {
                currentLocation = locationByNetwork;
                // Use latitude and longitude as needed
            }
        }
    }
}
