package com.todolist.app.data.local.database;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Type converters for Room Database
 * Chuyển đổi giữa Java types và SQLite types
 */
public class Converters {

    /**
     * Convert timestamp (Long) to Date object
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Convert Date object to timestamp (Long)
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}