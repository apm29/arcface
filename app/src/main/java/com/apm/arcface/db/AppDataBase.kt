package com.apm.arcface.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apm.arcface.db.converter.Converter
import com.apm.arcface.db.dao.LogDao
import com.apm.arcface.db.entity.LogEntity

/**
 *  author : ciih
 *  date : 2019-08-21 10:53
 *  description :
 */
@Database(
    entities = [LogEntity::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(
    value = [Converter::class]
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val DB_APP_NAME = "app_db"
    }

    abstract fun getLogDao(): LogDao
}