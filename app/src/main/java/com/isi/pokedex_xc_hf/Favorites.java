package com.isi.pokedex_xc_hf;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.isi.pokedex_xc_hf.adapters.ListFavoritesAdapter;
import com.isi.pokedex_xc_hf.controllers.FavoritePokemonController;
import com.isi.pokedex_xc_hf.models.Pokemon;
import com.isi.pokedex_xc_hf.swiper.SwipeHelper;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    RecyclerView recyclerViewFavorites;
    ListFavoritesAdapter listFavoritesAdapter;
    ArrayList<Pokemon> favoritesList=new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        recyclerViewFavorites= (RecyclerView) findViewById(R.id.recyclerViewFavorites);
        favoritesList = FavoritePokemonController.getAllFavoritePokemons(context);
        listFavoritesAdapter =new ListFavoritesAdapter(this, findViewById(R.id.textViewInstructions));
        listFavoritesAdapter.addPokemonList(favoritesList);
        listFavoritesAdapter.setInstructions();
        recyclerViewFavorites.setAdapter(listFavoritesAdapter);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));


        ItemTouchHelper.Callback callback=new SwipeHelper(listFavoritesAdapter);
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerViewFavorites);
    }
}