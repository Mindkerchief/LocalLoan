package com.lspuspcc.localloan;

import com.lspuspcc.localloan.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private ActivityMainBinding binding;
    public MapFragment mapFragment;
    private static final int MY_PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mapFragment = new MapFragment();
        replaceFragment(mapFragment);

        bottomNavigation = findViewById(R.id.bottomNavigationMenu);
        bottomNavigation.setOnItemSelectedListener(
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    return bottomNavigationBtnOnClick(item);
                }
            }
        );
        isPermissionsGranted();
    }

    private boolean bottomNavigationBtnOnClick(MenuItem item) {
        int menuItemId = item.getItemId();

        if (menuItemId == R.id.map) {
            replaceFragment(mapFragment);
            return true;
        }
        else if (menuItemId == R.id.compute) {
            replaceFragment(new ComputeFragment());
            return true;
        }
        else if (menuItemId == R.id.tracker) {
            replaceFragment(new TrackerFragment());
            return true;
        }
        else if (menuItemId == R.id.setting) {
            replaceFragment(new SettingFragment());
            return true;
        }
        return false;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, fragment)
                .addToBackStack(null)
                .commit();

    }

    private boolean isPermissionsGranted() {
        String[] permissions = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_REQUEST);
            return false;
        }
        else return true;
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}