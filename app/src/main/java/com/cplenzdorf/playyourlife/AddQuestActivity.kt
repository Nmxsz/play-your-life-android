package com.cplenzdorf.playyourlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cplenzdorf.playyourlife.models.Quest

class AddQuestActivity : AppCompatActivity() {
    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var rewardInput: EditText
    private var questId = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quest)
        titleInput = findViewById(R.id.questTitleInput)
        descriptionInput = findViewById(R.id.questDescriptionInput)
        rewardInput = findViewById(R.id.questRewardInput)

        val addQuestButton: Button = findViewById(R.id.addQuestButton)
        addQuestButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            val reward = rewardInput.text.toString().toInt()
            val quest = Quest(questId++, title, description, reward)
            val resultIntent = Intent()
            resultIntent.putExtra("NEW_QUEST", quest)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
