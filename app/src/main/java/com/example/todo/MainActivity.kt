package com.example.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_new_task.*
import java.util.*


class MainActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager


    companion object {
        val TASK_ID: String = "taskID"
        var id:Int=1
        lateinit var database: DatabaseManager
        lateinit var viewAdapter: TaskAdapter
        fun fabVisible(): Void = fabVisible()

    }
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DatabaseManager.context = this

        database = DatabaseManager.instance
        viewManager = LinearLayoutManager(this)

        viewAdapter = TaskAdapter(database.getAllTasks()){
            Toast.makeText(this, "${it.title} Clicked", Toast.LENGTH_SHORT).show()
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView

        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter


        fab.setOnClickListener{
            val fragment = FragmentNewTask.newInstacne()
            replaceFragment(fragment)
        }

        fab_refresh.setOnClickListener{
            finish()
            startActivity(getIntent())
            Toast.makeText(this, "refreshed", Toast.LENGTH_SHORT).show()
        }

        if(database.getAllTasks().size > 0)
            tvNotTask?.visibility = View.GONE
        else
            tvNotTask?.visibility = View.VISIBLE

    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()


    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    @SuppressLint("RestrictedApi")
    private fun fabVisible(){
        fab?.visibility = View.VISIBLE

        Log.v("da", "daa")
    }
}
