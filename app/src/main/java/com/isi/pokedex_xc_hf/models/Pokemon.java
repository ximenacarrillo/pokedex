package com.isi.pokedex_xc_hf.models;

public class Pokemon {

    private int id;
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        String[] urlParts = url.split("/");
        int id = Integer.parseInt(urlParts[urlParts.length - 1]);
        setId(id);
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
