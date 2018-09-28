package com.deonico.footballapp_kade.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.model.Team
import kotlinx.android.synthetic.main.item_club.view.*

class TeamAdapter(private val context: Context, private val teams: List<Team>, private val listener: (Team) -> Unit) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.item_club,
                    parent,
                    false)
    )


    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)


    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        fun bindItem(team: Team, listener: (Team) -> Unit) {
            itemView.tvTeamName.text = team.teamName
            Glide.with(context).load(team.teamBadge).into(itemView.imgTeam)
            itemView.setOnClickListener {
                listener(team)
            }
        }
    }
}