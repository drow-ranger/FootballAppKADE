package com.deonico.footballapp_kade.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Event(
        @SerializedName("intHomeShots")
        val intHomeShots: Any? = null,

        @SerializedName("strHomeLineupDefense")
        val strHomeLineupDefense: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        val strAwayLineupSubstitutes: String? = null,

        @SerializedName("strHomeLineupForward")
        val strHomeLineupForward: String? = null,

        @SerializedName("strHomeGoalDetails")
        val strHomeGoalDetails: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        val strAwayLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupMidfield")
        val strAwayLineupMidfield: String? = null,

        @SerializedName("idEvent")
        val idEvent: String? = null,

        @SerializedName("strHomeYellowCards")
        val strHomeYellowCards: String? = null,

        @SerializedName("idHomeTeam")
        val idHomeTeam: String? = null,

        @SerializedName("intHomeScore")
        val intHomeScore: String? = null,

        @SerializedName("dateEvent")
        val dateEvent: String? = null,

        @SerializedName("strAwayTeam")
        val strAwayTeam: String? = null,

        @SerializedName("strHomeLineupMidfield")
        val strHomeLineupMidfield: String? = null,

        @SerializedName("strDate")
        val strDate: String? = null,

        @SerializedName("strHomeFormation")
        val strHomeFormation: String? = null,

        @SerializedName("idAwayTeam")
        val idAwayTeam: String? = null,

        @SerializedName("strAwayRedCards")
        val strAwayRedCards: String? = null,


        @SerializedName("intAwayShots")
        val intAwayShots: Any? = null,


        @SerializedName("strTime")
        val strTime: String? = null,

        @SerializedName("strAwayGoalDetails")
        val strAwayGoalDetails: String? = null,

        @SerializedName("strAwayLineupForward")
        val strAwayLineupForward: String? = null,

        @SerializedName("strHomeRedCards")
        val strHomeRedCards: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        val strHomeLineupGoalkeeper: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        val strHomeLineupSubstitutes: String? = null,

        @SerializedName("strAwayFormation")
        val strAwayFormation: String? = null,

        @SerializedName("strAwayYellowCards")
        val strAwayYellowCards: String? = null,

        @SerializedName("strAwayLineupDefense")
        val strAwayLineupDefense: String? = null,

        @SerializedName("strHomeTeam")
        val strHomeTeam: String? = null,


        @SerializedName("intAwayScore")
        val intAwayScore: String? = null
) : Serializable

data class EventResponse(val events: List<Event>)