package com.isi.pokedex_xc_hf.models;

import com.google.gson.annotations.SerializedName;

public class Move {
    public class MoveName{
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
    }

    @SerializedName("move")
    private MoveName moveName;

    public MoveName getMoveName() {
        return moveName;
    }

    public void setMoveName(MoveName moveName) {
        this.moveName = moveName;
    }

}
