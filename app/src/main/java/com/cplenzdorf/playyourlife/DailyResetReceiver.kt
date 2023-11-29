package com.cplenzdorf.playyourlife

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider

class DailyResetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        println(intent)
        val isDailyReset = intent.getBooleanExtra("DAILY_RESET", false)
        println(isDailyReset)

        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        if (isDailyReset) {
            sharedPreferences.edit().putBoolean("DailyQuestsReset", true).apply()
            println(context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE).getBoolean("DailyQuestsReset", false))
        }
    }
}

