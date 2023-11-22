package com.lspuspcc.localloan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionRequest {
    Context context;
    public static final byte STORAGE_PERMISSIONS = 01;
    public static final byte INTERNET_PERMISSIONS = 02;
    public static final byte LOCATION_PERMISSIONS = 03;

    public PermissionRequest(Context context) {
        this.context = context;
    }

    public void requestStoragePermissions(MainActivity mainActivity) {
        String[] storagePermissions = new String[] {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_DENIED
            && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(mainActivity, storagePermissions, STORAGE_PERMISSIONS);
        }
    }

    public void requestInternetPermissions(MainActivity mainActivity) {
        String[] internetPermissions = new String[] {
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE
        };

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED
            && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED
            && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(mainActivity, internetPermissions, INTERNET_PERMISSIONS);
        }
    }

    public void requestLocationPermissions(MainActivity mainActivity) {
        String[] locationPermissions = new String[] {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        };

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(mainActivity, locationPermissions, LOCATION_PERMISSIONS);
        }
    }
}
