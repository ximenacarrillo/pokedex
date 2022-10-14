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
import com.isi.pokedex_xc_hf.pokeapi.ApiClient;
import com.isi.pokedex_xc_hf.pokeapi.PokeapiServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "Pokedex";
    private static final int LIMIT = 21;

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
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, @NonNull Throwable t) {
                canCharge = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
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
                                if (!text.isEmpty()) {
                                    Intent intent = new Intent(context, SearchResultActivity.class);
                                    intent.putExtra("filter", text);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
                                }
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