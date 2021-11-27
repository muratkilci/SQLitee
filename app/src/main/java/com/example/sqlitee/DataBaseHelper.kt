package com.example.sqlitee

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val database_name = "Database"
val table_name = "Users"
val col_name = "NameSurname"
val col_age = "Age"
val col_id = "Id"

class DataBaseHelper(var context: Context) : SQLiteOpenHelper(
    context,
    database_name, null, 1
) {
    //Veritabanı oluştuğunda bir kez çalışır
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = " CREATE TABLE " + table_name + " ( " +
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col_name + " VARCHAR(256), " +
                col_age + " INTEGER) "
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    //Veri kaydetme
    fun insertData(user: User) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_name, user.namesurname)
        cv.put(col_age, user.age)
        var sonuc = db.insert(table_name, null, cv)
        if (sonuc == (-1).toLong()) {
            Toast.makeText(context, "Mistaken", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Successfully", Toast.LENGTH_LONG).show()
        }
    }

    //Verileri okuma
    @SuppressLint("Range")
    fun readData(): MutableList<User> {
        var list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        var sorgu = "Select * from " + table_name
        var sonuc = db.rawQuery(sorgu, null)
        if (sonuc.moveToFirst()) {
            do {
                var user = User()
                user.id = sonuc.getString(sonuc.getColumnIndex(col_id)).toInt()
                user.namesurname = sonuc.getString(sonuc.getColumnIndex(col_name))
                user.age = sonuc.getString(sonuc.getColumnIndex(col_age)).toInt()
                list.add(user)
            } while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()
        return list
    }

    //Verileri güncelleme
    @SuppressLint("Range")
    fun updateData() {
        val db = this.readableDatabase
        var sorgu = "Select * from $table_name"
        var sonuc = db.rawQuery(sorgu, null)
        if (sonuc.moveToFirst()) {
            do {
                var cv = ContentValues()
                cv.put(col_age, (sonuc.getInt(sonuc.getColumnIndex(col_age))) + 1)
                cv.put(
                    col_name,
                    (sonuc.getString(sonuc.getColumnIndex(col_name))) + " " + "Updated"
                )
                db.update(
                    table_name, cv, "$col_id=? AND $col_name=?",
                    arrayOf(
                        sonuc.getString(sonuc.getColumnIndex(col_id)),
                        sonuc.getString(sonuc.getColumnIndex(col_name))
                    )
                )
            } while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()

    }

    //verileri silme
    fun deleteData() {
        val db = this.writableDatabase
        db.delete(table_name, null, null)
        db.close()
    }
}