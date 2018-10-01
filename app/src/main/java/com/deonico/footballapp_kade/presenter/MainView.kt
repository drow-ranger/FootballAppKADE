package com.deonico.footballapp_kade.presenter

import com.deonico.footballapp_kade.model.Event
import com.deonico.footballapp_kade.model.League
import com.deonico.footballapp_kade.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(leagues: List<League>)
    fun showMatchList(events: List<Event>)
}