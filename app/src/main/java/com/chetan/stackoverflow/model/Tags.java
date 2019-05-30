package com.chetan.stackoverflow.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tags {

    @SerializedName("items")
    @Expose
    private List<Items> tags;

    public List<Items> getTags() {
        return tags;
    }

    public void setTags(List<Items> tags) {
        this.tags = tags;
    }
}
