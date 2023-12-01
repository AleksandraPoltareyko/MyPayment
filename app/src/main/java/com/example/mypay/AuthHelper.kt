package com.example.mypay

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AuthHelper(context:Context): SQLiteOpenHelper(context, "auth.db", null, 1) {
    companion object{
        private const val COLUMN_ID="_id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_PASSWORD= "password"
        const val TABLE_AUTH = "auth"

        private const val AUTH_CREATE = ("create table "
                + TABLE_AUTH + "("
                + COLUMN_ID
                + " integer primary key autoincrement, "
                + COLUMN_LOGIN
                + " text not null, "
                + COLUMN_PASSWORD
                + " real not null "
                + ");")


    }
    override fun onCreate(db: SQLiteDatabase) {
       db.execSQL(AUTH_CREATE)
        db.execSQL("insert into auth(login, password) values('demo','12345')")
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_AUTH")
        onCreate(db)
    }

    fun getUser(login: String, password: String):Boolean{
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM $TABLE_AUTH WHERE $COLUMN_LOGIN = '$login' AND $COLUMN_PASSWORD = '$password'", null)
        return result.moveToFirst()
    }
}