package com.example.projectbluebook

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class Worksheet (
    var id: Int = 0,
    var title: String = "",
    var answers: ArrayList<String> = arrayListOf<String>(""),
    var date: String = ""
) : Serializable