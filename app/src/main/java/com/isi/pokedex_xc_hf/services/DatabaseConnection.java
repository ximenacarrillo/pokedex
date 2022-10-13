package com.isi.pokedex_xc_hf.services;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseConnection {
    private static final String dataBaseName = "pokemons";
    private static final int version = 1;
    private static SQLiteDatabase dataBase;


    public static SQLiteDatabase getDataBase(Context context) {
        DatabaseManager dbManager = new DatabaseManager(context,dataBaseName,null,version);
        return dataBase = dbManager.getWritableDatabase();
    }

    public static void closeDataBase(){
        dataBase.close();
    }

    public static void importDatabase(Context context) {

            AssetManager am = context.getAssets();
            InputStream in = null;
            OutputStream out = null;

            try {
                in = am.open(dataBaseName);
                File bd = context.getDatabasePath(dataBaseName);
                out = new FileOutputStream(bd);

                byte[] buffer = new byte[8];

                int read = 0;
                while ((read = in.read(buffer)) != -1){
                    out.write(buffer,0,read);

                    buffer = new byte[8];

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
