package com.example.temanmabars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MessageTemanMabarActivity extends AppCompatActivity {
    // View
    private Toolbar toolbar;
    private TextView Username;
    private RecyclerView recyclerViewchat;
    private EditText EditTextChat;
    private ImageButton Btnsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_teman_mabar);
        InitiateView();
    }

    private void InitiateView(){
        // Views
        toolbar = findViewById(R.id.ToolbarMSGActivityTebar);
        Username = findViewById(R.id.NameChatTebar);
        recyclerViewchat = findViewById(R.id.ReyclerViewMessageActivityTebar);
        EditTextChat = findViewById(R.id.EditTextMessageTebar);
        Btnsend = findViewById(R.id.BtnSendMessageTebar);

        // Intent

        // Reycler Viewer
        recyclerViewchat.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerViewchat.setLayoutManager(layoutManager);

        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainTemanMabar_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
            }
        });
    }
}