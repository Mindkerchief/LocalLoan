package com.mindkerchief.localloan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LoanCellAdapter extends ArrayAdapter<AddLoanClass> {
    public LoanCellAdapter(Context context, List<AddLoanClass> loansList)
    {
        super(context, 0, loansList);
    }

    public View getView(int position, View convertview, ViewGroup parent)
    {
        AddLoanClass loans = getItem(position);
        if (convertview == null)
        {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.loancell, parent, false);
        }

        TextView LoanAmount = convertview.findViewById(R.id.cellLoanAmount);
        TextView Interest = convertview.findViewById(R.id.cellInterest);
        TextView Terms = convertview.findViewById(R.id.cellTerms);
        TextView monthlyPayment = convertview.findViewById(R.id.cellMonthlyPayment);

        LoanAmount.setText("Loan Amount (PHP): " + AddLoanClass.getLoanAmount());
        Interest.setText("Interest Rate (%): "+ AddLoanClass.getInterest());
        Terms.setText("Term (in Months): " + AddLoanClass.getnumberOfPayments());
        monthlyPayment.setText("Monthly Payment (PHP): " + AddLoanClass.getMonthlyPayment());

        return convertview;
    }
}
