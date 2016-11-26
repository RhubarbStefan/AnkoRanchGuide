package com.example.stefan.criminalintent

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.util.*

class DatePickerFragment : DialogFragment() {
    companion object{
        val EXTRA_DATE = "com.bignerdranch.android.criminalintent.date"
        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_DATE, date)

            val fragment = DatePickerFragment()
            fragment.arguments = args

            return fragment
        }
    }

    var mDate: Date = Date()

    private fun sendResult(resultCode: Int): Unit {
        val i = Intent()
        i.putExtra(EXTRA_DATE, mDate)
        targetFragment?.onActivityResult(targetRequestCode, resultCode, i)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDate = arguments.getSerializable(EXTRA_DATE) as Date

        //Create a Calendar to get the year, mont, and day
        val calendar = Calendar.getInstance()
        calendar.time = mDate
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val v = DatePicker(activity)

        v.init(year, month, day, { view, year, month, day ->
            mDate = GregorianCalendar(year, month, day).time
            arguments.putSerializable(EXTRA_DATE, mDate)
        })


        return AlertDialog
                .Builder(activity)
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, {
                    dialog, which ->  sendResult(Activity.RESULT_OK)}
                )
                .create()
    }
}