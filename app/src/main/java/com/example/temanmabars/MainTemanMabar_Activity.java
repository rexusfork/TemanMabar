package com.example.temanmabars;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.temanmabars.FragmentTemanMabar.TemanMabar_chat;
import com.example.temanmabars.FragmentTemanMabar.TemanMabar_home;
import com.example.temanmabars.FragmentTemanMabar.TemanMabar_profile;
import com.example.temanmabars.Model.TemanMabar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainTemanMabar_Activity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    // View
    private BottomNavigationView NavView;

    // Fragment
    private TemanMabar_home temanMabar_home = new TemanMabar_home();
    private TemanMabar_chat temanMabar_chat = new TemanMabar_chat();
    private TemanMabar_profile temanMabar_profile = new TemanMabar_profile();

    // Shared Prefereces
    private SharedPreferences preferences;

    // Firebase Firestore
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection("TemanMabar");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teman_mabar);
        InitiateViews();

    }

    private void InitiateViews(){
        NavView = (BottomNavigationView) findViewById(R.id.BottomNavigationTebar);
        NavView.setOnItemSelectedListener(this);
        NavView.setSelectedItemId(R.id.HomeTemanMabar);

        preferences = getSharedPreferences("Data", MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "Default");
        reference
                .whereEqualTo("id_user", id_user)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot snapshot : task.getResult()){
                                TemanMabar temanMabar = snapshot.toObject(TemanMabar.class);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("coin", temanMabar.getCoin());
                                editor.apply();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MSG", e.getMessage());
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.HomeTemanMabar) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentTebar, temanMabar_home).commit();
            return true;
        } else if (item.getItemId() == R.id.chatTemanMabar) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentTebar, temanMabar_chat).commit();
            return true;
        } else if (item.getItemId() == R.id.ProfileTemanMabar) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentTebar,temanMabar_profile ).commit();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {

    }
}