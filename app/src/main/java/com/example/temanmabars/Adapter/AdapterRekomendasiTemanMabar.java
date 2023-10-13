package com.example.temanmabars.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temanmabars.Model.Tebargame;
import com.example.temanmabars.ProfileTemanMabar_Activity;
import com.example.temanmabars.R;

import java.util.ArrayList;

public class AdapterRekomendasiTemanMabar extends RecyclerView.Adapter<AdapterRekomendasiTemanMabar.ViewHolder> {
    private Context context;
    private ArrayList<Tebargame> list;

    public AdapterRekomendasiTemanMabar(Context context, ArrayList<Tebargame> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rekomendasi_temanmabar,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tebargame model = list.get(position);
        holder.username.setText(model.getUsername());
        holder.categorygame.setText(model.getCategorygame());
        holder.cost.setText(model.getBiaya_coin() + " Coin/Match");
        holder.bio.setText(model.getBio());

        holder.pic_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileTemanMabar_Activity.class);
                intent.putExtra("category", model.getCategorygame());
                intent.putExtra("Username", model.getUsername());
                intent.putExtra("id_user", model.getId_user());
                intent.putExtra("cost", model.getBiaya_coin());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView pic_id;
        private TextView username, categorygame, cost, bio;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pic_id = itemView.findViewById(R.id.ImageRecomend);
            username = itemView.findViewById(R.id.UsernameRekomendasiTebar);
            categorygame = itemView.findViewById(R.id.CategoryGameTebar);
            cost = itemView.findViewById(R.id.CostRekomendasiTebar);
            bio = itemView.findViewById(R.id.BioRekomendasiTemanMabar);
        }
    }
}
