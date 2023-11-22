package com.lspuspcc.localloan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoanDetailsActivity extends AppCompatActivity {

    private EditText loanEditText, interestEditText, termEditText, monthlyPaymentEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);
        initWidgets();
    }

    private void initWidgets() {
        loanEditText = findViewById(R.id.loanEditText);
        interestEditText = findViewById(R.id.interestEditText);
        termEditText = findViewById(R.id.termEditText);
        monthlyPaymentEditText = findViewById(R.id.monthlyPaymentEditText);
    }

    public void addLoan(View view) {
        LoanTrackerDBhelper loanDBhelper = LoanTrackerDBhelper.instanceofdatabase(this);

        String loanAmount = String.valueOf(loanEditText.getText());
        String interest = String.valueOf(interestEditText.getText());
        String term = String.valueOf(termEditText.getText());
        String monthlyPayment = String.valueOf(monthlyPaymentEditText.getText());

        int id = AddLoanClass.loanArrayList.size();
        AddLoanClass newLoanDetails = new AddLoanClass(loanAmount, monthlyPayment, term, id, interest);
        AddLoanClass.loanArrayList.add(newLoanDetails);

        loanDBhelper.addLoantoDatabase(newLoanDetails);
        finish();
    }

}