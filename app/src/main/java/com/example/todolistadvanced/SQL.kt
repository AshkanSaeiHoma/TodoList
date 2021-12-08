package com.example.todolistadvanced

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.text.CaseMap

class SQL(context: Context) : SQLiteOpenHelper(context, "ASHKANDB", null, 1) {

    companion object {

        val TABLE_NAME = "app_db"


        val ID = "id"


        val TITLE = "title"


        val ISCOMPLETE = "iscomplete"


    }

    override fun onCreate(p0: SQLiteDatabase) {


        p0.execSQL(

            "CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY AUTOINCREMENT,$ISCOMPLETE BOOLEAN,$TITLE VARCHAR)"

        )

        // we are calling sqlite
        // method for executing our query


    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {


    }

    fun AddTask(task: TaskModel): Long {

        val db = writableDatabase
        val contectvalues = ContentValues()
        contectvalues.put(TITLE, task.title)
        contectvalues.put(ISCOMPLETE, task.isCompleted)
        val long: Long = db.insert(TABLE_NAME, null, contectvalues)
        db.close()
        return long

    }

    fun GetTasks(): ArrayList<TaskModel> {

        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val list = ArrayList<TaskModel>()

        if (cursor.moveToFirst()) {

            do {
                val task = TaskModel()
                task.id = cursor.getLong(0)
                task.isCompleted = cursor.getInt(1) == 1
                task.title = cursor.getString(2)
                list.add(task)

            } while (cursor.moveToNext())

        }

        cursor.close()
        db.close()

        return list
    }

    fun SearchInTasks(query: String): ArrayList<TaskModel> {

        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $TITLE LIKE '%$query%'", null)

        val list = ArrayList<TaskModel>()

        if (cursor.moveToFirst()) {

            do {
                val task = TaskModel()
                task.id = cursor.getLong(0)
                task.isCompleted = cursor.getInt(1) == 1
                task.title = cursor.getString(2)
                list.add(task)

            } while (cursor.moveToNext())

        }
        db.close()
        return list

    }

    fun DeleteTask(task: TaskModel): Int {

        val db = writableDatabase
        val resualt = db.delete(TABLE_NAME, "id = ?", arrayOf(task.id.toString()))
        db.close()
        return resualt
    }

    fun DeleteAllTasks() {


        val db = writableDatabase

        db.execSQL(

            "DELETE FROM  $TABLE_NAME"

        )

        db.close()

    }

    fun UpdateTask(task: TaskModel): Int {


        val db = writableDatabase
        val contectvalues = ContentValues()
        contectvalues.put("title", task.title)
        contectvalues.put("isCompleted", task.isCompleted)
        val up = db.update(TABLE_NAME, contectvalues, "id = ?", arrayOf(task.id.toString()))
        db.close()
        return up


    }
}