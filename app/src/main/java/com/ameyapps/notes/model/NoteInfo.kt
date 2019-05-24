package com.ameyapps.notes.model


import java.io.Serializable

class NoteInfo() : Serializable {

    var id: Int = 1
    var title: String? = "Title Text"
    var description: String? =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt"
    var date: String? = "01/01/2018"
    var color: Int = 1

}