package com.apm.arcface

import android.util.Log
import com.apm.arcface.db.AppDataBaseManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fi.iki.elonen.NanoHTTPD
import java.io.InputStream
import java.util.*

/**
 *  author : ciih
 *  date : 2019-08-21 15:33
 *  description :
 */
class AppServer(hostName: String?, port: Int) : NanoHTTPD(hostName, port) {

    companion object {
        const val TAG = "AppServer"
    }

    init {
        Log.d(TAG, "Start AppServer on Port $port")
    }

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    override fun serve(session: IHTTPSession): Response {
        Log.e(TAG, session.uri)
        return when (session.uri) {
            //Main
            "/", "/index.html" -> {
                val data: InputStream = App.contextGlobal.assets.open("index.html")
                newChunkedResponse(Response.Status.OK, MIME_HTML, data)
            }
            // data interfaces
            "/databaseList" -> {
                val end = Date()
                val list = AppDataBaseManager.appDataBase().getLogDao().getLogByDate(
                    Date(end.time - 1000 * 60 * 1), end
                )
                newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, gson.toJson(list))
            }
            //file interfaces
            else -> {
                val start = session.uri.indexOf('/') + 1
                val end = session.uri.length
                val route = session.uri.substring(start, end)
                val data: InputStream = App.contextGlobal.assets.open(route)
                val mimeType = when (route.substring(route.lastIndexOf("."))) {
                    ".css" -> "text/css"
                    ".html" -> MIME_HTML
                    else -> MIME_PLAINTEXT
                }
                newChunkedResponse(Response.Status.OK, mimeType, data)
            }
        }
    }

}