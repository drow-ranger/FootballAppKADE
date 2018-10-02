package com.deonico.footballapp_kade.model

import java.io.Serializable

data class EventFavorite(
        val id: Long,
        val idEvent: String,
        val dateEvent: String,
        val strHomeTeam: String,
        val intHomeScore: String,
        val strAwayTeam: String,
        val intAwayScore: String,
        val idHomeTeam: String,
        val idAwayTeam: String
): Serializable

object EventTableConstant {
    const val TABLE_NAME = "Event_Favorite"
    const val ID = "ID"
    const val ID_EVENT = "ID_EVENT"
    const val EVENT_DATE = "EVENT_DATE"
    const val HOME_TEAM = "HOME_TEAM"
    const val HOME_SCORE = "HOME_SCORE"
    const val AWAY_TEAM = "AWAY_TEAM"
    const val AWAY_SCORE = "AWAY_SCORE"
    const val HOME_ID = "HOME_ID"
    const val AWAY_ID = "AWAY_ID"
}