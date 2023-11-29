package com.cplenzdorf.playyourlife

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DailyResetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Logik zum Zurücksetzen der Daily Quests
        resetDailyQuests()
    }

    private fun resetDailyQuests() {}

}
