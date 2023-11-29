package com.cplenzdorf.playyourlife

import android.media.MediaPlayer
import android.content.Context

class GameSoundPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playLevelUpSound() {
        mediaPlayer = MediaPlayer.create(context, R.raw.wow_level_up_ding)
        mediaPlayer?.start()

        // Optional: Listener hinzufügen, um den MediaPlayer freizugeben, wenn der Sound abgespielt wurde
        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }
    }

    fun playQuestSuccessSound() {
        mediaPlayer = MediaPlayer.create(context, R.raw.wow_success_quest)
        mediaPlayer?.start()

        // Optional: Listener hinzufügen, um den MediaPlayer freizugeben, wenn der Sound abgespielt wurde
        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }
    }
}
