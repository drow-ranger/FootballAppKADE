package com.deonico.footballapp_kade.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Team(
        @SerializedName("idTeam")
        val teamId: String? = null,

        @SerializedName("strTeam")
        val teamName: String? = null,

        @SerializedName("strTeamBadge")
        val teamBadge: String? = null,

        @SerializedName("strDescriptionEN")
        val temDescription: String? = null,

        @SerializedName("intFormedYear")
        val formedYear: Int? = null,

        @SerializedName("strStadium")
        val stadium: String? = null
) : Serializable

data class TeamResponse(val teams: List<Team>)