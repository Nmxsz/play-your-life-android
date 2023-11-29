package com.cplenzdorf.playyourlife

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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

    private lateinit var fabMenu: FloatingActionButton
    private lateinit var questList: ArrayList<String>

    private lateinit var recyclerView: RecyclerView
    private lateinit var questAdapter: QuestAdapter

    private lateinit var levelProgressBar: ProgressBar
    private lateinit var tvXpText: ProgressBar
    private lateinit var tvPlayerLevel: TextView

    private lateinit var viewModel: MainViewModel
    private lateinit var gameSoundPlayer: GameSoundPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameSoundPlayer = GameSoundPlayer(this)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.levelUpEvent.observe(this) {
            // Level-Up Event behandeln
            Toast.makeText(this, "Level Up! Level: " + viewModel.currentLevel.value.toString(), Toast.LENGTH_SHORT).show()
            gameSoundPlayer.playLevelUpSound()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fabMenu = findViewById(R.id.fabMenu)
        questList = ArrayList()

        questAdapter = QuestAdapter(viewModel.quests, this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = questAdapter

        levelProgressBar = findViewById(R.id.levelProgressBar)
        tvPlayerLevel = findViewById(R.id.tvPlayerLevel)
        tvPlayerLevel.text = viewModel.currentLevel.value.toString()
        updateXpText()

        fabMenu.setOnClickListener { v -> showPopupMenu(v) }


    }

    private fun showPopupMenu(view: View) {
        PopupMenu(this, view).apply {
            // Inflating the Popup using xml file
            menuInflater.inflate(R.menu.menu_main, menu)

            // Adding click listener
            setOnMenuItemClickListener { menuItem ->
                onOptionsItemSelected(menuItem)
                true
            }

            // Showing the popup menu
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle settings action
                true
            }
            R.id.action_profile -> {
                // Handle profile action
                true
            }
            R.id.action_quests -> {
                // Handle quests action
                true
            }
            R.id.action_add_task -> {
                // Handle add task action
                // Öffne AddQuestActivity
                val intent = Intent(this, AddQuestActivity::class.java)
                startActivityForResult(intent, ADD_QUEST_REQUEST_CODE)
                true
            }
            R.id.action_login_logout -> {
                // Handle login/logout action
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQuestCompleted(quest: Quest) {
        viewModel.addExperience(quest.experienceReward)
        updateLevelProgress() // Beispielwert
        // Markiere die Quest als abgeschlossen
        viewModel.quests.find { it.id == quest.id }?.completed = true
        updateQuestsList()
        gameSoundPlayer.playQuestSuccessSound()

    }

    private fun updateQuestsList() {
        questAdapter = QuestAdapter(viewModel.quests, this)
        recyclerView.adapter = questAdapter
    }

    private fun updateLevelProgress() {
        tvPlayerLevel.text = viewModel.currentLevel.value.toString()
        updateXpText()
        if(viewModel.isLevelUp) {
            animateLevelUp()
        } else {
            updateProgressBar(viewModel.progress)
        }
    }

    private fun updateXpText() {
        val progressText = "XP: ${viewModel.currentExperience} / ${viewModel.experienceForNextLevel}"
        findViewById<TextView>(R.id.xpProgressText).text = progressText
    }

    private fun animateLevelUp() {
        // Erste Animation: Aktuelle Erfahrung bis 100%
        val firstTarget = 100
        val firstAnimator = ObjectAnimator.ofInt(levelProgressBar, "progress", levelProgressBar.progress, firstTarget)
        firstAnimator.duration = 500 // Dauer der Animation bis 100%

        // Zweite Animation: 0% bis zum aktuellen Fortschritt im neuen Level
        val secondTarget = viewModel.progress
        val secondAnimator = ObjectAnimator.ofInt(levelProgressBar, "progress", 0, secondTarget)
        secondAnimator.duration = 500 // Dauer der Animation bis zum aktuellen Fortschritt

        // Starte die erste Animation
        firstAnimator.start()

        // Nach Abschluss der ersten Animation, starte die zweite Animation
        firstAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                secondAnimator.start()
            }
        })
    }

    private fun updateProgressBar(targetProgress: Int) {
        ObjectAnimator.ofInt(levelProgressBar, "progress", levelProgressBar.progress, targetProgress)
            .setDuration(500) // Dauer der Animation
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_QUEST_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra("NEW_QUEST")?.let { newQuest ->
                viewModel.addQuest(newQuest as Quest)
                updateQuestsList()
            }
        }
    }

    companion object {
        private const val ADD_QUEST_REQUEST_CODE = 1
    }

}