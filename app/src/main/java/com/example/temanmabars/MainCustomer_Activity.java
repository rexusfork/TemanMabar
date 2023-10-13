package com.example.temanmabars;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.temanmabars.FragmentCustomer.customer_chat;
import com.example.temanmabars.FragmentCustomer.customer_home;
import com.example.temanmabars.FragmentCustomer.customer_profile;
import com.example.temanmabars.Model.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainCustomer_Activity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    // View
    private BottomNavigationView NavView;

    // Fragment
    private customer_chat fragment_chat = new customer_chat();
    private customer_home fragment_home = new customer_home();
    private customer_profile fragment_profile = new customer_profile();

    private SharedPreferences preferences;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection("Customer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);
        InitiateViews();
    }

    private void InitiateViews(){
        NavView = (BottomNavigationView) findViewById(R.id.BottomNavigation);
        NavView.setOnItemSelectedListener(this);
        NavView.setSelectedItemId(R.id.HomeCustomer);

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
                                Customer customer = snapshot.toObject(Customer.class);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("coin", customer.getCoin());
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
        if (item.getItemId() == R.id.HomeCustomer){
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentfl, fragment_home).commit();
            return true;
        } else if (item.getItemId() == R.id.chatCustomer){
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentfl, fragment_chat).commit();
            return true;
        } else if (item.getItemId() == R.id.ProfileCustomer){
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentfl, fragment_profile).commit();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {

    }
}