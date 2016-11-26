package com.example.stefan.criminalintent

import android.content.Context
import java.util.*

/**
 * Created by stefan on 30.04.16.
 */

object CrimeLab {
    var appContext: Context? = null
    val crimes: ArrayList<Crime> = ArrayList((1..100).map{ Crime("Crime# " + it, it % 2 == 0 ) })
    fun get(c: Context){
        appContext = c.applicationContext
    }

    fun getCrime(id: UUID): Crime? = crimes.find{it.id == id}

}