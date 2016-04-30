package com.example.stefan.ankoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import org.jetbrains.anko.*

class CheatActivity: Activity(){
    private fun setReturn(isCheater: Boolean){
        var data = Intent()
        data.putExtra("Answer_shown", isCheater)
        setResult(RESULT_OK, data)

    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val answerIsTrue = getIntent().getBooleanExtra("answer", false)
        setReturn(false)

        val answer = if (answerIsTrue) "Correct" else "Incorrect"

        verticalLayout {
            gravity = Gravity.CENTER_HORIZONTAL
            val answerField = textView(R.string.warning_text){
                padding = dip(20)
            }
            //val answerField = textView(answer){
            //    visibility = 1
            //}
            button(R.string.cheat_button){
                onClick{
                    answerField.text = answer
                    setReturn(true)

                }
            }
        }
    }
}
