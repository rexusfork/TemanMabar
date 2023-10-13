package com.example.temanmabars;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register_Activity extends AppCompatActivity {
    // Initiate Views
    private EditText RegisterName, RegisterUsername, RegisterEmail, RegisterPassword;
    private Button BtnRegister;
    private TextView ToLoginActivity;

    // Temp Data
    private String Name,Username, Email,Password;

    // Firebase
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference reference = database.collection("User");
    private CollectionReference collectionReference = database.collection("Customer");
    private FirebaseUser user;

    // Shared Prefrences
    private SharedPreferences preferences;

    // Keys
    private static final String KEY_IDUSER = "id_user";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_COIN = "coin";
    private static final String KEY_Gender = "gender";
    private static final String KEY_UserType = "userType";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InitViews();

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        ToLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void InitViews(){
        // Initiate Views
        RegisterName = (EditText) findViewById(R.id.RegisterName);
        RegisterUsername = (EditText) findViewById(R.id.Registerusername);
        RegisterEmail = (EditText) findViewById(R.id.RegisterEmail);
        RegisterPassword = (EditText) findViewById(R.id.RegisterPassword);
        BtnRegister = (Button) findViewById(R.id.registerBtn);
        ToLoginActivity = (TextView) findViewById(R.id.ToLoginActivity);

        // Initiate Prefer
        preferences = getSharedPreferences("Data", MODE_PRIVATE);
    }

    private void Register(){
        Name = RegisterName.getText().toString();
        Username = RegisterUsername.getText().toString();
        Email = RegisterEmail.getText().toString();
        Password = RegisterPassword.getText().toString();

        if (Name.isEmpty() || Username.isEmpty() || Email.isEmpty() || Password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please Fills All Forms", Toast.LENGTH_LONG).show();
        } else {
            auth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                user = auth.getCurrentUser();
                                assert user != null;
                                final String id_user = user.getUid();
                                // For Login Data
                                Map<String, Object> NewUser = new HashMap<>();
                                NewUser.put(KEY_IDUSER,id_user);
                                NewUser.put(KEY_UserType, "Customer");
                                NewUser.put(KEY_NAME, Name);
                                NewUser.put(KEY_USERNAME, Username);
                                NewUser.put(KEY_EMAIL, Email);
                                NewUser.put(KEY_Gender, "Default");

                                // Saving Spesific Data
                                Map<String, Object> customer = new HashMap<>();
                                customer.put(KEY_IDUSER, id_user);
                                customer.put(KEY_UserType,"Customer");
                                customer.put(KEY_NAME, Name);
                                customer.put(KEY_USERNAME, Username);
                                customer.put(KEY_EMAIL, Email);
                                customer.put(KEY_COIN, 0);
                                customer.put(KEY_Gender, "Default");
                                collectionReference.add(customer)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        documentReference
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if (Objects.requireNonNull(task.getResult()).exists()){
                                                                            Toast.makeText(getApplicationContext(), "Register Sucessfull",Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getApplicationContext(),"Something went Wrong",Toast.LENGTH_SHORT).show();
                                                                Log.d("MSG", e.getMessage());
                                                            }
                                                        });


                                reference.add(NewUser)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (Objects.requireNonNull(task.getResult()).exists()){
                                                                    SharedPreferences.Editor editor = preferences.edit();
                                                                    editor.putString(KEY_IDUSER, id_user);
                                                                    editor.putString(KEY_UserType, "Customer");
                                                                    editor.putString(KEY_NAME, Name);
                                                                    editor.putString(KEY_USERNAME,Username);
                                                                    editor.putString(KEY_EMAIL, Email);
                                                                    editor.putString(KEY_Gender, "Default");
                                                                    editor.apply();
                                                                    Toast.makeText(getApplicationContext(),"Registration Successfull",Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(getApplicationContext(), MainCustomer_Activity.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"Something Went Wrong", Toast.LENGTH_SHORT).show();
                                                Log.d("MSG", e.getMessage());
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Something Went Wrong", Toast.LENGTH_SHORT).show();
                            Log.d("MSG", e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {

    }
}