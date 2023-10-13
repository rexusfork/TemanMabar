package com.example.temanmabars.FragmentTemanMabar;

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

public class TemanMabar_profile extends Fragment {
    // Views
    private de.hdodenhof.circleimageview.CircleImageView profiletebar;
    private Button Btninfo, BtnPayment, BtnNotif, BtnEditText, BtnPrivacy, BtnLogout;
    private TextView txtprofile;

    // Firebase Auth
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    // Preferences
    private SharedPreferences preferences;

    // Temp String
    private String name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teman_mabar_profile, container, false);
        InitiateViews(view);

        BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });
        return view;
    }

    private void InitiateViews(View view){
        BtnLogout = view.findViewById(R.id.TemanMabarlogoutBtn);
        txtprofile = view.findViewById(R.id.TemanMabar_UsernameProfileText);

        // Preferences
        preferences = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        name = preferences.getString("name", "Default");

        // Set Text
        txtprofile.setText(name);


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