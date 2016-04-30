package com.example.stefan.criminalintent

import java.util.*

/**
 * Created by stefan on 12.04.16.
 */

class Crime(var title: String = "Crime"){
    val id = UUID.randomUUID()
    var date = Date()
    var solved:Boolean = false
}