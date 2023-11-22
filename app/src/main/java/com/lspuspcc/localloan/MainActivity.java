package com.lspuspcc.localloan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    public MapFragment mapFragment;
    public ComputeFragment computeFragment;
    private TrackerFragment trackerFragment;
    public ComputeSavingsFragment computeSavingsFragment;
    public ComputeLoanFragment computeLoanFragment;

    public FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        checkPermissions();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mapFragment = new MapFragment();
        computeFragment = new ComputeFragment();
        trackerFragment = new TrackerFragment();
        computeSavingsFragment = new ComputeSavingsFragment();
        computeLoanFragment = new ComputeLoanFragment();

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
        PermissionRequest permissionRequest = new PermissionRequest(getApplicationContext());
        permissionRequest.requestLocationPermissions(this);
        permissionRequest.requestInternetPermissions(this);
    }

    public boolean bottomNavigationBtnOnClick(MenuItem item) {
        int menuItemId = item.getItemId();

        if (menuItemId == R.id.map) {
            if (!mapFragment.isVisible()) {
                replaceFragment(mapFragment);
            }
        }
        else if (menuItemId == R.id.compute) {
            if (!computeFragment.isVisible()) {
                replaceFragment(computeFragment);
            }
            return true;
        }
        else if (menuItemId == R.id.tracker) {
            if (!trackerFragment.isVisible()) {
                replaceFragment(trackerFragment);
            }
            return true;
        }
        return false;
    }

    public void replaceFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, fragment);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void hideFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragment);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }

    public void showFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public ComputeSavingsFragment getComputeSavingFragment() {
        return computeSavingsFragment;
    }

    public ComputeLoanFragment getComputeLoanFragment() {
        return computeLoanFragment;
    }
}
