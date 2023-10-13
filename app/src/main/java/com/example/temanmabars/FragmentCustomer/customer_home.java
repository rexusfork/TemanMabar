package com.example.temanmabars.FragmentCustomer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temanmabars.Adapter.AdapterRekomendasiTemanMabar;
import com.example.temanmabars.ListTemanMabar_Activity;
import com.example.temanmabars.Model.Tebargame;
import com.example.temanmabars.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class customer_home extends Fragment {
    // Views
    private ImageButton BtnML, BtnVALO, BtnGenshin, BtnMoreGame;
    private TextView TextSayHalloName;
    private RecyclerView RecommendedGamers;


    // Data Tempory
    private SharedPreferences preferences;
    private String TempUsername;
    private String TempGender;
    private String TempUID;
    private Tebargame tebargame;

    // Dialog
    private Dialog dialogGender;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dbUser = db.collection("User");
    private CollectionReference dbCustomer = db.collection("Customer");
    private CollectionReference dbTebargame = db.collection("Tebargame");

    // Data
    private ArrayList<Tebargame> listTebarGame;

    // Adapter
    private AdapterRekomendasiTemanMabar adapterRekomendasiTemanMabar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
        InitViews(view);

        BtnML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListTemanMabar_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Game", "Mobile Legend");
                startActivity(intent);
            }
        });

        BtnVALO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListTemanMabar_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Game", "Valorant");
                startActivity(intent);
            }
        });

        BtnGenshin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListTemanMabar_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Game", "Genshin Impact");
                startActivity(intent);
            }
        });

        BtnMoreGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void InitViews(View view){
        // Views
       BtnML = (ImageButton) view.findViewById(R.id.MobileLegendBtn);
       BtnVALO = (ImageButton) view.findViewById(R.id.ValoBtn);
       BtnGenshin = (ImageButton) view.findViewById(R.id.GenshinBtn);
       BtnMoreGame = (ImageButton) view.findViewById(R.id.MoreBtn);
       TextSayHalloName = (TextView) view.findViewById(R.id.txt_hello_cora);
       RecommendedGamers = (RecyclerView) view.findViewById(R.id.list_recommended_gamers);

       // Any detail
       preferences = getActivity().getSharedPreferences("Data",Context.MODE_PRIVATE);
       TempGender = preferences.getString("gender", "Default");
       TempUsername = preferences.getString("username", "Default");
       TempUID = preferences.getString("id_user","Default");
       TextSayHalloName.setText("Hello "+ TempUsername + "!");

       dialogGender = new Dialog(getActivity());
       if (TempGender.equals("Default")){
            OpenGenderDialog();
       }
       InitReyclerViewerRecomended();
    }

    private void InitReyclerViewerRecomended(){
        listTebarGame = new ArrayList<>();
        dbTebargame.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            for (DocumentSnapshot snapshot1 : snapshot.getDocuments()){
                                tebargame = snapshot1.toObject(Tebargame.class);
                                listTebarGame.add(tebargame);
                            }
                            adapterRekomendasiTemanMabar = new AdapterRekomendasiTemanMabar(getContext(), listTebarGame);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                            RecommendedGamers.setLayoutManager(layoutManager);
                            RecommendedGamers.setAdapter(adapterRekomendasiTemanMabar);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MSG", e.getMessage());
                    }
                });
    }

    private void OpenGenderDialog(){
        dialogGender.setContentView(R.layout.item_dialog);
        dialogGender.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton btnMale = dialogGender.findViewById(R.id.btn_male);
        ImageButton btnFemale = dialogGender.findViewById(R.id.btn_female);
        Button BtnOkay = dialogGender.findViewById(R.id.BtnOkayChooseGender);

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                TempGender = "Pria";
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("gender", "Pria");
                editor.apply();
            }
        });

        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempGender = "Perempuan";
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("gender", "Perempuan");
                editor.apply();
            }
        });

        BtnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TempGender.equals("Default")){
                    Toast.makeText(getActivity(),"Please Select Gender", Toast.LENGTH_SHORT).show();
                } else {
                    Query query = dbUser.whereEqualTo("id_user", TempUID);
                    Query queryCus = dbCustomer.whereEqualTo("id_user", TempUID);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                String docID = "";
                                for (QueryDocumentSnapshot snapshot : task.getResult()){
                                    docID = snapshot.getId();
                                }

                                Map<String, Object> updateData = new HashMap<>();
                                updateData.put("gender", TempGender);
                                dbUser.document(docID).update(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d("MSG", "Succes");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("MSG",e.getMessage());
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MSG",e.getMessage());
                        }
                    });

                    queryCus.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                String docID = "";
                                for (QueryDocumentSnapshot snapshot : task.getResult()){
                                    docID = snapshot.getId();
                                }

                                Map<String, Object> updateData = new HashMap<>();
                                updateData.put("gender", TempGender);
                                dbCustomer.document(docID).update(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d("MSG", "Succes");
                                        dialogGender.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("MSG",e.getMessage());
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MSG", e.getMessage());
                            dialogGender.dismiss();
                        }
                    });
                }
            }
        });
        dialogGender.show();
    }
}