package com.example.todolistadvanced

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), DialogFragment.NewTaskAdd {
    lateinit var sqllite: SQL
    lateinit var DATABASE: SQLiteDatabase
    var taskAdapter: TaskAdapter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sqllite = SQL(this)

        DATABASE = sqllite.writableDatabase

        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView_Main)

        val deleteAll = findViewById<ImageView>(R.id.IMG_DELETE_ALL_B)

        val edtsearch = findViewById<EditText>(R.id.EDIT_TEXT_SEARCH)

        edtsearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (edtsearch.text.isNotEmpty()) {

                    val list = sqllite.SearchInTasks(edtsearch.text.toString())
                          taskAdapter?.setTasks(list)
                } else {

                  val list =  sqllite.GetTasks()
                    taskAdapter?.setTasks(list)
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }


        })


        val list = sqllite.GetTasks()

        taskAdapter = TaskAdapter(this)

        recyclerView.apply {

            this.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)

            //Eno Ok Kon NewAdd Shode Ro Begir va add kon to arraylist va bede be Adapter
            this.adapter = taskAdapter
        }
        //fab

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        taskAdapter!!.addItems(list)


        fab.setOnClickListener {

            val dialog = DialogFragment(this)

            dialog.show(supportFragmentManager, "test")

        }

        deleteAll.setOnClickListener {


            taskAdapter!!.DeleteAllItems()


        }


        //todo create method in taskadapter for delete all tasks
    }

    override fun NewAdd(task: TaskModel) {

        sqllite.AddTask(task)
        taskAdapter!!.addItem(task)

    }

    override fun onDestroy() {
        super.onDestroy()

        sqllite.DeleteAllTasks()
    }

}