package com.isi.pokedex_xc_hf;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.isi.pokedex_xc_hf.adapters.ListFavoritesAdapter;
import com.isi.pokedex_xc_hf.controllers.FavoritePokemonController;
import com.isi.pokedex_xc_hf.databinding.ActivityFavoritesBinding;
import com.isi.pokedex_xc_hf.models.Pokemon;
import com.isi.pokedex_xc_hf.swiper.SwipeHelper;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

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
        listFavoritesAdapter =new ListFavoritesAdapter(this);
        listFavoritesAdapter.addPokemonList(favoritesList);
        recyclerViewFavorites.setAdapter(listFavoritesAdapter);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));


        ItemTouchHelper.Callback callback=new SwipeHelper(listFavoritesAdapter);
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerViewFavorites);
    }
    private void displayDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Test");



        //SHOW DIALOG
        d.show();

    }
}