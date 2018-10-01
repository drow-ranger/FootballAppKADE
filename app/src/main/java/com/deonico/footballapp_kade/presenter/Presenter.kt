package com.deonico.footballapp_kade.presenter

import com.google.gson.Gson
import com.deonico.footballapp_kade.api.ApiRepository
import com.deonico.footballapp_kade.api.SportDBApi
import com.deonico.footballapp_kade.model.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Presenter<in T>(private val view: T) {
    private val repository = ApiRepository()
    private val gson = Gson()
    var leagues: MutableList<League> = mutableListOf()

    fun getLeagueList() {
        if (view is EventView) {
            doAsync {
                val data: List<League>? = gson.fromJson(repository.doRequest(SportDBApi.getLeagues()),
                        LeagueResponse::class.java).leagues

                for (league in data!!.iterator()) {
                    if (league.type.equals("Soccer")) {
                        leagues.add(league)
                    }
                }

                uiThread {
                    data?.let { view.showLeagueList(leagues) }
                }
            }
        }
    }

    fun getEventDetail(eventId: String?) {
        if (view is EventDetailView) {
            view.showLoading()
            doAsync {
                val data: Event? = gson.fromJson(repository.doRequest(SportDBApi.getEventDetail(eventId)),
                        EventResponse::class.java).events[0]

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showEventDetail(it1) }
                }
            }
        }
    }

    fun getNextMatch(leagueId: String?) {
        if (view is EventView) {
            view.showLoading()
            doAsync {
                val data: List<Event>? = gson.fromJson(repository.doRequest(SportDBApi.getNextMatches(leagueId)),
                        EventResponse::class.java).events

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showEventList(it1) }
                }
            }
        }
    }

    fun getPrevMatch(leagueId: String?) {
        if (view is EventView) {
            view.showLoading()
            doAsync {
                val data: List<Event>? = gson.fromJson(repository.doRequest(SportDBApi.getPrevMatches(leagueId)),
                        EventResponse::class.java).events

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showEventList(it1) }
                }
            }
        }
    }

    fun getTeamList(league: String?) {
        if (view is TeamView) {
            view.showLoading()
            doAsync {
                val data: List<Team>? = gson.fromJson(repository.doRequest(SportDBApi.getTeams(league)),
                        TeamResponse::class.java
                ).teams

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showTeamList(it1) }
                }
            }
        }
    }

    fun getSepecificTeam(teamName: String?) {
        if (view is TeamDetailView) {
            view.showLoading()
            doAsync {
                val data: Team? = gson.fromJson(repository.doRequest(SportDBApi.getSpecificTeam(teamName)),
                        TeamResponse::class.java).teams[0]

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showTeamDetail(it1) }

                }
            }
        }
        if (view is EventDetailView) {
            view.showLoading()
            doAsync {
                val data: Team? = gson.fromJson(repository.doRequest(SportDBApi.getSpecificTeam(teamName)),
                        TeamResponse::class.java).teams[0]

                uiThread {
                    view.hideLoading()
                    data?.let { it1 -> view.showTeamEmblem(it1) }

                }
            }
        }

    }
}