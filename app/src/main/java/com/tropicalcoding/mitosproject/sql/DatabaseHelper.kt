package com.tropicalcoding.mitosproject.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tropicalcoding.mitosproject.User
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Creacion de tablas
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")


    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        // Elimina la tabla usuario si existe
        db.execSQL(DROP_USER_TABLE)

        // Crea la tabla otra vez
        onCreate(db)

    }


    fun getAllUser(): List<User> {

        // Creamos una variable que guarde todas las columnas
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)

        // orden
        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = ArrayList<User>()

        val db = this.readableDatabase

        // Tabla Usuario
        val cursor = db.query(TABLE_USER, columns, null, null, null, null, sortOrder)
        if (cursor.moveToFirst()) {
            do {
                val user = User(id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)))

                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }


    //Funcion para añadir Usuario
    fun addUser(user: User) {
        val db = this.writableDatabase
        //Ingresamos los datos
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)

        // Guardamos los datos en la tabla Usuario
        db.insert(TABLE_USER, null, values)
        db.close()
    }

  //Actualizar Tabla User
    fun updateUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
      //Ingresamos los datos a cambiar
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)

        // Actualizamos tabla
        db.update(TABLE_USER, values, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()
    }

    //Eliminamos datos en Tabla Usuario
    fun deleteUser(user: User) {

        // usamos el writableDatabase para eliminar datos
        val db = this.writableDatabase

        db.delete(TABLE_USER, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()


    }

     // Funcion para verificar si existe el correo
    fun checkUser(email: String, trim: String): Boolean {

        // readable verifica si existe un dato en BD
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase


        val selection = "$COLUMN_USER_EMAIL = ?"


        val selectionArgs = arrayOf(email)



        val cursor = db.query(TABLE_USER,columns, selection, selectionArgs, null, null, null)

         //Creamos una variable para la verificacion
        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }


    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "DBUser.db"


        private val TABLE_USER = "user"
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"
    }

}