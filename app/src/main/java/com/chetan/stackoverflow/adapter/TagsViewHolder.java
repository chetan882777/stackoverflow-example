package com.chetan.stackoverflow.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetan.stackoverflow.R;
import com.chetan.stackoverflow.model.tags.TagItems;

public class TagsViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public TagsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView_tag_list_item);
    }

    public void bindViewHolder(TagItems tag){
        textView.setText(tag.getName());
    }
}
