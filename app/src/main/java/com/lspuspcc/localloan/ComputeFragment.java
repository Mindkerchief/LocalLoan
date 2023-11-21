package com.lspuspcc.localloan;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComputeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComputeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public String clicked = "SAVINGS";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComputeSavingsFragment computeSavingsFragment;
    public ComputeLoanFragment computeLoanFragment;

    private FragmentTransaction fragmentTransaction;


    public ComputeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComputeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComputeFragment newInstance(String param1, String param2) {
        ComputeFragment fragment = new ComputeFragment();
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

        View view = inflater.inflate(R.layout.fragment_compute, container, false);
        Context context = getContext();
        MainActivity mainActivity = (MainActivity) context;
        fragmentTransaction = getChildFragmentManager().beginTransaction();

        computeSavingsFragment = mainActivity.computeSavingsFragment;
        computeLoanFragment = mainActivity.computeLoanFragment;

        if (clicked == "SAVINGS") {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, computeLoanFragment)
                    .commit();
            clicked = "LOAN";
        }
        else if (clicked == "LOAN") {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, computeSavingsFragment)
                    .commit();
            clicked = "SAVINGS";
        }

        view.findViewById(R.id.buttonLoan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked == "SAVINGS") {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, computeLoanFragment)
                            .commit();
                    clicked = "LOAN";
                }
            }
        });

        view.findViewById(R.id.buttonSavings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked == "LOAN") {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, computeSavingsFragment)
                            .commit();
                    clicked = "SAVINGS";
                }
            }
        });

        return view;
    }

    public void replaceFragment(Fragment fragment) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void addFragment(Fragment fragment) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment)
                .commit();
    }

    public void hideFragment(Fragment fragment) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    public void showFragment(Fragment fragment) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
}