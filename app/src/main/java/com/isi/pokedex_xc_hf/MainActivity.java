package com.isi.pokedex_xc_hf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.isi.pokedex_xc_hf.adapters.ListPokemonAdapter;
import com.isi.pokedex_xc_hf.models.Pokemon;
import com.isi.pokedex_xc_hf.models.PokemonResponse;
import com.isi.pokedex_xc_hf.pokeapi.PokeapiServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //TODO remove this constant
    private final static String TAG = "Pokedex";
    private static final int LIMIT = 21;


    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;
    private int offset;
    private boolean canCharge;
    private Context context;
    private EditText editTextFilter;

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
                            offset += 20;
                            getData();
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        canCharge = true;
        getData();
    }

    private void getData() {
        PokeapiServices services = retrofit.create(PokeapiServices.class);
        Call<PokemonResponse> pokemonResponseCall = services.getPokemonList(LIMIT, offset);

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, Response<PokemonResponse> response) {
                canCharge = true;
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<Pokemon> listPokemon = pokemonResponse.getResults();

                    listPokemonAdapter.addPokemonList(listPokemon);

                } else {
                    //TODO handle error
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                canCharge = true;
                //TODO handle error
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    private void searchByName() {





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuButtonSearch:
                //TODO: implement feature

                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.search_pokemon, null);

                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Search Pokemon")
                        .setView(view)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                editTextFilter = ((AlertDialog) dialogInterface).findViewById(R.id.editTextPokemonName);
                                String text = editTextFilter != null ? editTextFilter.getText().toString() : "";
                                Intent intent = new Intent(context, SearchResultActivity.class);
                                intent.putExtra("filter", text);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();


                break;
            case R.id.menuButtonFavorites:
                Intent intent = new Intent(context, Favorites.class);
                startActivity(intent);
                break;
        }



        return super.onOptionsItemSelected(item);
    }
}