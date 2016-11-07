package com.example.stefan.criminalintent

import java.util.*

/**
 * Created by stefan on 12.04.16.
 */

class Crime(var title: String = "Crime", var solved:Boolean = false){
    val id: UUID = UUID.randomUUID()
    val date = Date()
    override fun toString() = title
}