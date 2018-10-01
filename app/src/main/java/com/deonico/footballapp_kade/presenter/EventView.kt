package com.deonico.footballapp_kade.presenter

import com.deonico.footballapp_kade.model.Event
import com.deonico.footballapp_kade.model.League

interface EventView : BaseView {
    fun showLeagueList(leagues: List<League>)
    fun showEventList(events: List<Event>)
}