package com.example.stefan.ankoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView


import org.jetbrains.anko.*
import org.jetbrains.anko.configuration

class MainActivity : Activity(), AnkoLogger {
    private val dataBank: List<TrueFalse> = listOf(
            TrueFalse(R.string.question_oceans, true),
            TrueFalse(R.string.question_mideast, false),
            TrueFalse(R.string.question_africa, false),
            TrueFalse(R.string.question_americas, true),
            TrueFalse(R.string.question_asia, true)
    )
    private var index = 0
    private var questionText: TextView? = null
    private val KEY_INDEX  = "index"
    private var isCheater = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        //MainActivityUi().setContentView(this)
        verticalLayout {
            gravity = Gravity.CENTER
            questionText = textView {
                //textResource =R.string.question
                padding = dip(24)
                gravity = Gravity.CENTER_HORIZONTAL
            }
            linearLayout {
                //gravity = Gravity.CENTER_HORIZONTAL
                //gravity = Gravity.CENTER_VERTICAL
                button("True") {
                    onClick { verify(true) }
                }
                button("False") {
                    onClick {
                        verify(false)
                    }
                }
                this.gravity = Gravity.CENTER_HORIZONTAL
            }
            button(R.string.cheat_button){
                onClick{
                    //startActivity<CheatActivity>("answer" to dataBank[index].answerTrue)
                    startActivityForResult<CheatActivity>(0, "answer" to dataBank[index].answerTrue)
                }
            }
            button{
                width = wrapContent
                height = wrapContent
                //text = "Next Question"
                onClick{ nextQuestion()}
                configuration(orientation = Orientation.LANDSCAPE){
                    this.gravity = Gravity.BOTTOM or Gravity.RIGHT
                }
                configuration(orientation = Orientation.PORTRAIT){
                    this.gravity = Gravity.CENTER_HORIZONTAL
                }
                text = "Next Question"
            }
        }
        nextQuestion()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?){
        super.onSaveInstanceState(savedInstanceState)
        info("onSaveInstanceState")
        savedInstanceState?.putInt(KEY_INDEX, index-1)
    }

    private fun nextQuestion() {
        index = (index + 1) % dataBank.size
        questionText?.setText(dataBank[index].question)
    }
    private fun verify(answer: Boolean) {

        val correct = answer == dataBank[index].answerTrue
        val reaction = when {
            isCheater -> "Cheating in wrong"
            correct -> "Correct"
            else -> "Incorrect"
        }

        toast(reaction)
        nextQuestion()

    }

    override fun onStart(){
        super.onStart()
        debug("onStart() called")
    }

    override fun onPause(){
        super.onPause()
        debug("onPause() called")
    }

    override fun onResume(){
        super.onResume()
        debug("onResume() called")
    }

    override fun onStop(){
        super.onStop()
        debug("onStop() called")
    }

    override fun onDestroy(){
        super.onDestroy()
        debug("onDestroy() called")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        isCheater = data?.getBooleanExtra("Answer_shown", false) ?: true
    }

}

/*
class MainActivityUi: AnkoComponent<MainActivity>{
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui){
        verticalLayout{
            gravity = Gravity.CENTER
            textView{
                textResource =R.string.question
                padding=dip(24)
                gravity = Gravity.CENTER_HORIZONTAL
            }
            linearLayout{
                button("True")
                button("False")
                gravity = Gravity.CENTER_HORIZONTAL
            }
            button("Next Question")
        }
    }
}*/

class TrueFalse(val question: Int, val answerTrue: Boolean)