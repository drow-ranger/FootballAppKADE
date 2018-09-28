package com.deonico.footballapp_kade.presenter

import com.deonico.footballapp_kade.model.Team

interface TeamDetailView : BaseView {
    fun showTeamDetail(team: Team)
}