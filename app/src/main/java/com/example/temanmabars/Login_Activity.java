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

import com.example.temanmabars.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login_Activity extends AppCompatActivity {
    // Views
    private EditText Email, Password;
    private Button BtnLogin, BtnLoginFacebook, BtnLoginGoogle;
    private TextView ToRegisterActivity;

    // Temp Data
    private String email, password;

    // Firebase Auth
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    // Firebase Firestore
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference userPath = database.collection("User");
    // Shared Pref
    private SharedPreferences preferences;

    // Data
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitViews();

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        ToRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToRegisterActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            String id_user = firebaseUser.getUid();
            userPath.whereEqualTo("id_user", id_user)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot snapshot : task.getResult()){
                                    user = snapshot.toObject(User.class);
                                }

                                // Temp Data
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("id_user", user.getId_user());
                                editor.putString("userType", user.getUserType());
                                editor.putString("name", user.getName());
                                editor.putString("username", user.getUsername());
                                editor.putString("email", user.getEmail());
                                editor.putString("gender", user.getGender());
                                editor.apply();

                                if ("Customer".equals(user.getUserType())){
                                    Intent intent = new Intent(getApplicationContext(), MainCustomer_Activity.class);
                                    Toast.makeText(getApplicationContext(),"Login Successfuly", Toast.LENGTH_SHORT).show();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else if ("TemanMabar".equals(user.getUserType())){
                                    Intent intent = new Intent(getApplicationContext(), MainTemanMabar_Activity.class);
                                    Toast.makeText(getApplicationContext(),"Login Successfuly", Toast.LENGTH_SHORT).show();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else if ("Admin".equals(user.getUserType())) {
                                    Intent intent = new Intent(getApplicationContext(), MainAdmin_Activity.class);
                                    Toast.makeText(getApplicationContext(), "Login Successfuly", Toast.LENGTH_SHORT).show();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
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
    }

    private void InitViews(){
        // Views
        Email = (EditText) findViewById(R.id.EmailLogin);
        Password = (EditText) findViewById(R.id.PasswordLogin);
        BtnLogin = (Button) findViewById(R.id.LoginBtn);
        BtnLoginFacebook = (Button) findViewById(R.id.facebook_btn);
        BtnLoginGoogle = (Button) findViewById(R.id.google_btn);
        ToRegisterActivity = (TextView) findViewById(R.id.ToRegisterActivity);

        // Firebase
        auth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences("Data",MODE_PRIVATE);
    }

    private void ToRegisterActivity(){
        Intent intent = new Intent(getApplicationContext(), Register_Activity.class);
        startActivity(intent);
    }

    private void Login(){
        // Get Data
        email = Email.getText().toString();
        password = Password.getText().toString();
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please fills all forms",Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                firebaseUser = auth.getCurrentUser();
                                assert firebaseUser != null;
                                final String IDUSER = firebaseUser.getUid();
                                userPath.whereEqualTo("id_user", IDUSER)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    for (QueryDocumentSnapshot snapshot : task.getResult()){
                                                        user = snapshot.toObject(User.class);
                                                        Log.d("MSG", user.getUserType());
                                                    }
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("id_user", user.getId_user());
                                                    editor.putString("userType", user.getUserType());
                                                    editor.putString("name", user.getName());
                                                    editor.putString("username", user.getUsername());
                                                    editor.putString("email", user.getEmail());
                                                    editor.putString("gender", user.getGender());
                                                    editor.apply();

                                                    if ("Customer".equals(user.getUserType())){
                                                        Intent intent = new Intent(getApplicationContext(), MainCustomer_Activity.class);
                                                        Toast.makeText(getApplicationContext(),"Login Successfuly", Toast.LENGTH_SHORT).show();
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    } else if ("TemanMabar".equals(user.getUserType())){
                                                        Intent intent = new Intent(getApplicationContext(), MainTemanMabar_Activity.class);
                                                        Toast.makeText(getApplicationContext(),"Login Successfuly", Toast.LENGTH_SHORT).show();
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    } else if ("Admin".equals(user.getUserType())){
                                                        Intent intent = new Intent(getApplicationContext(), MainAdmin_Activity.class);
                                                        Toast.makeText(getApplicationContext(),"Login Successfuly", Toast.LENGTH_SHORT).show();
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    }
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(getApplicationContext(),"Invalid Email or Password",Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MSG", e.getMessage());
                            Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {

    }
}