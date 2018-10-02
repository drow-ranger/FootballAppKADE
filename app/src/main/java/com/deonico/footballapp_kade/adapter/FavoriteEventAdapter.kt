package com.deonico.footballapp_kade.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.api.ApiRepository
import com.deonico.footballapp_kade.api.SportDBApi
import com.deonico.footballapp_kade.changeFormatDate
import kotlinx.android.synthetic.main.item_match.view.*
import com.deonico.footballapp_kade.model.EventFavorite
import com.deonico.footballapp_kade.model.TeamResponse
import com.deonico.footballapp_kade.strToDate
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavoriteEventAdapter(private val context: Context, private val eventFavorite: List<EventFavorite>, private val listener: (EventFavorite) -> Unit) : RecyclerView.Adapter<FavoriteEventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.item_match,
                    parent,
                    false)
    )


    override fun getItemCount(): Int = eventFavorite.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(eventFavorite[position], listener)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        fun bindItem(eventFavorite: EventFavorite, listener: (EventFavorite) -> Unit) {
            getBadge(eventFavorite.idHomeTeam, itemView.homeLogo)
            getBadge(eventFavorite.idAwayTeam, itemView.awayLogo)
            itemView.dateMatch.text = changeFormatDate(strToDate(eventFavorite.dateEvent))
            itemView.homeTeam.text = eventFavorite.strHomeTeam
            itemView.homeScore.text = eventFavorite.intHomeScore
            itemView.awayTeam.text = eventFavorite.strAwayTeam
            itemView.awayScore.text = eventFavorite.intAwayScore
            itemView.setOnClickListener {
                listener(eventFavorite)
            }

        }
        private fun getBadge(idTeam: String?, imageView: ImageView) {

            val api = ApiRepository()
            val gson = Gson()

            var data: TeamResponse
            doAsync {

                data = gson.fromJson(api
                        .doRequest(SportDBApi.getTeamDetail(idTeam)),
                        TeamResponse::class.java
                )

                uiThread {
                    var linkBadge = data.teams.get(0).teamBadge
                    Picasso.get().load(linkBadge).into(imageView)
                }
            }
        }
    }
}
