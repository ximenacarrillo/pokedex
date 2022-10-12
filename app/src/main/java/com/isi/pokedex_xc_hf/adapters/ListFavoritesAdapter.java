package com.isi.pokedex_xc_hf.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ListFavoritesAdapter extends RecyclerView.Adapter<ListFavoritesAdapter.ViewHolder> {
    private ArrayList<Pokemon> favoritesList;
    private Context context;

    public ListFavoritesAdapter(Context context){
        this.context = context;
        this.favoritesList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFavoritesAdapter.ViewHolder holder, int position){
        Pokemon pokemon = favoritesList.get(position);
        String image = pokemon.getImage(pokemon.getId());
        holder.textView.setText(pokemon.getName());

        Glide.with(context)
                .load(image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);


    }
    @Override
    public int getItemCount(){
        return favoritesList.size();
    }

    public void addPokemonList(ArrayList<Pokemon> listPokemon) {
        favoritesList.addAll(listPokemon);
        notifyDataSetChanged();
    }

    public void deleteFavorite(int adapterPosition) {
        Pokemon pokemon = favoritesList.get(adapterPosition);
        int rowsDeleted = FavoritePokemonController.deleteFavoritePokemon(context, pokemon);
        if (rowsDeleted == 1 ){
            showToast(pokemon.getName().toUpperCase() + " deleted from your favorites successfully!" );
            favoritesList.remove(adapterPosition);
        }else
        {
            showToast("Unable To Delete");
        }
        this.notifyItemRemoved(adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photoImageViewFavorite);
            textView = (TextView) itemView.findViewById(R.id.nameTextViewFavorite);
        }
    }

    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
