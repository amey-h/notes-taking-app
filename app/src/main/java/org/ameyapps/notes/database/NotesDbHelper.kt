package org.ameyapps.notes.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import org.ameyapps.notes.model.NoteInfo

class NotesDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val TABLE_NAME = "NotesTable"
        const val DATABASE_NAME = "NotesDb"
        const val COLUMN_NAME_TITLE = "Title"
        const val COLUMN_NAME_DESCP = "Description"
        const val COLUMN_NAME_DATE = "Date"
        const val COLUMN_ID = "Id"
        const val DATABASE_VERSION = 1
        val TAG = NotesDbHelper::class.java.simpleName
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_NOTES_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME_TITLE + " TEXT,"
                + COLUMN_NAME_DESCP + " TEXT,"
                + COLUMN_NAME_DATE + " TEXT" + ")")
        db?.execSQL(CREATE_NOTES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addNote(noteInfo: NoteInfo) {
        val dbWriter = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, noteInfo.id)
        contentValues.put(COLUMN_NAME_TITLE, noteInfo.title)
        contentValues.put(COLUMN_NAME_DESCP, noteInfo.description)
        contentValues.put(COLUMN_NAME_DATE, noteInfo.date)
        dbWriter.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "Note added to db.")
        dbWriter.close()
    }

    fun getNote(): NoteInfo {

        val noteInfo = NoteInfo()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        if (cursor!!.moveToFirst()) {
            do {
                noteInfo.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                noteInfo.title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE))
                noteInfo.description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCP))
                noteInfo.date = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE))
            } while (cursor.moveToNext())
        }
        Log.d(TAG, "fetch data from db")
        return noteInfo
    }
}