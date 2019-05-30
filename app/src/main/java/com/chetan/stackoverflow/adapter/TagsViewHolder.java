package com.chetan.stackoverflow.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetan.stackoverflow.R;
import com.chetan.stackoverflow.model.tags.TagItems;

public class TagsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textView;
    private TagsAdapter.TagClickListener listener;
    private TagItems tag;

    public TagsViewHolder(@NonNull View itemView, TagsAdapter.TagClickListener listener) {
        super(itemView);
        this.listener = listener;
        textView = itemView.findViewById(R.id.textView_tag_list_item);
        itemView.setOnClickListener(this);
    }

    public void bindViewHolder(TagItems tag){
        this.tag = tag;
        textView.setText(tag.getName());
    }

    @Override
    public void onClick(View v) {
        listener.onTagClicked(tag);
    }
}
