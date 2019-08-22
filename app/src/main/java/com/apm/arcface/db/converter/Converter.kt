package com.apm.arcface.db.converter

import androidx.room.TypeConverter
import java.util.*


/**
 *  author : ciih
 *  date : 2019-08-21 11:15
 *  description :
 */
class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (date?.time)
    }

}