package com.isi.pokedex_xc_hf.models;

import com.google.gson.annotations.SerializedName;

public class Ability {
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

}
