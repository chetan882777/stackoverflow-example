package com.chetan.stackoverflow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetan.stackoverflow.R;
import com.chetan.stackoverflow.model.tags.TagItems;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsViewHolder> {

    private List<TagItems> tags;
    private Context mContext;

    public TagsAdapter(Context context, List<TagItems> tagItems){
        tags = tagItems;
        mContext = context;
    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_list_item, parent, false);
        return new TagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsViewHolder holder, int position) {
        holder.bindViewHolder(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }
}
