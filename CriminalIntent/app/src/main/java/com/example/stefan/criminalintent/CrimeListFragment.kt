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
        //CrimeLab.get(activity)

        //activity.setTitle(R.string.crimes_title)

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


abstract class ListItemAdapter(ctx: Context, val items: List<ListItem>) : ArrayAdapter<ListItem>(ctx, 0, items) {
    // Reusable context allows adding multiple views, so it can be used in adapters without overhead
    private val ankoContext = AnkoContext.createReusable(ctx, this)

    protected abstract val listItemClasses: List<Class<out ListItem>>

    /*private val types: Map<Class<out ListItem>, Int> by lazy {
        listItemClasses.withIndex().fold(hashMapOf<Class<out ListItem>, Int>()) {
            map, t -> map.put(t.value, t.index); map
        }
    }*/

    //override fun getViewTypeCount(): Int = 1

    //override fun getItemViewType(position: Int) = types[getItem(position)?.javaClass as Class<out ListItem>] ?: 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val item = getItem(position)
        if (item != null) {
            val view = convertView ?: item.createView(ankoContext)
            item.apply(view)
            return view
        } else return convertView
    }
}

interface ListItem : AnkoComponent<ListItemAdapter> {
    fun apply(convertView: View)
}

internal open class TextListItem(val title: String, val subtitle: String, val checked: Boolean) : ListItem {
    protected inline fun createTextView(ui: AnkoContext<ListItemAdapter>, init: TextView.() -> Unit) = ui.apply {
        textView {
            text = title // default text (for the preview)

            init()
        }
    }.view

    override fun createView(ui: AnkoContext<ListItemAdapter>) =ui.apply {
        relativeLayout{
            verticalLayout {
                textView {
                    text = title
                    textSize = 20f
                }
                textView {
                    text = subtitle
                    textSize = 15f
                }
            }
            checkBox("", checked){
                isFocusable = false
            }
        }
    }.view

    private fun getHolder(convertView: View): Holder {
        return (convertView.tag as? Holder) ?: Holder(convertView as TextView).apply {
            convertView.tag = this
        }
    }

    override fun apply(convertView: View) {
        val h = getHolder(convertView)
        h.textView.text = title
    }

    internal class Holder(val textView: TextView)
}


internal class CrimeItem(crime: Crime): TextListItem(crime.title, crime.date.toString(), crime.solved)