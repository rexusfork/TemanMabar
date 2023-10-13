package com.example.temanmabars;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temanmabars.Adapter.AdapterMessage;
import com.example.temanmabars.Model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Message_Activity extends AppCompatActivity {
    // Views
    private Toolbar toolbar;
    private TextView Username;
    private RecyclerView ReyclerViewChat;
    private EditText EditTextChat;
    private ImageButton BtnSend;

    // Intent
    private Intent intent;

    // Temp Data
    private String username, id_user, user, categorygame;
    private int cost;

    // Firebase
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference db;

    // Array List
    private ArrayList<Chat> list;

    // Adapter Chat
    private AdapterMessage adapterMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Initiateviews();

        ReadMessage();

        BtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Msg = EditTextChat.getText().toString();
                if (Msg.isEmpty()){
                    // No Command
                } else {
                    SendMessage(Msg);
                    Log.d("MSG", Msg);
                }
                EditTextChat.setText("");
            }
        });
    }

    private void Initiateviews(){
        // Views
        toolbar = findViewById(R.id.ToolbarMSGActivity);
        Username = findViewById(R.id.NameChat);
        ReyclerViewChat = findViewById(R.id.ReyclerViewMessageActivity);
        EditTextChat = findViewById(R.id.EditTextMessage);
        BtnSend = findViewById(R.id.BtnSendMessage);

        // Intent
        intent = getIntent();
        categorygame = intent.getStringExtra("categorygame");
        username = intent.getStringExtra("Username");
        id_user = intent.getStringExtra("id_user");
        cost = intent.getIntExtra("cost", 0);
        Username.setText(username);

        // Reycler Viewer
        ReyclerViewChat.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        ReyclerViewChat.setLayoutManager(layoutManager);

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileTemanMabar_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Username",username);
                intent.putExtra("id_user",id_user);
                intent.putExtra("categorygame", categorygame);
                intent.putExtra("cost", cost);
                finish();
            }
        });

        // Firebase
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user = firebaseUser.getUid();
    }

    private void ReadMessage(){
        list = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("Chat");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()){
                    Chat chat = Snapshot.getValue(Chat.class);
                    if ((user.equals(chat.getId_sender()) && id_user.equals(chat.getId_receiver()))
                            || (user.equals(chat.getId_receiver()) && id_user.equals(chat.getId_sender()))){
                        list.add(chat);
                    }
                }
                adapterMessage = new AdapterMessage(getApplicationContext(),list);
                ReyclerViewChat.setAdapter(adapterMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MSG", error.getMessage());
            }
        });
    }

    private void SendMessage(String Message){
        DatabaseReference ref = database.getReference();

        // Time
        LocalTime localTime = LocalTime.now();
        String time = localTime.getHour() + "." + localTime.getMinute();

        // Date
        String date = LocalDate.now().toString();

        // Save To Database
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id_sender",user);
        hashMap.put("id_receiver",id_user);
        hashMap.put("message",Message);
        hashMap.put("time",time);
        hashMap.put("date",date);
        ref.child("Chat").push().setValue(hashMap);
    }
}