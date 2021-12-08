package com.example.todolistadvanced


data class TaskModel(

    var id: Long? = null,

    var isCompleted: Boolean? = null,

    var title: String? = null


) {

    constructor(isCompleted: Boolean? = null, title: String? = null) : this(
        0,
        isCompleted,
        title
    )

}


