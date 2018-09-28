package com.deonico.footballapp_kade.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.model.TeamFavorite
import kotlinx.android.synthetic.main.item_club.view.*

class FavoriteTeamAdapter(private val context: Context, private val teamFavorite: List<TeamFavorite>, private val listener: (TeamFavorite) -> Unit) : RecyclerView.Adapter<FavoriteTeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.item_club,
                    parent,
                    false)
    )


    override fun getItemCount(): Int = teamFavorite.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teamFavorite[position], listener)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        fun bindItem(teamFavorite: TeamFavorite, listener: (TeamFavorite) -> Unit) {
            itemView.tvTeamName.text = teamFavorite.teamName
            Glide.with(context).load(teamFavorite.teamBadge).into(itemView.imgTeam)
            itemView.setOnClickListener {
                listener(teamFavorite)
            }
        }
    }
}
