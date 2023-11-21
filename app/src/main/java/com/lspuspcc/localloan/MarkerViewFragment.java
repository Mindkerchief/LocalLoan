package com.lspuspcc.localloan;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarkerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarkerViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private MainActivity mainActivity;

    public ComputeSavingsFragment computeSavingsFragment;
    public ComputeLoanFragment computeLoanFragment;

    public TextView establishmentNameText;
    public TextView establishmentBusinessTimeText;
    public TextView savingDetailsText;
    public TextView loanDetailsText;
    public Button savingsRedirectBtn;
    public Button loanRedirectBtn;
    private ImageButton markerDetailsCloseBtn;

    public String savingsInterestRate;
    public String savingsMinimumDeposit;
    public String loanAddOnRate;
    public String loanMinimumAmount;

    public MarkerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarkerView.
     */
    // TODO: Rename and change types and number of parameters
    public static MarkerViewFragment newInstance(String param1, String param2) {
        MarkerViewFragment fragment = new MarkerViewFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_marker_view, container, false);
        context = getActivity();
        mainActivity = (MainActivity) context;

        establishmentNameText = rootView.findViewById(R.id.establishmentNameText);
        establishmentBusinessTimeText = rootView.findViewById(R.id.establishmentBusinessTimeText);
        savingDetailsText = rootView.findViewById(R.id.savingDetailsText);
        loanDetailsText = rootView.findViewById(R.id.loanDetailsText);
        savingsRedirectBtn = rootView.findViewById(R.id.savingsRedirectBtn);
        loanRedirectBtn = rootView.findViewById(R.id.loanRedirectBtn);
        markerDetailsCloseBtn = rootView.findViewById(R.id.markerDeatilsCloseBtn);

        savingsRedirectBtn.setOnClickListener(view -> savingsRedirectBtnOnClick());
        loanRedirectBtn.setOnClickListener(view -> loanRedirectBtnOnClick());
        markerDetailsCloseBtn.setOnClickListener(view -> markerDetailsCloseBtnOnClick());
        return rootView;
    }

    private void markerDetailsCloseBtnOnClick() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(this);
        transaction.commit();
    }

    private void savingsRedirectBtnOnClick() {
        mainActivity.replaceFragment(mainActivity.computeFragment);
        mainActivity.computeFragment.clicked = "LOANS";

        computeSavingsFragment.textPrincipal = savingsMinimumDeposit;
        computeSavingsFragment.textAnnualInterestRate = savingsInterestRate;
        markerDetailsCloseBtnOnClick();
    }

    private void loanRedirectBtnOnClick() {
        mainActivity.replaceFragment(mainActivity.computeFragment);
        mainActivity.computeFragment.clicked = "SAVINGS";

        computeLoanFragment.textLoanAmount = loanMinimumAmount;
        computeLoanFragment.textInterestRate = loanAddOnRate;
        markerDetailsCloseBtnOnClick();
    }
}