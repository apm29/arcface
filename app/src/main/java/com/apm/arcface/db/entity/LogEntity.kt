package com.apm.arcface.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 *  author : ciih
 *  date : 2019-08-21 10:54
 *  description :
 */
@Entity(tableName = "t_app_log")
data class LogEntity(
   @PrimaryKey(autoGenerate = true) val id:Long,
   @ColumnInfo(name = "create_time") val  createTime:Date,
   @ColumnInfo(name = "log_content") val logContent:String
)