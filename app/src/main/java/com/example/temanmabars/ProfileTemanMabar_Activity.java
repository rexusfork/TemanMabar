package com.example.temanmabars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileTemanMabar_Activity extends AppCompatActivity {
    // Views
    private TextView TextUsername, TextCategoryGame;
    private Toolbar toolbar;
    private Button BtnOrder, BtnChat;

    // Intent
    private Intent intent;


    // Temp Data
    private String categorygame, Username, id_user;
    private int cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teman_mabar);
        InitViews();

        BtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Transaction_Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Username", Username);
                i.putExtra("category", categorygame);
                i.putExtra("id_user", id_user);
                i.putExtra("cost", cost);
                startActivity(i);
            }
        });

        BtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Message_Activity.class);
                i.putExtra("id_user", id_user);
                i.putExtra("Username", Username);
                i.putExtra("category",categorygame);
                i.putExtra("cost", cost);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }

    private void InitViews(){
        // Views
        TextUsername = findViewById(R.id.UsernameProfileTemanMabar);
        TextCategoryGame = findViewById(R.id.NameGames);
        toolbar = findViewById(R.id.ToolbarProfileTebar);
        BtnOrder = findViewById(R.id.BtnOrderProfileTebar);
        BtnChat = findViewById(R.id.BtnChatProfileTebar);

        // Intent
        intent = getIntent();

        // String Temp
        categorygame = intent.getStringExtra("category");
        Username = intent.getStringExtra("Username");
        id_user = intent.getStringExtra("id_user");
        cost = intent.getIntExtra("cost", 0);

        // Set Text
        TextUsername.setText(Username);
        TextCategoryGame.setText(categorygame);

        // Initiate Toolbar
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
    }
}