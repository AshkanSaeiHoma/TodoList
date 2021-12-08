package com.example.todolistadvanced

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DialogFragment(val newTaskAdd: NewTaskAdd) : DialogFragment() {

    @SuppressLint("InflateParams", "UseRequireInsteadOfGet")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val dialog = AlertDialog.Builder(context!!)

        val view = LayoutInflater.from(context!!)
            .inflate(R.layout.dialog_task, null, false)

        val edittext = view.findViewById<TextInputEditText>(R.id.EDIT_TEXT_MAIN)
        val parentedittext = view.findViewById<TextInputLayout>(R.id.PARENT_EDIT_TEXT)
        val btn = view.findViewById<MaterialButton>(R.id.BUTTON_MAIN)

        btn.setOnClickListener {

            if (edittext.text!!.isNotEmpty()) {

                val task = TaskModel(isCompleted = false, edittext.text.toString())

                newTaskAdd.NewAdd(task)


                onStop()


            } else {

                parentedittext.setError("عنوان نباید خالی باشد")

                /// TODO: 12/6/2021 Complete Here!! 

            }


        }

        dialog.setView(view)

        return dialog.create()


    }

    interface NewTaskAdd {

        fun NewAdd(task: TaskModel)

    }

}