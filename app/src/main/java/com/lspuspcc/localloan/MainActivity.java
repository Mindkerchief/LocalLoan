package com.lspuspcc.localloan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.lspuspcc.localloan.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        replaceFragment(new MapFragment());

        binding.bottomNavigationMenu.setOnItemSelectedListener(item -> {
            int menuItemId = item.getItemId();

            if (menuItemId == R.id.map) {
                replaceFragment(new MapFragment());
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

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }
}