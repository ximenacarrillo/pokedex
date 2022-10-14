package com.isi.pokedex_xc_hf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.isi.pokedex_xc_hf.models.Pokemon;
import com.isi.pokedex_xc_hf.pokeapi.ApiClient;
import com.isi.pokedex_xc_hf.pokeapi.PokeapiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPokemonActivity extends AppCompatActivity {
    //TODO remove this constant
    private final static String TAG = "Detail";


    TextView titleDetail;
    ImageView pokemonImage;
    TextView weightDetail;
    TextView heightDetail;
    TextView abilitiesDetail;
    int id;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokemon);


        titleDetail = findViewById(R.id.titleDetail);
        pokemonImage = findViewById(R.id.pokemonImage);
        weightDetail = findViewById(R.id.weightDetail);
        heightDetail = findViewById(R.id.heightDetail);
        abilitiesDetail = findViewById(R.id.abilitiesDetail);

        Bundle getData = getIntent().getExtras();
        id = getData.getInt("id");
        context = this;


        PokeapiServices services = ApiClient.getClient().create(PokeapiServices.class);
        Call<Pokemon> response = services.getPokemonDetail(id);

        response.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()){

                    Pokemon pokemon = response.body();

                    titleDetail.setText(pokemon.getName().toUpperCase());
                    weightDetail.setText(pokemon.getWeightInKg());
                    heightDetail.setText(pokemon.getHeightInMeters());
                    abilitiesDetail.setText(pokemon.getAbilitiesString());
                    Glide.with(context)
                            .load(pokemon.getImage())
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(pokemonImage);
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