package com.example.projectbluebook

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper2(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "worksheet_db"
        private const val TBL_FEAR = "tbl_fear"
        private const val ID = "id"
        private const val TITLE = "title"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblFear = ("CREATE TABLE " + TBL_FEAR + "("
                + ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT"
                + ")")
        db?.execSQL(createTblFear)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_FEAR")
        onCreate(db)
    }

    fun insertFear(f: Fear): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, f.id)
        contentValues.put(TITLE, f.title)

        val success = db.insert(TBL_FEAR, null, contentValues)
        db.close()
        return success
    }

    fun getAllFears(): ArrayList<Fear> {
        val fList: ArrayList<Fear> = ArrayList()
        //val selectQuery = "SELECT * FROM $TBL_FEAR"
        val selectQuery = "SELECT * FROM $TBL_FEAR"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var title: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val f = Fear(id = id, title = title)
                fList.add(f)
            } while (cursor.moveToNext())
        }
        return fList
    }

    fun deleteFearById(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_FEAR, "id=$id", null)
        db.close()
        return success
    }
}