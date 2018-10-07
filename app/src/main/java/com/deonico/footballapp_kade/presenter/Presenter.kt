package com.deonico.footballapp_kade.presenter

import com.google.gson.Gson
import com.deonico.footballapp_kade.api.ApiRepository
import com.deonico.footballapp_kade.api.SportDBApi
import com.deonico.footballapp_kade.helper.CoroutinesContextProvider
import com.deonico.footballapp_kade.model.*
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class Presenter<in T>(private val view: T,
                      private val repository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutinesContextProvider = CoroutinesContextProvider()) {

    fun getLeagueList(){
        if(view is EventView){
            async(context.main){
                val data = bg {
                    gson.fromJson(repository
                            .doRequest(SportDBApi.getLeagues()),
                            LeagueResponse::class.java
                    )
                }
                view.showLeagueList(data.await().leagues)
            }
        }
    }

    fun getEventDetail(eventId: String?) {
        if (view is EventDetailView) {
            view.showLoading()
            async(context.main){
                val data = bg{
                    gson.fromJson(repository
                            .doRequest(SportDBApi.getEventDetail(eventId)),
                            EventResponse::class.java
                    )
                }
                view.showEventDetail(data.await().events[0])
                view.hideLoading()
            }
        }
    }

    fun getNextMatch(leagueId: String?) {
        if (view is EventView) {
            view.showLoading()
            async(context.main){
                val data = bg{
                    gson.fromJson(repository
                            .doRequest(SportDBApi.getNextMatches(leagueId)),
                            EventResponse::class.java
                    )
                }
                view.showEventList(data.await().events)
                view.hideLoading()
            }
        }
    }

    fun getPrevMatch(leagueId: String?) {
        if (view is EventView) {
            view.showLoading()
            async(context.main){
                val data = bg{
                    gson.fromJson(repository
                            .doRequest(SportDBApi.getPrevMatches(leagueId)),
                            EventResponse::class.java
                    )
                }
                view.showEventList(data.await().events)
                view.hideLoading()
            }
        }
    }

    fun getTeamList(league: String?) {
        if (view is TeamView) {
            view.showLoading()

            async(context.main){
                val data = bg {
                    gson.fromJson(repository
                            .doRequest(SportDBApi.getTeams(league)),
                            TeamResponse::class.java
                    )
                }
                view.showTeamList(data.await().teams)
                view.hideLoading()
            }
        }
    }

    fun getSepecificTeam(teamName: String?) {
        if (view is TeamDetailView) {
            view.showLoading()
            async(context.main){
                val data = bg{
                    gson.fromJson(repository
                            .doRequest(SportDBApi.getSpecificTeam(teamName)),
                            TeamResponse::class.java
                    )
                }
                view.showTeamDetail(data.await().teams[0])
                view.hideLoading()
            }
        }
        if (view is EventDetailView) {
            view.showLoading()
            async(context.main){
                val data = bg{
                    gson.fromJson(repository
                            .doRequest(SportDBApi.getSpecificTeam(teamName)),
                            TeamResponse::class.java
                    )
                }
                view.showTeamEmblem(data.await().teams[0])
                view.hideLoading()
            }
        }

    }
}