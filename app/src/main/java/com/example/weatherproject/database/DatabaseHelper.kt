package com.example.weatherproject.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

val DATABASE_NAME = "WeatherProject.db"
val DATABASE_VERSION = 1

val CITY_TABLE = "cities"
val CITY_NAME = "cityname"

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val createCityTable =
            "CREATE TABLE $CITY_TABLE(" +
                    "$CITY_NAME TEXT NOT NULL PRIMARY KEY" +
                    ")"
        db?.execSQL(createCityTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val deleteCityTable = "DROP TABLE IF EXISTS $CITY_TABLE"
        db?.execSQL(deleteCityTable)
    }

    fun addCityData(city: String){
        val db = this.writableDatabase
        val content = ContentValues()
        content.put(CITY_NAME,city)
        val result = db.insert(CITY_TABLE,null,content)
        if(result == (-1).toLong()){
            Log.d("Database:", "Could not add data!!")
        }else{
            Log.d("Database:", "Data added successfully!!")
        }
        db.close()
    }
    @SuppressLint("Range")
    fun getCities(): ArrayList<String> {
        val cities = ArrayList<String>()
        val selectQuery = "SELECT * FROM $CITY_TABLE"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        cursor = db.rawQuery(selectQuery,null)
        var city: String
        if (cursor != null) {
            if(cursor.moveToFirst()){
                do{
                    city = cursor.getString(cursor.getColumnIndex(CITY_NAME))
                    cities.add(city)
                }while (cursor.moveToNext())
            }
        }
        return cities
    }

    fun countTableRow():Int{
        val db = this.readableDatabase
        val count = DatabaseUtils.queryNumEntries(db, CITY_TABLE)
        db.close()
        return count.toInt()
    }
}