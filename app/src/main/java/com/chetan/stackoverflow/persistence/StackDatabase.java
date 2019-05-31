package com.chetan.stackoverflow.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chetan.stackoverflow.model.tags.TagItems;

@Database(entities = {TagItems.class}, version = 1, exportSchema = false)
public abstract class StackDatabase extends RoomDatabase {


    public static final String DATABASE_NAME = "stack_db";

    public abstract TagsDao getTagsDao();

}
