package com.example.temanmabars;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temanmabars.Adapter.AdapterTemanMabar;
import com.example.temanmabars.Model.Tebargame;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListTemanMabar_Activity extends AppCompatActivity {
    // Views
    private TextView TextTitle;
    private Toolbar toolbar;
    private RecyclerView ListGame;

    // Intent
    private Intent intent;

    // Data
    private String Category;
    private ArrayList<Tebargame> list;

    // Firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("Tebargame");

    // Adapter
    private AdapterTemanMabar temanMabar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_teman_mabar);
        InitViews();
    }

    private void InitViews(){
        // Initiate Views
        TextTitle = findViewById(R.id.ListTemanMabarText);
        toolbar = findViewById(R.id.ToolbarListTemanMabarByCategory);
        ListGame = findViewById(R.id.ReyclerViewListTebar);

        // Intent
        intent = getIntent();
        Category = intent.getStringExtra("Game");
        TextTitle.setText(Category);

        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainCustomer_Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

        // Reycler Viewer Initiate
        InitiateReyclerViewerListTebar();
    }

    private void InitiateReyclerViewerListTebar(){
        list = new ArrayList<>();
        reference
                .whereEqualTo("categorygame", Category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot querySnapshot = task.getResult();
                            for (DocumentSnapshot snapshot : querySnapshot.getDocuments()){
                                Tebargame tebargame = snapshot.toObject(Tebargame.class);
                                list.add(tebargame);
                            }
                            temanMabar = new AdapterTemanMabar(getApplicationContext(), list);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            ListGame.setLayoutManager(layoutManager);
                            ListGame.setAdapter(temanMabar);
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
}