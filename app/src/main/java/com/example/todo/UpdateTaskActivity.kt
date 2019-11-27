package com.example.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update_task.*
import kotlinx.android.synthetic.main.fragment_new_task.*
import kotlinx.android.synthetic.main.fragment_new_task.view.*
import java.util.*

class UpdateTaskActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_task)

        DatabaseManager.context = this
        val taskId = intent.getIntExtra(MainActivity.TASK_ID, -1)
        val oldTask = DatabaseManager.instance.getTaskById(taskId)

        Log.v("dupa", oldTask?.title)
        activity_et_task_title.setText(oldTask?.title)
        activity_et_task_description.setText(oldTask?.description)

        bt_update.setOnClickListener { view ->


            val day: Int = activity_datepicker.getDayOfMonth()
            val month: Int = activity_datepicker.getMonth()
            val year: Int = activity_datepicker.getYear()

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            var task = Task(taskId, activity_et_task_title.text.toString(), activity_et_task_description.text.toString(), calendar.time.toString())
            MainActivity.database.updateTask(task)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        bt_delete.setOnClickListener{
            MainActivity.database.deleteTask(taskId)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}