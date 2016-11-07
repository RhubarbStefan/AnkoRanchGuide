package com.example.stefan.criminalintent

import android.content.Context
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import org.jetbrains.anko.*

class CrimeListFragment: ListFragment(){
    companion object{
        private val TAG = "CrimeListFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        val crimes = CrimeLab.crimes
        super.onCreate(savedInstanceState)
        getActivity().setTitle(R.string.crimes_title)
        CrimeLab.get(getActivity())

        val adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, crimes)
        listAdapter = adapter

    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        val c = getListAdapter().getItem(position)
        Log.e(TAG, c.toString() + "was clicked")
    }

}

abstract class ListItemAdapter(ctx: Context, items: List<ListItem>) : ArrayAdapter<ListItem>(ctx, 0, items) {
    // Reusable context allows adding multiple views, so it can be used in adapters without overhead
    private val ankoContext = AnkoContext.createReusable(ctx, this)

    protected abstract val listItemClasses: List<Class<out ListItem>>

    private val types: Map<Class<out ListItem>, Int> by lazy {
        listItemClasses.withIndex().fold(hashMapOf<Class<out ListItem>, Int>()) {
            map, t -> map.put(t.value, t.index); map
        }
    }

    override fun getViewTypeCount(): Int = types.size
    override fun getItemViewType(position: Int) = types[getItem(position)?.javaClass as Class<out ListItem>] ?: 0

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

internal class CrimeAdapter(ctx: Context, items: List<ListItem>): ListItemAdapter(ctx, items){
    override val listItemClasses = listOf(CrimeItem::class.java)
}

internal class CrimeItem(crime: Crime): TextListItem(crime.title, crime.date.toString(), crime.solved)