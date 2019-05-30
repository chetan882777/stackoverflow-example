package com.chetan.stackoverflow.model.tags;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagItems {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("has_synonyms")
    @Expose
    private boolean hasSynonyms;

    public TagItems(String name, int count, boolean hasSynonyms) {
        this.name = name;
        this.count = count;
        this.hasSynonyms = hasSynonyms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasSynonyms() {
        return hasSynonyms;
    }

    public void setHasSynonyms(boolean hasSynonyms) {
        this.hasSynonyms = hasSynonyms;
    }
}
