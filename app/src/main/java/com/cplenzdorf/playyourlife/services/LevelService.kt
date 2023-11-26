package com.cplenzdorf.playyourlife.services

class LevelService {
    var experience = 0
        private set
    var level = 1
        private set

    private val experienceForNextLevel: Int
        get() = level * 100

    fun addExperience(points: Int) {
        experience += points
        checkLevelUp()
    }

    private fun checkLevelUp() {
        while (experience >= experienceForNextLevel) {
            experience -= experienceForNextLevel
            level++
            // Optional: Benachrichtige andere Komponenten über das Level-Up
        }
    }
}
