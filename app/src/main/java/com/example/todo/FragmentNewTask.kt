package com.example.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_new_task.*
import kotlinx.android.synthetic.main.fragment_new_task.view.*
import java.util.*

class FragmentNewTask : Fragment(){

    companion object {
        fun newInstacne() = FragmentNewTask()
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_new_task, container, false)

        view.bt_submit.setOnClickListener { view ->
            val day: Int = datepicker.getDayOfMonth()
            val month: Int = datepicker.getMonth()
            val year: Int = datepicker.getYear()

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            var task = Task(MainActivity.id++, et_task_title.text.toString(), et_task_description.text.toString(), calendar.time.toString())
            MainActivity.database.insertData(task)
            Log.v("dupa","dupa")
            //MainActivity.fabVisible()
            MainActivity.viewAdapter.notifyItemInserted(MainActivity.id)
            Log.v("ups", "ups")
            getActivity()?.getSupportFragmentManager()?.popBackStack()

//            MainActivity.viewAdapter = TaskAdapter(MainActivity.database.getAllTasks()){}
//
//            MainActivity.viewAdapter.notifyDataSetChanged()

        }

        return view
    }

}
