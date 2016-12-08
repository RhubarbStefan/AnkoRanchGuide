package com.bignerdranch.android.hellomoon

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.frameLayout

class HelloMoonActivity : FragmentActivity(), AnkoLogger{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout { }
        if (null == savedInstanceState) {
            supportFragmentManager.beginTransaction().replace(android.R.id.content, HelloMoonFragment()).commit()
        }
    }
}

