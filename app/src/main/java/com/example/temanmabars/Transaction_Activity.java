package com.example.temanmabars;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Transaction_Activity extends AppCompatActivity {
    // Views
    private Toolbar toolbar;
    private TextView UsernameOrder, TextMatch, TextTotalQuantity, TextTotalPrice, TextUnitPrice, FinalPrice, TextBalance;
    private Button BtnCategory, BtnOrder;
    private ImageButton BtnMin, BtnPlus;

    // Intent
    private Intent intent;

    // Temp
    private String Username, category, id_user, id_myuser, DocId;
    private int cost, myCoin;
    private int number = 1, total = 1;

    // Shared Preferences
    private SharedPreferences preferences;

    // Firebase Firestore
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference;
    private CollectionReference customerRef;

    // Database Realtime
    private FirebaseDatabase db = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        InitViews();

        BtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddOrder();
            }
        });

        BtnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveMatch();
            }
        });

        BtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatch();
            }
        });
    }

    private void InitViews(){
        // Initiate Views
        toolbar = findViewById(R.id.ToolbarOrder);
        UsernameOrder = findViewById(R.id.TebarOrderText);
        BtnCategory = findViewById(R.id.BtnCategory);
        BtnOrder = findViewById(R.id.BtnOrder);
        BtnMin = findViewById(R.id.BtnMinMatch);
        BtnPlus = findViewById(R.id.BtnAddMatch);
        TextMatch = findViewById(R.id.MatchTotal);
        TextTotalPrice = findViewById(R.id.TextResultTotalPrice);
        TextTotalQuantity = findViewById(R.id.TextResultTotalQuantity);
        TextUnitPrice = findViewById(R.id.UnitText);
        FinalPrice = findViewById(R.id.TextFinalPrice);
        TextBalance = findViewById(R.id.TextBalance);

        // Preferences
        preferences = getSharedPreferences("Data", MODE_PRIVATE);
        myCoin = preferences.getInt("coin", 0);
        id_myuser = preferences.getString("id_user", "Default");
        TextBalance.setText("Balance : " + myCoin + " Coin");

        // Get Intent
        intent = getIntent();
        id_user = intent.getStringExtra("id_user");
        Username = intent.getStringExtra("Username");
        category = intent.getStringExtra("category");
        cost = intent.getIntExtra("cost", 0);
        UsernameOrder.setText(Username);
        BtnCategory.setText(category);
        TextUnitPrice.setText(cost + " Coin/Match");

        // Initiate Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProfileTemanMabar_Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Username", Username);
                i.putExtra("category", category);
                i.putExtra("id_user", id_user);
                i.putExtra("cost", cost);
                startActivity(i);
                finish();
            }
        });

        // Firebase
        reference = firestore.collection("Order");
        customerRef = firestore.collection("Customer");
    }

    private void addMatch(){
        number++;
        TextMatch.setText("" + number);
        total = number * cost;
        TextTotalPrice.setText(total + " Coin");
        TextTotalQuantity.setText(number + " Match");
        FinalPrice.setText("Final Price : " + total + " Coin");
    }

    private void RemoveMatch(){
        if (number > 1){
            number--;
            TextMatch.setText("" + number);
            total = number * cost;
            TextTotalPrice.setText(total + " Coin");
            TextTotalQuantity.setText(number + " Match");
            FinalPrice.setText("Final Price : " +total + " Coin");
        }
    }

    private void AddOrder(){
        if (myCoin >= total){
            // Local time and Date
            LocalTime localTime = LocalTime.now();
            String date = LocalDate.now().toString();
            String time;
            if (localTime.getMinute() < 10){
                time = localTime.getHour() + ".0" + localTime.getMinute();
            } else {
                time = localTime.getHour() + "." + localTime.getMinute();
            }

            // Hash Map
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id_user", id_myuser);
            hashMap.put("id_temanmabar", id_user);
            hashMap.put("categorygame", category);
            hashMap.put("total_harga", total);
            hashMap.put("total_match", number);
            hashMap.put("time", time);
            hashMap.put("date", date);

            customerRef.whereEqualTo("id_user", id_myuser)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot snapshot : task.getResult()){
                                    DocId = snapshot.getId();
                                }

                                HashMap<String, Object> update = new HashMap<>();
                                update.put("coin", myCoin - total);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("coin", myCoin - total);
                                editor.apply();

                                customerRef.document(DocId).update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("MSG", e.getMessage());
                                    }
                                });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MSG", e.getMessage());
                        }
                    });

            reference.add(hashMap)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Intent i = new Intent(getApplicationContext(), MainCustomer_Activity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Toast.makeText(getApplicationContext(),"Order Success",Toast.LENGTH_SHORT).show();
                                startActivity(i);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MSG", e.getMessage());
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(),"Coin Tidak Cukup", Toast.LENGTH_SHORT).show();
        }

    }
}