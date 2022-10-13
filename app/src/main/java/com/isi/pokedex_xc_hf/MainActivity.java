package com.isi.pokedex_xc_hf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.isi.pokedex_xc_hf.adapters.ListPokemonAdapter;
import com.isi.pokedex_xc_hf.models.Pokemon;
import com.isi.pokedex_xc_hf.models.PokemonResponse;
import com.isi.pokedex_xc_hf.pokeapi.ApiClient;
import com.isi.pokedex_xc_hf.pokeapi.PokeapiServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //TODO remove this constant
    private final static String TAG = "Pokedex";
    private static final int LIMIT = 21;

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;
    private int offset;
    private boolean canCharge;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        recyclerView = findViewById(R.id.recyclerView);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (canCharge) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            canCharge = false;
                            offset += 21;
                            getData();
                        }
                    }
                }
            }
        });

        canCharge = true;
        getData();

    }

    private void getData(){
        PokeapiServices services = ApiClient.getClient().create(PokeapiServices.class);
        Call<PokemonResponse> call = services.getPokemonList(LIMIT,offset);

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, Response<PokemonResponse> response) {
                canCharge = true;
                if (response.isSuccessful()){
                    ArrayList<Pokemon> pokeList = response.body().getResults();
                    listPokemonAdapter.addPokemonList(pokeList);
                } else {
                    //TODO handle error
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, @NonNull Throwable t) {
                canCharge = true;
                //TODO handle error
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
    private void searchByName(String name){

        ArrayList<Pokemon> listPokemon = listPokemonAdapter.getDataList();
        listPokemon.removeIf(pokemon -> !pokemon.getName().contains(name));
        listPokemonAdapter.addPokemonList(listPokemon);

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        Intent intent = new Intent();
        switch (item.getItemId()){
            case R.id.menuButtonSearch:
                //TODO: implement feature
                //searchByName("as");
                break;
            case R.id.menuButtonFavorites:
                intent = new Intent(context, Favorites.class);
                break;
        }

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}