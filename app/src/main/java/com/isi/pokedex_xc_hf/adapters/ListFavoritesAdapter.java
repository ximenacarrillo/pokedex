package com.isi.pokedex_xc_hf.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    TextView textViewInstructions;

    public ListFavoritesAdapter(Context context, TextView instructions){
        this.context = context;
        this.favoritesList = new ArrayList<>();
        this.textViewInstructions = instructions;
        setInstructions();
    }

    public void setInstructions() {
        String instructions = favoritesList.size() > 0 ? context.getString(R.string.favorites_instructions) : context.getString(R.string.favorites_empty);
        this.textViewInstructions.setText(instructions);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);
        textViewInstructions = parent.findViewById(R.id.textViewInstructions);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFavoritesAdapter.ViewHolder holder, int position){
        Pokemon pokemon = favoritesList.get(position);
        String image = pokemon.getImage();
        holder.textView.setText(pokemon.getName());

        Glide.with(context)
                .load(image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPokemonActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("id", pokemon.getId());
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

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
            showToast(pokemon.getName().toUpperCase() + " " + context.getString(R.string.favorite_deleted));
            favoritesList.remove(adapterPosition);
            textViewInstructions = (TextView) ((Activity) context).findViewById(R.id.textViewInstructions);
            setInstructions();
            notifyDataSetChanged();
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
