package com.example.temanmabars.FragmentCustomer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.temanmabars.Login_Activity;
import com.example.temanmabars.R;
import com.google.firebase.auth.FirebaseAuth;

public class customer_profile extends Fragment {
    // View
    private Button BtnAccountInformation, BtnTopUp, BtnAppSetting, BtnPrivacy, BtnLogOut;
    private TextView textprofile;
    // Shared Preferences
    private SharedPreferences preferences;

    // Firebase Auth
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String TempName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_profile, container, false);
        InitViews(view);



        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        return view;
    }

    private void InitViews(View view){
        // View
        BtnLogOut = (Button) view.findViewById(R.id.CustomerlogoutBtn);
        textprofile = (TextView) view.findViewById(R.id.UsernameProfileText);


        // Preferences
        preferences = getActivity().getSharedPreferences("Data",Context.MODE_PRIVATE);
        TempName = preferences.getString("name", "Default");
        textprofile.setText(TempName);
    }

    private void LogOut(){
        auth.signOut();
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(getActivity(), Login_Activity.class);
        Toast.makeText(getContext(), "Log out Successfuly", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}