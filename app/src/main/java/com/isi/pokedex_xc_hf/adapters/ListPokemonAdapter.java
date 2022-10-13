package com.isi.pokedex_xc_hf.adapters;

import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;

import android.util.Log;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Intent;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.isi.pokedex_xc_hf.DetailPokemonActivity;
import com.isi.pokedex_xc_hf.R;
import com.isi.pokedex_xc_hf.controllers.FavoritePokemonController;
import com.isi.pokedex_xc_hf.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {



    private ArrayList<Pokemon> dataList;
    private Context context;

    public ListPokemonAdapter(Context context){
        this.context = context;
        dataList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Pokemon pokemon = dataList.get(position);
        holder.textView.setText(pokemon.getName());

        Glide.with(context)
                .load(pokemon.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder
                        .setTitle(pokemon.getName().toUpperCase())
                        .setMessage(R.string.message_alert)
                        .setPositiveButton(context.getString(R.string.add_as_favorite), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    if(!FavoritePokemonController.isFavorite(context,pokemon)) {
                                        long newFavoriteId = FavoritePokemonController.createFavoritePokemon(context, pokemon);
                                        if (newFavoriteId > 0) {
                                            showToast(pokemon.getName().toUpperCase() + " "+ context.getString(R.string.added_as_favorite));
                                        } else {
                                            showToast(context.getString(R.string.error_creating_favorite));
                                        }
                                    }else{
                                        showToast(context.getString(R.string.is_already_favorite));
                                    }
                                } catch (Exception e){
                                    showToast(context.getString(R.string.error_creating_favorite));
                                    Log.println(Log.ERROR,"CREATE",e.getMessage());
                                }
                            }
                        })
                        .setNegativeButton(context.getString(R.string.view_details), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, DetailPokemonActivity.class);
                                Bundle bundle = new Bundle();

                                bundle.putInt("id", pokemon.getId());
                                intent.putExtras(bundle);

                                //startActivity(context,intent,bundle);
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });;

    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }

    public void addPokemonList(List<Pokemon> listPokemon) {
        dataList.addAll(listPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            textView = (TextView) itemView.findViewById(R.id.nameTextView);
        }
    }

    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void setDataList(ArrayList<Pokemon> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public ArrayList<Pokemon> getDataList() {
        return dataList;
    }
}
