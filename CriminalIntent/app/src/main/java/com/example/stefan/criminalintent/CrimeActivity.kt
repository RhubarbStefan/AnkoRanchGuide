/* Not needed right now*/

package com.example.stefan.criminalintent

import android.app.Fragment
import java.util.*

class CrimeActivity : SingleFragmentActivity(1001) {
    override fun createFragment(): CrimeFragment{
        val crimeId = intent?.getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID) as UUID

        return CrimeFragment.newInstance(crimeId)
    }
}

