package com.cplenzdorf.playyourlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cplenzdorf.playyourlife.adapter.QuestAdapter
import com.cplenzdorf.playyourlife.databinding.ActivityMainBinding
import com.cplenzdorf.playyourlife.interfaces.QuestCompletionListener
import com.cplenzdorf.playyourlife.models.Quest
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), QuestCompletionListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lvQuestList: ListView

    private lateinit var fabAddQuest: FloatingActionButton
    private lateinit var questList: ArrayList<String>

    private lateinit var recyclerView: RecyclerView
    private lateinit var questAdapter: QuestAdapter

    private lateinit var levelProgressBar: ProgressBar
    private lateinit var tvPlayerLevel: TextView

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.levelUpEvent.observe(this) {
            // Level-Up Event behandeln
            Toast.makeText(this, "Level Up! Level: " + viewModel.currentLevel.value.toString(), Toast.LENGTH_SHORT).show()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fabAddQuest = findViewById(R.id.fabAddQuest)
        questList = ArrayList()

        fabAddQuest.setOnClickListener {
            // Öffne AddQuestActivity
            val intent = Intent(this, AddQuestActivity::class.java)
            startActivityForResult(intent, ADD_QUEST_REQUEST_CODE)
            questAdapter.notifyDataSetChanged()
        }

        questAdapter = QuestAdapter(viewModel.quests, this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = questAdapter

        levelProgressBar = findViewById(R.id.levelProgressBar)
        tvPlayerLevel = findViewById(R.id.tvPlayerLevel)
        tvPlayerLevel.text = viewModel.currentLevel.value.toString()

    }

    override fun onQuestCompleted(quest: Quest) {
        viewModel.addExperience(quest.experienceReward)
        updateLevelProgress(viewModel.currentExperience) // Beispielwert
        quest.completed = true
    }

    private fun updateLevelProgress(progress: Int) {
        tvPlayerLevel.text = viewModel.currentLevel.value.toString()
        println(viewModel.progress)
        levelProgressBar.progress = viewModel.progress
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_QUEST_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra("NEW_QUEST")?.let { newQuest ->
                viewModel.addQuest(newQuest as Quest)
                questAdapter.notifyDataSetChanged()
                // Füge die neue Quest zur Liste hinzu und aktualisiere den Adapter
                // Oder aktualisiere dein ViewModel, wenn du eines verwendest
            }
        }
    }

    companion object {
        private const val ADD_QUEST_REQUEST_CODE = 1
    }

}