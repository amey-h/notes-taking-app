package org.ameyapps.notes.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import org.ameyapps.notes.model.NoteInfo
import org.ameyapps.notes.utils.Log

class NotesDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val TABLE_NAME = "NotesTable"
        const val DATABASE_NAME = "NotesDb"
        const val COLUMN_NAME_TITLE = "Title"
        const val COLUMN_NAME_DESCP = "Description"
        const val COLUMN_NAME_DATE = "Date"
        const val COLUMN_ID = "Id"
        const val COLUMN_NAME_COLOR = "Color"
        const val DATABASE_VERSION = 1
        val TAG = "NotesApp " + NotesDbHelper::class.java.simpleName
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_NOTES_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME_TITLE + " TEXT,"
                + COLUMN_NAME_DESCP + " TEXT,"
                + COLUMN_NAME_DATE + " TEXT, "
                + COLUMN_NAME_COLOR + " INTEGER" + ")")
        db?.execSQL(CREATE_NOTES_TABLE)
        Log.d(TAG, "Table Created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
        Log.d(TAG, "onUpgrade called.")
    }

    fun addNote(noteInfo: NoteInfo) {
        Log.d(TAG, "addNote")
        val dbWriter = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, noteInfo.id)
        contentValues.put(COLUMN_NAME_TITLE, noteInfo.title)
        contentValues.put(COLUMN_NAME_DESCP, noteInfo.description)
        contentValues.put(COLUMN_NAME_DATE, noteInfo.date)
        contentValues.put(COLUMN_NAME_COLOR, noteInfo.color)
        val insValue = dbWriter.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "Note added to db: $insValue")
        dbWriter.close()
    }

    fun getAllNotes(): ArrayList<NoteInfo> {

        Log.d(TAG, "getAllNotes")
        val noteList = ArrayList<NoteInfo>()
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
                val noteInfo = NoteInfo()
                noteInfo.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                noteInfo.title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE))
                noteInfo.description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCP))
                noteInfo.date = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE))
                noteInfo.color = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_COLOR))
                noteList.add(noteInfo)

            } while (cursor.moveToNext())
        }
        Log.d(TAG, "fetched data from db: ${noteList.size}, ")
        db.close()
        return noteList
    }

    fun updateNote(noteInfo: NoteInfo) {
        Log.d(TAG, "update Note")
        val dbWriter = writableDatabase
        val contentValues = ContentValues()
        //contentValues.put(COLUMN_ID, noteInfo.id)
        contentValues.put(COLUMN_NAME_TITLE, noteInfo.title)
        contentValues.put(COLUMN_NAME_DESCP, noteInfo.description)
        contentValues.put(COLUMN_NAME_DATE, noteInfo.date)
        contentValues.put(COLUMN_NAME_COLOR, noteInfo.color)
        val selection = COLUMN_ID + " = ?"
        Log.d(TAG, "selection: $selection")
        val selectionArgs = arrayOf(noteInfo.id.toString())
        Log.d(TAG, "selectionArgs: ${selectionArgs}")
        val updateVal = dbWriter.update(TABLE_NAME, contentValues, selection, selectionArgs)
        Log.d(TAG, "Update Value: ${updateVal}")
        if (updateVal >= 1) {
            Log.d(TAG, "Note updated")
        } else {
            Log.w(TAG, "Note not updated")
        }
        dbWriter.close()
    }
}