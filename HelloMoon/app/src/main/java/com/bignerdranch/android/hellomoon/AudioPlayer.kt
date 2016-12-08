package com.bignerdranch.android.hellomoon

import android.content.Context
import android.media.MediaPlayer

class AudioPlayer{
    private var mPlayer: MediaPlayer? = null

    fun stop(){
        mPlayer?.release()
        mPlayer = null
    }

    fun play(c: Context){
        stop()

        mPlayer = MediaPlayer.create(c, R.raw.one_small_step)

        mPlayer?.setOnCompletionListener { MediaPlayer.OnCompletionListener { stop() } }

        mPlayer?.start()
    }
}