package com.isi.pokedex_xc_hf.models;

import java.util.List;

public class Pokemon {

    private int id;
    private String name;
    private String url;
    private String image;
    private int height;
    private int weight;
    //private List<Ability> abilities;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public String getImage(){
        String[] urlParts = url.split("/");
        id = Integer.parseInt(urlParts[urlParts.length - 1]);
        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";
        return image;
        //TODO fix it
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }
}
