package com.cplenzdorf.playyourlife.interfaces

import com.cplenzdorf.playyourlife.models.Quest

interface QuestCompletionListener {
    fun onQuestCompleted(quest: Quest) {
        quest.completed = true
    }
}
