package com.todolist.app.data.local.dao;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import java.lang.Class;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SubtaskDao_Impl implements SubtaskDao {
  private final RoomDatabase __db;

  public SubtaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
