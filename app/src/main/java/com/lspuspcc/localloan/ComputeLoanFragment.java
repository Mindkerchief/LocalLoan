package com.lspuspcc.localloan;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
    public EditText editTextLoanAmount;
    public EditText editTextInterestRate;

    public static String textLoanAmount;
    public static String textInterestRate;

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

        editTextLoanAmount = view.findViewById(R.id.editTextLoanAmount);
            restrictDecimalPlaces(editTextLoanAmount, 2);
        editTextInterestRate = view.findViewById(R.id.editTextInterestRate);
        EditText editTextTerm = view.findViewById(R.id.editTextTerm);
            editTextTerm.setInputType(InputType.TYPE_CLASS_NUMBER);
        Button buttonComputeLoan = view.findViewById(R.id.btnComputeLoan);
        TextView output = view.findViewById(R.id.textOutput);
        TableLayout tableLoanAmortization = view.findViewById(R.id.tableLoanAmortization);
        View rootView = view.findViewById(R.id.frameLayout2);
        HorizontalScrollView horizontalScrollView = view.findViewById(R.id.horizontalScrollViewLoan);
        EditText finalEditTextLoanAmount = editTextLoanAmount;

        if (textLoanAmount != null) {
            editTextLoanAmount.setText(textLoanAmount);
            textLoanAmount = null;
        }
        if (textInterestRate != null) {
            editTextInterestRate.setText(textInterestRate);
            textInterestRate = null;
        }

        buttonComputeLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFocus(v, finalEditTextLoanAmount, editTextInterestRate, editTextTerm);

                String loanAmountStr = editTextLoanAmount.getText().toString();
                String interestRateStr = editTextInterestRate.getText().toString();
                String termMonthsStr = editTextTerm.getText().toString();
                try {
                    double loanAmount = Double.parseDouble(loanAmountStr);
                    double interestRate = Double.parseDouble(interestRateStr);
                    int loanTermMonths = Integer.parseInt(termMonthsStr);

                    double monthlyInterestRate = interestRate / 12 / 100;
                    double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -loanTermMonths));
                    double totalPayment = monthlyPayment * loanTermMonths;
                    monthlyPayment = Math.round(monthlyPayment * 100);
                    monthlyPayment = monthlyPayment / 100;
                    totalPayment = Math.round(totalPayment * 100);
                    totalPayment /= 100;

                    String result = "Loan Amount: Php " + loanAmount +
                            "\nInterest Rate: " + interestRate + "%\nTerm (Months): " + loanTermMonths +
                            "\nMonthly Payment: Php " + monthlyPayment + "\nTotal Payment: Php " + totalPayment + "\n";
                    output.setText(result);

                    tableLoanAmortization.removeAllViews();
                    TableLayout output = generateAmortizationTable(loanAmount, monthlyInterestRate, loanTermMonths, monthlyPayment);
                    tableLoanAmortization.addView(output);
                }
                catch(NumberFormatException e) {
                    output.setText("");
                    tableLoanAmortization.removeAllViews();

                    if (loanAmountStr.isEmpty()) {
                        finalEditTextLoanAmount.setError("Enter a Valid Loan Amount");
                    }
                    if (interestRateStr.isEmpty()) {
                        editTextInterestRate.setError("Enter a Valid Interest Rate");
                    }
                    if (termMonthsStr.isEmpty()) {
                        editTextTerm.setError("Enter a Valid Term");
                    }
                }
            }
        });

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearFocus(v, finalEditTextLoanAmount, editTextInterestRate, editTextTerm);
                return false;
            }
        });

        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearFocus(v, finalEditTextLoanAmount, editTextInterestRate, editTextTerm);
                return false;
            }
        });

    return view;
    }

    private void restrictDecimalPlaces(EditText editText, final int maxDecimalPlaces) {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();

                int dotIndex = input.indexOf(".");
                if (dotIndex != -1 && input.length() - dotIndex > maxDecimalPlaces + 1) {
                    editable.delete(dotIndex + maxDecimalPlaces + 1, editable.length());
                }
            }
        });
    }

    private TableLayout generateAmortizationTable(double loanAmount, double monthlyInterestRate, int termMonths, double monthlyPayment) {
        TableLayout tableLayout = new TableLayout(getContext());
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        TableRow headerRow = new TableRow(getContext());

        addTableCell(headerRow, "No.");
        addTableCell(headerRow, "Payment");
        addTableCell(headerRow, "Principal");
        addTableCell(headerRow, "Interest");
        addTableCell(headerRow, "Total Interest");
        addTableCell(headerRow, "Balance");

        tableLayout.addView(headerRow);

        double totalInterest = 0;

        for (int i = 1; i <= termMonths; i++) {
            TableRow row = new TableRow(getContext());

            addTableCell(row, String.valueOf(i));
            addTableCell(row, String.format("Php %.2f", monthlyPayment));

            double interest = loanAmount * monthlyInterestRate;

            double principal = Math.min(loanAmount, monthlyPayment - interest);

            addTableCell(row, String.format("Php %.2f", principal));
            addTableCell(row, String.format("Php %.2f", interest));

            totalInterest += interest;
            addTableCell(row, String.format("Php %.2f", totalInterest));

            loanAmount -= principal;
            addTableCell(row, String.format("Php %.2f", loanAmount));

            tableLayout.addView(row);
        }

        return tableLayout;
    }


    private void addTableCell(TableRow row, String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(16, 8, 16, 8);
        row.addView(textView);
    }

    private void clearFocus(View v, EditText editTextLoanAmount, EditText editTextInterestRate, EditText editTextTerm) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

        editTextLoanAmount.clearFocus();
        editTextInterestRate.clearFocus();
        editTextTerm.clearFocus();
    }
}
