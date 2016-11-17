package com.example.stefan.criminalintent

import android.app.Fragment
import android.app.ListActivity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import org.jetbrains.anko.*
import java.util.*


class CrimeListFragment: ListActivity(){
    companion object{
        private val TAG = "CrimeListFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val crimes = ArrayList(CrimeLab.crimes)

        val adapter = CrimeAdapter(ctx, crimes)
        listAdapter = adapter

    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        val c = listAdapter.getItem(position)
        Log.e(TAG, c.toString() + "was clicked")
    }
    internal open class CrimeAdapter(val ctx: Context, items: ArrayList<Crime>): ArrayAdapter<Crime>(ctx, 0, items) {

        override fun getView(position: Int, oldConvertView: View?, parent: ViewGroup?): View {
            val convertView = oldConvertView ?: ctx.layoutInflater.inflate(R.layout.list_item_crime, null)

            val c = getItem(position)
            val titleTextView = convertView.findViewById(R.id.crime_list_item_titleTextView) as TextView
            titleTextView.text = c.title
            val dateTextView = convertView.findViewById(R.id.crime_list_item_dateTextView) as TextView
            dateTextView.text = c.date.toString()
            val solvedCheckBox: CheckBox = convertView.findViewById(R.id.crime_list_item_solvedCheckBox) as CheckBox
            solvedCheckBox.isChecked = c.solved

            return convertView
        }
    }

}