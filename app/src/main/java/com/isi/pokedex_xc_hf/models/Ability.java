package com.isi.pokedex_xc_hf.models;

import com.google.gson.annotations.SerializedName;

public class Ability {
    public class AbilityName {
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

        @Override
        public String toString(){
            return "-" + getName().toUpperCase() + "\n";
        }
    }
    @SerializedName("ability")
    private AbilityName ability;
    private boolean is_hidden;
    private int slot;

    public AbilityName getAbility() {
        return ability;
    }

    public void setAbility(AbilityName ability) {
        this.ability = ability;
    }

    public boolean isIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getName(){
        return ability.getName();
    }
}
