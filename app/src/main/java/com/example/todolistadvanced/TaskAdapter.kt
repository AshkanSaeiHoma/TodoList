package com.example.todolistadvanced

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import java.sql.SQLPermission

class TaskAdapter(val context: Context) :
    RecyclerView.Adapter<TaskAdapter.CustomViewHolder>() {
    //Instance Objects
    var list2 = ArrayList<TaskModel>()
    val sqlite = SQL(context)

    inner class CustomViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val checkbox = itemview.findViewById<CheckBox>(R.id.CheckBox_Main)

        val deleteimage = itemview.findViewById<ImageView>(R.id.Delete_Main)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        return CustomViewHolder(
            LayoutInflater.from(context).inflate(R.layout.itemlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.checkbox.isChecked = list2[position].isCompleted!!
        holder.checkbox.text = list2[position].title


        holder.deleteimage.setOnClickListener {

            DeleteItemFromList(list2[position], position)

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(taskModel: TaskModel) {

        list2.add(0, taskModel)
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(arrayList: ArrayList<TaskModel>) {


        this.list2.addAll(arrayList)
        notifyDataSetChanged()

    }

    fun DeleteItemFromList(Task: TaskModel, position: Int) {

        sqlite.DeleteTask(Task)
        list2.remove(Task)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list2.size)

    }


    @SuppressLint("NotifyDataSetChanged")
    fun DeleteAllItems() {

        val sql = SQL(context)
        sql.DeleteAllTasks()
        list2.clear()
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(list:ArrayList<TaskModel>) {

        this.list2 = list
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int = list2.size


}