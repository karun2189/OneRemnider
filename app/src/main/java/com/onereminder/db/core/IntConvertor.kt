package com.onereminder.db.core

import androidx.room.TypeConverter
import java.util.Date

object IntConverter {

    @TypeConverter
    @JvmStatic
    fun toIntValue(value: Long?): Int {
        return value?.let { it.toInt() } ?: 0
    }

    @TypeConverter
    @JvmStatic
    fun toLongValue(value: Int?): Long {
        return value?.let { value.toLong() } ?: 0L
    }
}
