package com.example.stefan.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import java.util.*

class CrimePagerActivity : FragmentActivity(){
    private var mViewPager: ViewPager? = null
    private var mCrimes: ArrayList<Crime>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewPager = ViewPager(this)
        mViewPager?.id = R.id.viewPager
        setContentView(mViewPager)

        CrimeLab.get(this)
        mCrimes = CrimeLab.crimes

        val fm : FragmentManager= supportFragmentManager
        mViewPager?.adapter = object : FragmentStatePagerAdapter(fm){
            override fun  getCount(): Int {
                return mCrimes?.size ?: 0
            }
            override fun getItem(pos: Int): Fragment{
                val crime = mCrimes?.get(pos)
                return CrimeFragment.newInstance(crime?.id ?: UUID.randomUUID())
            }
        }
    }
}