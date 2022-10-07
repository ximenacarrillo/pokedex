package com.isi.pokedex_xc_hf.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.isi.pokedex_xc_hf.models.FavoritePokemon;
import com.isi.pokedex_xc_hf.models.Pokemon;
import com.isi.pokedex_xc_hf.services.DatabaseConnection;
import java.util.ArrayList;

public class FavoritePokemonController {
    public static ArrayList<Pokemon> getAllFavoritePokemons(Context context) {
        ArrayList<Pokemon> favoritePokemons = null;
        SQLiteDatabase dataBase = DatabaseConnection.getDataBase(context);

        Cursor cursor = dataBase.rawQuery("SELECT * FROM favorites", null);

        if (cursor.isBeforeFirst()) favoritePokemons = new ArrayList<>();

        while (cursor.moveToNext()) {
            Pokemon pokemon = new Pokemon();
            pokemon.setId(cursor.getInt(1));
            pokemon.setName(cursor.getString(2));
            //pokemon.getImage();
            favoritePokemons.add(pokemon);
        }

        cursor.close();
        DatabaseConnection.closeDataBase();

        return favoritePokemons;
    }

    public static boolean isFavorite(Context context, Pokemon pokemon){
        SQLiteDatabase dataBase = DatabaseConnection.getDataBase(context);
        Cursor cursor = dataBase.rawQuery("SELECT * FROM favorites where pokemonId = ? and pokemonName= ?", new String[] {String.valueOf(pokemon.getId()), String.valueOf(pokemon.getName())});

        boolean isFavorite =  cursor.getCount() > 0;

        cursor.close();
        DatabaseConnection.closeDataBase();

        return isFavorite;
    }

    public static long createFavoritePokemon(Context context, Pokemon Pokemon) {
        SQLiteDatabase db = DatabaseConnection.getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("pokemonId", Pokemon.getId());
        values.put("pokemonName", Pokemon.getName());

        return db.insertOrThrow("favorites", null, values);
    }


    public static int deleteFavoritePokemon(Context context, Pokemon Pokemon) {
        SQLiteDatabase db = DatabaseConnection.getDataBase(context);
        return db.delete("favorites", "pokemonId = ?", new String[] {String.valueOf(Pokemon.getId())});
    }
}
