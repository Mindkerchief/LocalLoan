package com.lspuspcc.localloan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    public MapFragment mapFragment;
    public FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
    }
    private void checkPermissions() {
        PermissionRequest requestPermission = new PermissionRequest(getApplicationContext());
        requestPermission.isLocationPermissionsGranted(this);
        requestPermission.isStoragePermissionsGranted(this);
        requestPermission.isInternetPermissionsGranted(this);
    }

    private boolean bottomNavigationBtnOnClick(MenuItem item) {
        int menuItemId = item.getItemId();

        if (menuItemId == R.id.map) {
            if (!mapFragment.isVisible()) {
                replaceFragment(mapFragment);
                showFragment(mapFragment);
            }
        }
        else if (menuItemId == R.id.compute) {
            if (mapFragment.isVisible())
                hideFragment(mapFragment);

            addFragment(new ComputeFragment());
            return true;
        }
        else if (menuItemId == R.id.tracker) {
            if (mapFragment.isVisible())
                hideFragment(mapFragment);

            addFragment(new TrackerFragment());
            return true;
        }
        else if (menuItemId == R.id.setting) {
            if (mapFragment.isVisible())
                hideFragment(mapFragment);

            addFragment(new SettingFragment());
            return true;
        }
        return false;
    }

    public void replaceFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment)
            .addToBackStack(null)
            .commit();
    }

    public void addFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void hideFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    public void showFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
}
