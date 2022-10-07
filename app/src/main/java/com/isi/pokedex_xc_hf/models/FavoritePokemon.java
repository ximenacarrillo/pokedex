package com.isi.pokedex_xc_hf.models;

public class FavoritePokemon {

    private long id;
    private Pokemon pokemon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
}
