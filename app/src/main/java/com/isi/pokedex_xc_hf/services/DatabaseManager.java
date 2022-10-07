package com.isi.pokedex_xc_hf.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(Context context, String dataBaseName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dataBaseName, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS favorites(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pokemonId INTEGER NOT NULL,"+
                "pokemonName TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
