package com.deonico.footballapp_kade.presenter

import com.deonico.footballapp_kade.model.Event
import com.deonico.footballapp_kade.model.Team

interface EventDetailView : BaseView {
    fun showEventDetail(event: Event)
    fun showTeamEmblem(team: Team)
}