package com.deonico.footballapp_kade.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.deonico.footballapp_kade.model.EventTableConstant
import org.jetbrains.anko.db.*

class EventDBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DatabaseProperties.DATABASE_EVENT_NAME, null, 1) {
    companion object {
        private var instance: EventDBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): EventDBHelper {
            if (instance == null) {
                instance = EventDBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(EventTableConstant.TABLE_NAME, true,
                EventTableConstant.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EventTableConstant.ID_EVENT to TEXT,
                EventTableConstant.EVENT_DATE to TEXT,
                EventTableConstant.HOME_TEAM to TEXT,
                EventTableConstant.HOME_SCORE to TEXT,
                EventTableConstant.AWAY_TEAM to TEXT,
                EventTableConstant.AWAY_SCORE to TEXT,
                EventTableConstant.HOME_ID to TEXT,
                EventTableConstant.AWAY_ID to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(EventTableConstant.TABLE_NAME, true)
    }

    val Context.eventDB: EventDBHelper
        get() = getInstance(applicationContext)

}