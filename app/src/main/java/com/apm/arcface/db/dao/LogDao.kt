package com.apm.arcface.db.dao

import androidx.room.*
import com.apm.arcface.db.entity.LogEntity
import java.util.*

/**
 *  author : ciih
 *  date : 2019-08-21 10:58
 *  description :
 */
@Dao
abstract class LogDao {

    @Query("DELETE FROM t_app_log")
    abstract fun deleteAllLogs()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addLog(log: LogEntity): Long


    @Query("SELECT * FROM t_app_log WHERE create_time < :end AND create_time > :start")
    abstract fun getLogByDate(start: Date, end: Date):List<LogEntity>
}