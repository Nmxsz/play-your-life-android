package com.cplenzdorf.playyourlife

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cplenzdorf.playyourlife.adapter.QuestAdapter
import com.cplenzdorf.playyourlife.databinding.ActivityMainBinding
import com.cplenzdorf.playyourlife.models.Quest
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lvQuestList: ListView

    private lateinit var fabAddQuest: FloatingActionButton
    private lateinit var questList: ArrayList<String>

    private lateinit var recyclerView: RecyclerView
    private lateinit var questAdapter: QuestAdapter

    private lateinit var levelProgressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fabAddQuest = findViewById(R.id.fabAddQuest)
        questList = ArrayList()

        questList.add("Apfel")
        questList.add("Banane")

//        questAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, questList)
//        lvQuestList.adapter = questAdapter

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Beispieldaten
        val quests: List<Quest> = listOf(
            Quest(1, "Quest Titel 1", "Beschreibung 1", 10),
            Quest(2, "Quest Titel 2", "Beschreibung 2", 20)
            // Weitere Quests...
        )

        fabAddQuest.setOnClickListener {
            // Öffne AddQuestActivity
            val intent = Intent(this, AddQuestActivity::class.java)
            startActivity(intent)
        }

        questAdapter = QuestAdapter(quests)
        recyclerView.adapter = questAdapter

        levelProgressBar = findViewById(R.id.levelProgressBar)
        updateLevelProgress(50) // Beispielwert
    }

    private fun updateLevelProgress(progress: Int) {
        levelProgressBar.progress = progress
    }

}