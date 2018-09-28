package com.deonico.footballapp_kade.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.deonico.footballapp_kade.model.TeamTableConstant
import org.jetbrains.anko.db.*

class TeamDBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DatabaseProperties.DATABASE_TEAM_NAME, null, 1) {
    companion object {
        private var instance: TeamDBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): TeamDBHelper {
            if (instance == null) {
                instance = TeamDBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(TeamTableConstant.TABLE_NAME, true,
                TeamTableConstant.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamTableConstant.TEAM_ID to TEXT,
                TeamTableConstant.TEAM_NAME to TEXT,
                TeamTableConstant.TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(TeamTableConstant.TABLE_NAME, true)
    }

    val Context.teamDB: TeamDBHelper
        get() = getInstance(applicationContext)
}