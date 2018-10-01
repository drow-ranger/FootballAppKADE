package com.deonico.footballapp_kade.presenter

import com.deonico.footballapp_kade.api.ApiRepository
import com.deonico.footballapp_kade.api.SportDBApi
import com.deonico.footballapp_kade.model.*
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView) {
    val repository = ApiRepository()
    val gson = Gson()
    var leagues: MutableList<League> = mutableListOf()

    fun getLeagueList() {
        view.showLoading()
        doAsync {
            val data: List<League>? = gson.fromJson(repository.doRequest(SportDBApi.getLeagues()),
                    LeagueResponse::class.java).leagues

            for (league in data!!.iterator()) {
                if (league.type.equals("Soccer")) {
                    leagues.add(league)
                }
            }

            uiThread {
                view.hideLoading()
                data?.let { view.showLeagueList(leagues) }
            }
        }
    }

    fun getNextMatch(leagueId: String?) {
        view.showLoading()
        doAsync {
            val data: List<Event>? = gson.fromJson(repository.doRequest(SportDBApi.getNextMatch(leagueId)),
                    EventResponse::class.java).events

            uiThread {
                view.hideLoading()
                data?.let { it -> view.showMatchList(it) }
            }
        }
    }
}