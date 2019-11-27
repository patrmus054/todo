package com.example.todo

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class TaskAdapter( val taskList: List<Task>, val listener: (Task) -> Unit):
        RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(task: Task, listener: (Task) -> Unit) = with(itemView){
            tvTitle.text = task.title
            tvDescription.text = task.description
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return TaskViewHolder(rootView)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList.get(position), listener)


        holder.itemView.setOnClickListener{
            val item = taskList[position]
            val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java)
            intent.putExtra(MainActivity.TASK_ID, item.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = taskList.size
}