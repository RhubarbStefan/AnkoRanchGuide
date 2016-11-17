package com.example.stefan.criminalintent

import android.app.Fragment
import java.util.*

class CrimeActivity : SingleFragmentActivity(1001) {
    override fun createFragment(): CrimeFragment{
        val crimeId = intent?.getSerializableExtra(CrimeFragment.extra_crime_id) as UUID

        return CrimeFragment.newInstance(crimeId)
    }
}

