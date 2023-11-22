package com.lspuspcc.localloan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class TrackerSavingsFragment extends Fragment {

    private ListView savingsListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker_loan, container, false);
        savingsListView = view.findViewById(R.id.loanListView);
        LoanTrackerDBhelper loanDBhelper = LoanTrackerDBhelper.instanceofdatabase(getActivity());
        //loanDBhelper.PopulateArrayList();
        setloanAdapter();
        Button newBtn =(Button) view.findViewById(R.id.newBtn);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newLoanIntent = new Intent (getActivity(), LoanDetailsActivity.class);
                startActivity(newLoanIntent);
            }
        });
        return view;
    }

    private void setloanAdapter() {
        SavingsCellAdaper savingsAdapter = new SavingsCellAdaper(getActivity().getApplicationContext(), AddSavingsClass.savingsArrayList);
        savingsListView.setAdapter(savingsAdapter);
    }

}