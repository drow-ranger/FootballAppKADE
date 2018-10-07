package com.deonico.footballapp_kade.presenter


import com.google.gson.Gson
import com.deonico.footballapp_kade.api.ApiRepository
import com.deonico.footballapp_kade.api.SportDBApi
import com.deonico.footballapp_kade.helper.CoroutinesContextProvider
import com.deonico.footballapp_kade.model.*
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamsPresenter(private val view: TeamView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutinesContextProvider = CoroutinesContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(SportDBApi.getTeams(league)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}