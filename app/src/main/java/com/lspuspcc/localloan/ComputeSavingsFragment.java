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
 * Use the {@link ComputeSavingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComputeSavingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComputeSavingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComputeSavingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComputeSavingsFragment newInstance(String param1, String param2) {
        ComputeSavingsFragment fragment = new ComputeSavingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_compute_savings, container, false);

        EditText editTextPrincipal = view.findViewById(R.id.editTextPrincipal);
        EditText editTextAnnualInterestRate = view.findViewById(R.id.editTextAnnualInterestRate);
        EditText editTextTerm = view.findViewById(R.id.editTextTerm);
            editTextTerm.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditText editTextMonthlyContribution = view.findViewById(R.id.editTextMonthlyContribution);
        Button buttonComputeSavings = view.findViewById(R.id.btnComputeSavings);
        TextView output = view.findViewById(R.id.textOutput);

        buttonComputeSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                editTextPrincipal.clearFocus();
                editTextAnnualInterestRate.clearFocus();
                editTextTerm.clearFocus();
                editTextMonthlyContribution.clearFocus();

                String principalStr = editTextPrincipal.getText().toString();
                String annualInterestRateStr = editTextAnnualInterestRate.getText().toString();
                String termYearsStr = editTextTerm.getText().toString();
                String monthlyContributionStr = editTextMonthlyContribution.getText().toString();

                double principal = Double.parseDouble(principalStr);
                double annualInterestRate = Double.parseDouble(annualInterestRateStr);
                int loanTermYears = Integer.parseInt(termYearsStr);
                double monthlyContribution = Double.parseDouble(monthlyContributionStr);

                int compoundingPeriodsPerYear = 12;
                double monthlyInterestRate = annualInterestRate / compoundingPeriodsPerYear / 100;
                int totalCompoundingPeriods = compoundingPeriodsPerYear * loanTermYears;

                double futureValue = principal * Math.pow(1 + monthlyInterestRate, totalCompoundingPeriods);

                for (int i = 1; i <= totalCompoundingPeriods; i++) {
                    futureValue += monthlyContribution * Math.pow(1 + monthlyInterestRate, totalCompoundingPeriods - i);
                }

                String result = String.format("Principal: Php " + principal +
                        "\nAnnual Interest Rate: " + annualInterestRate + "%%\nTerm (Years): " + loanTermYears +
                        "\nMonthly Contribution: Php " + monthlyContribution + "\nFuture Value: Php " + futureValue);
                output.setText(result);
            }
        });

        View rootView = view.findViewById(R.id.frameLayout3); // Replace with the actual ID of your root layout
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                editTextPrincipal.clearFocus();
                editTextAnnualInterestRate.clearFocus();
                editTextTerm.clearFocus();
                editTextMonthlyContribution.clearFocus();

                return false;
            }
        });

        return view;
    }
}