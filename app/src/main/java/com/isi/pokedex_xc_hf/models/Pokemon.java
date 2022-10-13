package com.isi.pokedex_xc_hf.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Pokemon {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    private String image;
    @SerializedName("height")
    private int height;
    @SerializedName("weight")
    private int weight;

    @SerializedName("abilities")
    private List<Ability> abilities;



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
        if (id == 0 ) {
            String[] urlParts = url.split("/");
            id = Integer.parseInt(urlParts[urlParts.length - 1]);
        }
        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";
        return image;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public String getHeightInCentimeters(){
        return (height * 10.0) + " cm";
    }

    public String getHeightInMeters(){
        return (height / 10.0) + " m";
    }

    public String getAbilitiesString() {
        String toReturn = "";

        for (Ability ability: abilities) {
            toReturn += ability.getAbility().getName().toUpperCase() + "\n";
        }

        return toReturn;
    }
}
