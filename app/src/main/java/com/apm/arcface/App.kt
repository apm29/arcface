package com.apm.arcface

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.apm.arcface.db.AppDataBaseManager
import com.facebook.stetho.InspectorModulesProvider
import com.facebook.stetho.Stetho

/**
 *  author : ciih
 *  date : 2019-08-21 10:21
 *  description :
 */
class App : Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        lateinit var contextGlobal: Context
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector {
                    Stetho.DefaultInspectorModulesBuilder(this)
                        .finish()
                }
                .build()
        )
        contextGlobal = this
        AppDataBaseManager.initDataBase(this)
        AppServer(null,8090).start()
    }
}