package com.cplenzdorf.playyourlife.models

data class Quest(
    val id: Int,
    val title: String,
    val description: String,
    val experienceReward: Int,
    var completed: Boolean = false
)