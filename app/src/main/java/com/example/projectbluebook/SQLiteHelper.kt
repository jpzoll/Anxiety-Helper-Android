package com.example.projectbluebook

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

        companion object {
            private const val DATABASE_VERSION = 1
            private const val DATABASE_NAME = "worksheet_db"
            private const val TBL_WORKSHEET = "tbl_worksheet"
            private const val TBL_FEAR = "tbl_fear"
            private const val ID = "id"
            private const val TITLE = "title"
            private const val ANSWER_1 = "answer1"
            private const val ANSWER_2 = "answer2"
            private const val ANSWER_3 = "answer3"
            private const val ANSWER_4 = "answer4"
            private const val ANSWER_5 = "answer5"
            private const val ANSWER_6 = "answer6"
            private const val DATE = "date"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblWorksheet = ("CREATE TABLE " + TBL_WORKSHEET + "("
                + ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT,"
                + ANSWER_1 + " TEXT,"
                + ANSWER_2 + " TEXT,"
                + ANSWER_3 + " TEXT,"
                + ANSWER_4 + " TEXT,"
                + ANSWER_5 + " TEXT,"
                + ANSWER_6 + " TEXT,"
                + DATE + " TEXT"
                + ")")
        val createTblFear = ("CREATE TABLE " + TBL_FEAR + "("
                + ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT"
                + ")")
        db?.execSQL(createTblWorksheet)
        db?.execSQL(createTblFear)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_WORKSHEET")
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_FEAR")
        onCreate(db)
    }

    fun insertWorksheet(ws: Worksheet): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, ws.id)
        contentValues.put(TITLE, ws.title)
        contentValues.put(ANSWER_1, ws.answers.get(0))
        contentValues.put(ANSWER_2, ws.answers.get(1))
        contentValues.put(ANSWER_3, ws.answers.get(2))
        contentValues.put(ANSWER_4, ws.answers.get(3))
        contentValues.put(ANSWER_5, ws.answers.get(4))
        contentValues.put(ANSWER_6, ws.answers.get(5))
        contentValues.put(DATE, ws.date)

        val success = db.insert(TBL_WORKSHEET, null, contentValues)
        db.close()
        return success
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

    fun getAllWorksheets(): ArrayList<Worksheet> {
        val wsList: ArrayList<Worksheet> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_WORKSHEET"
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
        var answer: String
        var answers: ArrayList<String>
        var date: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                // Answers List
                answers = arrayListOf("", "", "", "", "", "")
                answers.set(0, cursor.getString(cursor.getColumnIndexOrThrow("answer1")))
                answers.set(1, cursor.getString(cursor.getColumnIndexOrThrow("answer2")))
                answers.set(2, cursor.getString(cursor.getColumnIndexOrThrow("answer3")))
                answers.set(3, cursor.getString(cursor.getColumnIndexOrThrow("answer4")))
                answers.set(4, cursor.getString(cursor.getColumnIndexOrThrow("answer5")))
                answers.set(5, cursor.getString(cursor.getColumnIndexOrThrow("answer6")))
                date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                val std = Worksheet(id = id, title = title, answers = answers, date = date)
                wsList.add(std)
            } while (cursor.moveToNext())
        }
        return wsList
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

    fun updateWorksheet(ws: Worksheet): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, ws.id)
        contentValues.put(TITLE, ws.title)
        contentValues.put(ANSWER_1, ws.answers.get(0))
        contentValues.put(ANSWER_2, ws.answers.get(1))
        contentValues.put(ANSWER_3, ws.answers.get(2))
        contentValues.put(ANSWER_4, ws.answers.get(3))
        contentValues.put(ANSWER_5, ws.answers.get(4))
        contentValues.put(ANSWER_6, ws.answers.get(5))
        contentValues.put(DATE, ws.date)

        val success = db.update(TBL_WORKSHEET, contentValues, "id=" + ws.id, null)
        db.close()
        return success
    }

    fun deleteWorksheetById(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_WORKSHEET, "id=$id", null)
        db.close()
        return success
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