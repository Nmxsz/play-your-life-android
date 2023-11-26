package com.cplenzdorf.playyourlife

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddQuestActivity : AppCompatActivity() {
    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var rewardInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quest)

        titleInput = findViewById(R.id.questTitleInput)
        descriptionInput = findViewById(R.id.questDescriptionInput)
        rewardInput = findViewById(R.id.questRewardInput)
        val addButton: Button = findViewById(R.id.addQuestButton)

        addButton.setOnClickListener {
            // Implementiere Logik zum Hinzufügen der Quest
        }
    }
}
