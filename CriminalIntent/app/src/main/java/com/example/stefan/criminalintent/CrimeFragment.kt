package com.example.stefan.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.util.*

class CrimeFragment: Fragment(), AnkoLogger {
    companion object{
        val extra_crime_id = "com.bignerdranch.android.criminalintent.crime_id"
        fun newInstance(crimeId: UUID): CrimeFragment{
            val args = Bundle()
            args.putSerializable(extra_crime_id, crimeId)
            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private var mCrime: Crime? = null
    private var mTitleField: EditText? = null
    private var mDateButton: Button? = null
    private var mSolvedCheckBox: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CrimeLab.get(activity)

        val crimeId: UUID = arguments.getSerializable(extra_crime_id) as UUID
        mCrime = CrimeLab.getCrime(crimeId)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        val ui = try {
            UI {
                verticalLayout {
                    textView {
                        text = getString(R.string.crime_title_label)
                    }
                    mTitleField = editText {
                        hintResource = R.string.crime_title_hint
                        setText (mCrime?.title ?: "")
                    }
                    textView {
                        text = getString(R.string.crime_details_label)
                    }

                    mDateButton = button {
                        text = mCrime?.date?.toString() ?: Date().toString()
                        enabled = false
                    }.lparams(width = matchParent) {
                        verticalMargin = dip(16)
                    }
                    mSolvedCheckBox = checkBox {
                        text = getString(R.string.crime_solved_label)
                        isChecked = mCrime?.solved ?: false
                        onCheckedChange { compoundButton, isChecked -> mCrime?.solved = isChecked }
                    }.lparams {
                        margin = dip(16)
                    }
                }
            }.view
        }
        catch (e: Exception){
            error("Could not create UI: $e")
            null
        }

        mTitleField?.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mCrime?.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        return ui
    }
}