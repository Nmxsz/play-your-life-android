package com.cplenzdorf.playyourlife

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.cplenzdorf.playyourlife.models.Quest
import com.cplenzdorf.playyourlife.services.LevelService
import java.util.Calendar

class MainViewModel : ViewModel() {
    private val levelService = LevelService()

    var currentExperience = levelService.experience
    var experienceForNextLevel = levelService.experienceForNextLevel

    var progress = 0

    var isLevelUp = false;

    val currentLevel: LiveData<Int>
        get() = MutableLiveData<Int>().apply { value = levelService.level }

    // LiveData zur Überwachung des Levels und der Erfahrungspunkte
    // LiveData für Level-Up Event
    private val _levelUpEvent = MutableLiveData<Unit>()
    val levelUpEvent: LiveData<Unit> = _levelUpEvent
    private val _currentLevel = MutableLiveData<Int>().apply { value = levelService.level }

    // Beispieldaten
    val quests: MutableList<Quest> = mutableListOf(
        //-Quest(1, "Quest Titel 1", "Beschreibung 1", 10, false),
        //Quest(2, "Quest Titel 2", "Beschreibung 2", 20, true, true)
        // Weitere Quests...
    )

    val _currentExperience: LiveData<Int>
        get() = MutableLiveData<Int>().apply { value = levelService.experience }

    fun addExperience(points: Int) {
        val previousLevel = levelService.level
        levelService.addExperience(points)
        currentExperience = levelService.experience
        experienceForNextLevel = levelService.experienceForNextLevel
        if (levelService.level > previousLevel) {
            // Level-Up erfolgt
            _levelUpEvent.value = Unit
            isLevelUp = true
        } else {
            isLevelUp = false
        }
        progress = (currentExperience.toFloat() / levelService.experienceForNextLevel * 100).toInt()
    }

    fun addQuest(quest: Quest) {
        val highestQuestId = quests.maxByOrNull { it.id }?.id?.plus(1)

        quest.id = highestQuestId ?: 0
        println(quest)
        quests.add(quest)
    }

    fun resetDailyQuest() {
        println("Reset Daily Quests!")
        quests.forEach { quest: Quest ->
            if (quest.daily) {
                quest.completed = false
            }
        }
    }
}
