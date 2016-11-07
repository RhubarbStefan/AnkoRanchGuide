package com.example.stefan.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import org.jetbrains.anko.frameLayout

/**
 * Created by stefan on 30.04.16.
 */


abstract class SingleFragmentActivity(val rootId: Int= 1001): FragmentActivity(){
    protected abstract fun createFragment(): Fragment

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val fragment = frameLayout()
        //val rootId: Int = 1001
        //val frag: Fragment = com.example.stefan.criminalintent.CrimeFragment()

        frameLayout{id = rootId }

        if (null == savedInstanceState){
            supportFragmentManager.beginTransaction()
            .add(rootId, createFragment())
            .commit()
        }
    }
}