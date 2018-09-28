package com.deonico.footballapp_kade.presenter

import com.deonico.footballapp_kade.model.Team

interface TeamView : BaseView{
    fun showTeamList(teams: List<Team>)
}