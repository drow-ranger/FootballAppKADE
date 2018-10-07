package com.deonico.footballapp_kade.api

import android.net.Uri
import com.deonico.footballapp_kade.BuildConfig

object SportDBApi {

    fun getTeamDetail(idTeam: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id="+idTeam
    }

    fun getLeagues(): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("all_leagues.php")
                .build()
                .toString()
    }

    fun getEventDetail(eventId: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupevent.php")
                .appendQueryParameter("id", eventId)
                .build()
                .toString()
    }

    fun getSpecificTeam(teamName: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("searchteams.php")
                .appendQueryParameter("t", teamName)
                .build()
                .toString()
    }

    fun getTeams(league: String?): String {
        //return "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=Spain"
        val league = league?.replace(" ", "%20")
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
    }

    fun getPrevMatches(leagueId: String?): String {
        //https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + leagueId
    }

    fun getNextMatches(leagueId: String?): String {
        //https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id="+ leagueId
    }

}