package com.apm.arcface

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import com.apm.arcface.db.AppDataBaseManager
import com.apm.arcface.db.entity.LogEntity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.DataOutputStream
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        thread {
            val process = Runtime.getRuntime().exec("logcat -v time\n")
            val bufferedReader = process.inputStream.bufferedReader()
            val bufferedWriter = DataOutputStream(process.outputStream)
            val errorBufferedReader = process.errorStream.bufferedReader()
            var readLine = bufferedReader.readLine()
            thread {
                while (readLine != null) {
                    addLog(readLine)
                    readLine = bufferedReader.readLine()
                }
            }
            var errorLine = errorBufferedReader.readLine()
            thread {
                while (errorLine != null) {
                    addLog(errorLine)
                    errorLine = bufferedReader.readLine()
                }
            }
        }

        val list = AppDataBaseManager.logDao().getLogByDate(Date(1566367412106), Date(1566367412206))
        list.forEach {
            println("log = $it")
        }
    }

    private fun addLog(readLine: String) {
        runOnUiThread {
            tv_log.append("$readLine\n")
            layout_log.fullScroll(View.FOCUS_DOWN)
        }
        AppDataBaseManager.logDao().addLog(LogEntity(
            id = 0,
            createTime = Date(),
            logContent = readLine
        ))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
