package com.cplenzdorf.playyourlife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cplenzdorf.playyourlife.R
import com.cplenzdorf.playyourlife.interfaces.QuestCompletionListener
import com.cplenzdorf.playyourlife.models.Quest

// QuestAdapter.kt
class QuestAdapter(private val quests: List<Quest>, private val listener: QuestCompletionListener) : RecyclerView.Adapter<QuestAdapter.QuestViewHolder>() {

    class QuestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.questTitle)
        val description: TextView = view.findViewById(R.id.questDescription)
        val completeButton: Button = view.findViewById(R.id.completeQuestButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quest, parent, false)
        return QuestViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        val quest = quests[position]
        holder.title.text = quest.title
        holder.description.text = quest.description
        holder.completeButton.setOnClickListener {
            // Implementiere die Logik zum Markieren der Quest als abgeschlossen
            listener.onQuestCompleted(quest)
        }
    }

    override fun getItemCount() = quests.size
}
