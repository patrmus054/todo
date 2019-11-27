package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DatabaseManager private constructor() : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        private object HOLDER {
            val INSTANCE = DatabaseManager()
        }


    companion object {
        var DB_NAME = "tasks"
        var DB_VERSION = 4
        var context: Context? = null
        val instance: DatabaseManager by lazy { HOLDER.INSTANCE }
    }
    private lateinit var db: SQLiteDatabase
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS  tasks")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE tasks(id INTEGER  PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, date TEXT)"
        db?.execSQL(query)
    }


     fun insertData(task: Task) {
        db = writableDatabase
        val values = ContentValues().apply {
            put("title", task.title)
            put("description", task.description)
            put("date", task.createdAt)
        }
        db.insert("tasks", null, values)
    }

    fun getAllTasks(): MutableList<Task> {
        val columns = listOf("id", "title", "description", "date").toTypedArray()
        db = readableDatabase
        val cursor = db.query("tasks", columns, null, null, null, null, null)
        val tasks = mutableListOf<Task>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getInt(getColumnIndexOrThrow("id"))
                val title = getString(getColumnIndexOrThrow("title"))
                val description = getString(getColumnIndexOrThrow("description"))
                val createdAt = getString(getColumnIndexOrThrow("date"))
                tasks.add(Task(itemId, title, description, createdAt))
            }
        }
        cursor.close()
        return tasks
    }

    fun getTaskById(id: Int): Task {
        val columns = listOf("id", "title", "description", "date").toTypedArray()
        db = readableDatabase
        try{
            lateinit var task : Task
            val args = listOf<String>("$id").toTypedArray()
            val cursor = db.query(DB_NAME, columns, "id=?", args, null, null, null)
            if(id != -1 && cursor != null){
                cursor.moveToFirst()
                task = Task(
                    id,
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                )
            }else{
                cursor.close()
                return Task(id, "e", "e", "e")
            }
            cursor.close()
            return task
        }catch (e : TypeCastException){
            Log.v("db", "nicht gut")
        }
        return Task(id, "e", "e", "e")
    }

    fun updateTask(task: Task): Task {
        db = writableDatabase
        val args = listOf<String>(task.id.toString()).toTypedArray()
        val values = ContentValues().apply {
            put("title", task.title)
            put("description", task.description)
            put("date", task.createdAt)
        }
        db.update(DB_NAME, values, "id=?", args)
        val newTask = getTaskById(task.id)
        return newTask
    }

    fun deleteTask(id:Int){
        db = writableDatabase
        val args = listOf<String>("${id}")
        db.delete(DB_NAME, "id=?", args.toTypedArray())
        Log.v("dupa", id.toString())
    }
}