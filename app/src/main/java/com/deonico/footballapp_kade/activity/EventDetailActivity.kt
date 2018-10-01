package com.deonico.footballapp_kade.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.changeFormatDate
import com.deonico.footballapp_kade.helper.EventDBHelper
import com.deonico.footballapp_kade.model.Event
import com.deonico.footballapp_kade.model.EventFavorite
import com.deonico.footballapp_kade.model.EventTableConstant
import com.deonico.footballapp_kade.model.Team
import com.deonico.footballapp_kade.presenter.EventDetailView
import com.deonico.footballapp_kade.presenter.Presenter
import com.deonico.footballapp_kade.strToDate
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.onRefresh

class EventDetailActivity : AppCompatActivity(), EventDetailView, AnkoLogger {


    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var presenter: Presenter<EventDetailView>
    private lateinit var eventId: String
    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        eventId = intent.getStringExtra("eventId")
        presenter = Presenter(this)
        presenter.getEventDetail(eventId)


        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefresh.onRefresh {
            presenter.getEventDetail(eventId)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun parserGoal(input: String): String {
        return input.replace(";", "\n", false)
    }

    private fun parser(input: String): String {
        return input.replace("; ", "\n", false)
    }

    override fun showEventDetail(event: Event) {
        this.event = event
        date.text = changeFormatDate(strToDate(event.dateEvent))
//        HOME
        team_home.text = event.strHomeTeam ?: " "
        home_score.text = event.intHomeScore ?: " "
        home_formation.text = event.strHomeFormation ?: " "
        home_goal.text = parserGoal(event.strHomeGoalDetails ?: " ")
        home_shot.text = event.intHomeShots?.toString()
        home_goalkeeper.text = parserGoal(event.strHomeLineupGoalkeeper ?: " ")
        home_defense.text = parser(event.strHomeLineupDefense ?: " ")
        home_midlefield.text = parser(event.strHomeLineupMidfield ?: " ")
        home_forward.text = parser(event.strHomeLineupForward ?: " ")
        home_subtituties.text = parser(event.strHomeLineupSubstitutes ?: " ")
        home_yellowcard.text = parser(event.strHomeYellowCards ?: " ")
        home_redcard.text = parser(event.strHomeRedCards ?: " ")

//        AWAY
        team_away.text = event.strAwayTeam ?: " "
        away_score.text = event.intAwayScore ?: " "
        away_formation.text = event.strAwayFormation ?: " "
        away_goal.text = parserGoal(event.strAwayGoalDetails ?: " ")
        away_shot.text = event.intAwayShots?.toString()
        away_goalkeeper.text = parserGoal(event.strAwayLineupGoalkeeper ?: " ")
        away_defense.text = parser(event.strAwayLineupDefense ?: " ")
        away_midlefield.text = parser(event.strAwayLineupMidfield ?: " ")
        away_forward.text = parser(event.strAwayLineupForward ?: " ")
        away_subtituties.text = parser(event.strAwayLineupSubstitutes ?: " ")
        away_yellowcard.text = parser(event.strAwayYellowCards ?: " ")
        away_redcard.text = parser(event.strAwayRedCards ?: " ")

//        TO SHOW GRAB THE TEAM EMBLEM
        presenter.getSepecificTeam(event.strHomeTeam)
        presenter.getSepecificTeam(event.strAwayTeam)
        getFavoriteState()
    }

    override fun showTeamEmblem(team: Team) {
        if (team.teamName.equals(event.strHomeTeam)) {
            Glide.with(this).load(team.teamBadge).into(logo_home)
        } else if (team.teamName.equals(event.strAwayTeam)) {
            Glide.with(this).load(team.teamBadge).into(logo_away)
        }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

//    DATABASE STUFF

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favourite_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_favourite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
            }
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    private fun getFavoriteState() {
        try {
            EventDBHelper.getInstance(this).use {
                val result = select(EventTableConstant.TABLE_NAME).whereArgs("${EventTableConstant.ID_EVENT} = ${event.idEvent}")
                val favorite = result.parseList(classParser<EventFavorite>())
                if (!favorite.isEmpty()) isFavorite = true
                setFavorite()
            }
        } catch (e: SQLiteConstraintException) {
            info { e.localizedMessage }
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_on)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_off)
    }

    private fun addToFavorite() {
        try {
            val homeScore = event.intHomeScore ?: " "
            val awayScore = event.intAwayScore ?: " "
            EventDBHelper.getInstance(this).use {
                insert(EventTableConstant.TABLE_NAME,
                        EventTableConstant.ID_EVENT to event.idEvent,
                        EventTableConstant.EVENT_DATE to event.dateEvent,
                        EventTableConstant.HOME_TEAM to event.strHomeTeam,
                        EventTableConstant.HOME_SCORE to homeScore,
                        EventTableConstant.AWAY_TEAM to event.strAwayTeam,
                        EventTableConstant.AWAY_SCORE to awayScore
                )
            }
            snackbar(swipeRefresh, "added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            val use = EventDBHelper.getInstance(this).use {
                delete(EventTableConstant.TABLE_NAME, "${EventTableConstant.ID_EVENT} = ${event.idEvent}")
            }
            snackbar(swipeRefresh, "removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

}
