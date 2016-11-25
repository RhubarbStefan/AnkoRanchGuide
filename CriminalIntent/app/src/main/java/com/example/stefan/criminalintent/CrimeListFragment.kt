package com.example.stefan.criminalintent

import android.R.attr.layout_toLeftOf
import android.R.attr.textStyle
import android.app.Fragment
import android.app.ListActivity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.util.Log
import android.view.Gravity
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
        val c = listAdapter.getItem(position) as Crime
        Log.d(TAG, c.toString() + "was clicked")

        startActivity<CrimeActivity>(CrimeFragment.extra_crime_id to c.id)
    }

    override fun onResume() {
        super.onResume()
        (listAdapter as CrimeAdapter).notifyDataSetChanged()
    }

    internal open class CrimeAdapter(val ctx: Context, items: ArrayList<Crime>): ArrayAdapter<Crime>(ctx, 0, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val c = getItem(position)

            val view = ctx.relativeLayout{
                val box = checkBox{
                    enabled = false
                    isFocusable = false
                    gravity = Gravity.CENTER
                    padding = dip(4)
                    isChecked = c.solved
                }.lparams{
                    alignParentRight()
                }
                val titleView = textView(c.toString()) {
                    setPadding(dip(4),dip(4),dip(4),dip(4))
                }.lparams{
                    leftOf(box.id)
                }
                textView(c.date.toString()){
                    setPadding(dip(4), dip(20), dip(4), dip(4))
                }.lparams {
                    below(titleView.id)
                    leftOf(box.id)
                }

            }

            return convertView ?: view
        }
    }

}