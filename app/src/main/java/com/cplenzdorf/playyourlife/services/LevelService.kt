package com.cplenzdorf.playyourlife.services

class LevelService {
    var experience = 0
        private set
    var level = 1
        private set

    var experienceForNextLevel = 100

    // Linearer Anstieg der XP-Anforderungen
    private fun calculateExperienceForNextLevel() {
        experienceForNextLevel = 100 * level // Jedes Level benötigt mehr XP
    }

    fun addExperience(points: Int) {
        experience += points
        checkLevelUp()
    }

    private fun checkLevelUp() {
        while (experience >= experienceForNextLevel) {
            experience -= experienceForNextLevel
            level++
            calculateExperienceForNextLevel()
            // Optional: Benachrichtige andere Komponenten über das Level-Up
        }
    }
}
