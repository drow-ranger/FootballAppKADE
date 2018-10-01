package com.deonico.footballapp_kade.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class League(
        @SerializedName("idLeague")
        val leagueMain: String? = null,

        @SerializedName("strLeague")
        val leagueNameMain: String? = null,

        @SerializedName("strLeagueAlternate")
        val alternateLeagueName: String? = null,

        @SerializedName("strSport")
        val type: String? = null
) : Serializable

data class LeagueResponse(val leagues: List<League>)