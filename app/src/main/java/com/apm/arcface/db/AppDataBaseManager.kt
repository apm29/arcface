package com.apm.arcface.db

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.apm.arcface.db.dao.LogDao
import java.lang.IllegalStateException

/**
 *  author : ciih
 *  date : 2019-08-21 11:36
 *  description :
 */
object AppDataBaseManager {

    private lateinit var appDataBaseInstance: AppDataBase

    fun appDataBase(): AppDataBase {
        checkInstance("initDataBase is Not CALLED before call appDataBase")
        return appDataBaseInstance
    }

    private fun checkInstance(message: String) {
        if (!::appDataBaseInstance.isInitialized) {
            throw IllegalStateException(message)
        }
    }

    fun initDataBase(context: Context) {
        appDataBaseInstance = Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            AppDataBase.DB_APP_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        Log.d("App DataBase","Init Room DataBase Success")
    }

    fun logDao(): LogDao {
        checkInstance("initDataBase is Not CALLED before call logDao")
        return appDataBaseInstance.getLogDao()
    }

}