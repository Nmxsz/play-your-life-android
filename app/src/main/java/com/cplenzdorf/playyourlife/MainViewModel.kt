package com.cplenzdorf.playyourlife

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cplenzdorf.playyourlife.services.LevelService

class MainViewModel : ViewModel() {
    private val levelService = LevelService()

    var currentExperience = levelService.experience

    val currentLevel: LiveData<Int>
        get() = MutableLiveData<Int>().apply { value = levelService.level }

    // LiveData zur Überwachung des Levels und der Erfahrungspunkte
    // LiveData für Level-Up Event
    private val _levelUpEvent = MutableLiveData<Unit>()
    val levelUpEvent: LiveData<Unit> = _levelUpEvent
    private val _currentLevel = MutableLiveData<Int>().apply { value = levelService.level }

    val _currentExperience = MutableLiveData<Int>().apply { value = levelService.experience }

    fun addExperience(points: Int) {
        val previousLevel = levelService.level
        levelService.addExperience(points)
        currentExperience = levelService.experience
        if (levelService.level > previousLevel) {
            // Level-Up erfolgt
            _levelUpEvent.value = Unit
        }
    }
}
