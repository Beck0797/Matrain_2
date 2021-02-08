package com.matrain.beck;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditNameFragment extends Fragment {

    SharedPreferences sharedPref;
    EditText usernameEditText;
    String username;
    SharedPreferences.Editor editor;
    Button change;
    FrameLayout frameLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditNameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditNameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditNameFragment newInstance(String param1, String param2) {
        EditNameFragment fragment = new EditNameFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_name, container, false);
        usernameEditText = v.findViewById(R.id.usernameEditText2);
        frameLayout = v.findViewById(R.id.myFrame);
        change = v.findViewById(R.id.btnChange);
        sharedPref = Objects.requireNonNull(getActivity()).getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usernameEditText.getText().toString().isEmpty()) {
                    if (usernameEditText.getText().toString().length() < 35) {
                        username = usernameEditText.getText().toString();
                        editor.putString("username", username);
                        editor.apply();
                        //removing the fragment
                        closeFragment();
                        MainActivity.btnChangeOnClick(username, getString(R.string.username_text).concat(username));

                    } else {
                        Toast.makeText(getActivity(), R.string.username_length_warning, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.emptyUserName), Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;
    }

    private void closeFragment() {
        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.myFrame);
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fm.beginTransaction();
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }

    }


}