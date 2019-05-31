package com.chetan.stackoverflow.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.chetan.stackoverflow.model.tags.TagItems;

import java.util.List;

@Dao
public interface TagsDao {

    @Query("SELECT * FROM tags")
    LiveData<List<TagItems>> getAllTags();

    @Query("SELECT * FROM tags WHERE name = :name")
    LiveData<TagItems> getTagByname(String name);

    @Insert
    void insetTags(TagItems ... tags);

    @Insert
    void insetTags(List<TagItems> tags);

    @Insert
    void insetTag(TagItems tags);

    @Update (onConflict = OnConflictStrategy.REPLACE)
    void updateTag(TagItems tag);

    @Delete
    void deleteTags(TagItems ... tags);

    @Delete
    void deleteTag(TagItems tag);

}
