package com.example.stefan.criminalintent

import android.app.Activity
import android.content.Intent
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
        val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"
        private val DIALOG_DATE = "date"
        private val REQUEST_DATE = 0
        fun newInstance(crimeId: UUID): CrimeFragment{
            val args = Bundle()
            args.putSerializable(EXTRA_CRIME_ID, crimeId)
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

        val crimeId: UUID = arguments.getSerializable(EXTRA_CRIME_ID) as UUID
        mCrime = CrimeLab.getCrime(crimeId)
        error { "I received $crimeId, this results in $mCrime" }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        if(requestCode == REQUEST_DATE){
            val date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            mCrime?.date = date
            mDateButton?.text = mCrime?.date.toString()
        }
        super.onActivityResult(requestCode, resultCode, data)
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
                        enabled = true
                        onClick {
                            val fm = activity.supportFragmentManager
                            val dialog = DatePickerFragment.newInstance(mCrime?.date ?: Date())
                            dialog.setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                            dialog.show(fm, DIALOG_DATE)
                        }
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