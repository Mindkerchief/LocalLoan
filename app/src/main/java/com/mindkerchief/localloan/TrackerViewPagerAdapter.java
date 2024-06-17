package com.mindkerchief.localloan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TrackerViewPagerAdapter extends FragmentStateAdapter {
    public TrackerViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TrackerLoanFragment();
            case 1:
                return new TrackerSavingsFragment();
            default:
                    return new TrackerLoanFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
