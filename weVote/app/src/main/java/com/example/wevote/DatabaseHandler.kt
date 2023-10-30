package com.example.wevote

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MyDatabase"
        private const val TABLE_NAME = "MyTable"

        private const val KEY_ID = "id"
        private const val KEY_SUBJECT = "subject"
        private const val KEY_OPT1_NAME = "opt1name"
        private const val KEY_OPT1_COUNT = "opt1count"
        private const val KEY_OPT2_NAME = "opt2name"
        private const val KEY_OPT2_COUNT = "opt2count"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, $KEY_SUBJECT TEXT, $KEY_OPT1_NAME TEXT, $KEY_OPT1_COUNT INTEGER, $KEY_OPT2_NAME TEXT, $KEY_OPT2_COUNT INTEGER)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addData(data: DataModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ID, data.id)
        values.put(KEY_SUBJECT, data.subject)
        values.put(KEY_OPT1_NAME, data.opt1name)
        values.put(KEY_OPT1_COUNT, data.opt1count)
        values.put(KEY_OPT2_NAME, data.opt2name)
        values.put(KEY_OPT2_COUNT, data.opt2count)
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllData(): ArrayList<DataModel> {
        val dataList = ArrayList<DataModel>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val data = DataModel(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_SUBJECT)),
                    cursor.getString(cursor.getColumnIndex(KEY_OPT1_NAME)),
                    cursor.getInt(cursor.getColumnIndex(KEY_OPT1_COUNT)),
                    cursor.getString(cursor.getColumnIndex(KEY_OPT2_NAME)),
                    cursor.getInt(cursor.getColumnIndex(KEY_OPT2_COUNT))
                )
                dataList.add(data)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return dataList
    }

    fun getCampaignDetailsById(campaignId: Int): DataModel? {
        val db = this.readableDatabase
        val selection = "$KEY_ID = ?"
        val selectionArgs = arrayOf(campaignId.toString())
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            val data = DataModel(
                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(KEY_OPT1_NAME)),
                cursor.getInt(cursor.getColumnIndex(KEY_OPT1_COUNT)),
                cursor.getString(cursor.getColumnIndex(KEY_OPT2_NAME)),
                cursor.getInt(cursor.getColumnIndex(KEY_OPT2_COUNT))
            )
            cursor.close()
            return data
        }
        cursor.close()
        return null
    }

    fun updateCampaignDetails(campaignId: Int, updatedCampaign: DataModel?) {
        val db = this.writableDatabase
        val values = ContentValues()
        if (updatedCampaign != null) {
            values.put(KEY_OPT1_COUNT, updatedCampaign.opt1count)
            values.put(KEY_OPT2_COUNT, updatedCampaign.opt2count)
        }
        val whereClause = "$KEY_ID = ?"
        val whereArgs = arrayOf(campaignId.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }


}
