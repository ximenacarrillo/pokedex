package com.isi.pokedex_xc_hf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.isi.pokedex_xc_hf.adapters.ListPokemonAdapter;
import com.isi.pokedex_xc_hf.models.Pokemon;
import com.isi.pokedex_xc_hf.models.PokemonResponse;
import com.isi.pokedex_xc_hf.pokeapi.ApiClient;
import com.isi.pokedex_xc_hf.pokeapi.PokeapiServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    private final static String TAG = "Pokedex";
    private final static int LIMIT = 1154;
    private final static int OFFSET = 0;
    private String filterText = "";
    private ListPokemonAdapter listPokemonAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        context = this;
        Bundle getData = getIntent().getExtras();
        if (getData != null) {
            String data = getData.getString("filter");
            filterText = data != null ? data : "";
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSearchResult);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);


        PokeapiServices services = ApiClient.getClient().create(PokeapiServices.class);
        Call<PokemonResponse> call = services.getPokemonList(LIMIT,OFFSET);

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, Response<PokemonResponse> response) {


                if (response.isSuccessful()){
                    ArrayList<Pokemon> pokeList = response.body().getResults();

                    pokeList.removeIf(pokemon -> !pokemon.getName().contains(filterText));
                    if (pokeList.size() == 0 ||pokeList.size() == LIMIT  ) {
                        setContentView(R.layout.pokemon_not_found);
                    }
                    listPokemonAdapter.setDataList(pokeList);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, @NonNull Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });

    }
}