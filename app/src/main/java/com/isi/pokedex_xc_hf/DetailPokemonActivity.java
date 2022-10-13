package com.isi.pokedex_xc_hf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.isi.pokedex_xc_hf.models.Pokemon;
import com.isi.pokedex_xc_hf.pokeapi.ApiClient;
import com.isi.pokedex_xc_hf.pokeapi.PokeapiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPokemonActivity extends AppCompatActivity {
    //TODO remove this constant
    private final static String TAG = "Detail";


    TextView textView;
    int id;
    Context context;
    Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokemon);

        Bundle getData = getIntent().getExtras();
        textView = findViewById(R.id.idTextViews);
        id = getData.getInt("id");
        context = this;


        PokeapiServices services = ApiClient.getClient().create(PokeapiServices.class);
        Call<Pokemon> response = services.getPokemonDetail(id);

        response.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()){

                    Pokemon pokemon = response.body();

                    textView.setText(pokemon.getAbilitiesString());
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });

    }
}