package com.cplenzdorf.playyourlife.models

import java.io.Serializable

data class Quest(
    val id: Int,
    val title: String,
    val description: String,
    val experienceReward: Int,
    var completed: Boolean = false
): Serializable