package com.lspuspcc.localloan;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComputeLoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComputeLoanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComputeLoanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComputeLoanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComputeLoanFragment newInstance(String param1, String param2) {
        ComputeLoanFragment fragment = new ComputeLoanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compute_loan, container, false);

        EditText editTextLoanAmount = view.findViewById(R.id.editTextLoanAmount);
        EditText editTextInterestRate = view.findViewById(R.id.editTextInterestRate);
        EditText editTextTerm = view.findViewById(R.id.editTextTerm);
            editTextTerm.setInputType(InputType.TYPE_CLASS_NUMBER);
        Button buttonComputeLoan = view.findViewById(R.id.btnComputeLoan);
        TextView output = view.findViewById(R.id.textOutput);

        buttonComputeLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                editTextLoanAmount.clearFocus();
                editTextInterestRate.clearFocus();
                editTextTerm.clearFocus();

                String loanAmountStr = editTextLoanAmount.getText().toString();
                String interestRateStr = editTextInterestRate.getText().toString();
                String termMonthsStr = editTextTerm.getText().toString();

                double loanAmount = Double.parseDouble(loanAmountStr);
                double interestRate = Double.parseDouble(interestRateStr);
                int loanTermMonths = Integer.parseInt(termMonthsStr);

                double monthlyInterestRate = interestRate / 100 / 12;
                double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -loanTermMonths));
                double totalPayment = monthlyPayment * loanTermMonths;

                // Display the result or take further actions as needed
                String result = String.format("Loan Amount: Php " + loanAmount +
                        "\nInterest Rate: " + interestRate + "%%\nTerm (Months): " + loanTermMonths +
                        "\nMonthly Payment: Php " + monthlyPayment + "\nTotal Payment: " + totalPayment);
                output.setText(result);
            }
        });

        View rootView = view.findViewById(R.id.frameLayout2); // Replace with the actual ID of your root layout
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                editTextLoanAmount.clearFocus();
                editTextInterestRate.clearFocus();
                editTextTerm.clearFocus();

                return false;
            }
        });

    return view;
    }
}
