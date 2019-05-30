package com.chetan.stackoverflow.model.tags;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tags {

    @SerializedName("items")
    @Expose
    private List<TagItems> tags;

    public List<TagItems> getTags() {
        return tags;
    }

    public void setTags(List<TagItems> tags) {
        this.tags = tags;
    }
}
