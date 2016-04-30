package com.example.stefan.criminalintent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.frameLayout
import CrimeFragment

class CrimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val fragment = frameLayout()
        val rootId: Int = 1001
        //val frag: Fragment = CrimeFragment()

        frameLayout{id = rootId }

        if (null == savedInstanceState){
            supportFragmentManager.beginTransaction()
            .add(rootId, CrimeFragment())
            .commit()
        }
    }
}

