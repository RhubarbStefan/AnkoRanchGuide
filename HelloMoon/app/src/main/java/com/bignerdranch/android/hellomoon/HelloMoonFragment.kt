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

class HelloMoonFragment() : Fragment(), AnkoLogger {
    private val mPlayer = AudioPlayer()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Apparently, we need to put this in some UI, otherwise container is set falsely.
        val v = UI {
                tableLayout {
                    imageView{
                        imageResource = R.drawable.armstrong_on_moon
                    }.lparams {
                        contentDescription = getString(R.string.hellomoon_description)
                        width = matchParent
                        height = matchParent
                        weight = 1f
                    }
                        tableRow {
                            button{
                                text = getString(R.string.hellomoon_play)
                                setTextColor(-1)
                                onClick { mPlayer.play(activity) }
                            }
                            button{
                                text = getString(R.string.hellomoon_stop)
                                onClick { mPlayer.stop() }
                            }
                        }.lparams {
                            weight = 0f
                            gravity = bottom or centerX
                        }
                    }

            }.view

        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer.stop()
    }
}



