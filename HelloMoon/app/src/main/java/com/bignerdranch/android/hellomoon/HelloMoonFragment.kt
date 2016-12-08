package com.bignerdranch.android.hellomoon

import android.R.attr.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class HelloMoonFragment : Fragment() {
    private var mPLayButton: Button? = null
    private var mStopButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = activity.tableLayout {
                imageView(R.drawable.armstrong_on_moon).lparams {
                    contentDescription = getString(R.string.hellomoon_description)
                    width = matchParent
                    height = matchParent
                    weight = 1f
                }
            tableRow{
                mPLayButton = button(R.string.hellomoon_play)
                mStopButton = button(R.string.hellomoon_stop)
            }.lparams {
                weight = 0f
                gravity = bottom or centerX
            }

        }


        return v
    }
}