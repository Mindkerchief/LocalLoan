package com.lspuspcc.localloan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SavingsCellAdaper extends ArrayAdapter<AddSavingsClass> {
    public SavingsCellAdaper(Context context, List<AddSavingsClass> savingsList)
    {
        super(context, 0, savingsList);
    }

    public View getView(int position, View convertview, ViewGroup parent)
    {
        AddSavingsClass savings = getItem(position);
        if (convertview == null)
        {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.savingscell, parent, false);
        }

        TextView principal = convertview.findViewById(R.id.cellPrincipal);
        TextView Interest = convertview.findViewById(R.id.cellInterest);
        TextView Terms = convertview.findViewById(R.id.cellTerms);
        TextView monthlyContribution = convertview.findViewById(R.id.cellMonthlyContribution);

        principal.setText("Loan Amount (PHP): " + AddSavingsClass.getPrincipal());
        Interest.setText("Interest Rate (%): "+ AddSavingsClass.getInterest());
        Terms.setText("Term (in Months): " + AddSavingsClass.getnumberOfPayments());
        monthlyContribution.setText("Monthly Payment (PHP): " + AddSavingsClass.getMonthlyContribution());

        return convertview;
    }
}
