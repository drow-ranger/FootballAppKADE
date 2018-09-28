package com.deonico.footballapp_kade.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.helper.EventDBHelper
import com.deonico.footballapp_kade.model.Event
import com.deonico.footballapp_kade.model.EventFavorite
import com.deonico.footballapp_kade.model.EventTableConstant
import com.deonico.footballapp_kade.model.Team
import com.deonico.footballapp_kade.presenter.EventDetailView
import com.deonico.footballapp_kade.presenter.Presenter
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
        eventDate.text = event.dateEvent ?: " "
//        HOME
        homeTeam.text = event.strHomeTeam ?: " "
        homeScore.text = event.intHomeScore ?: " "
        homeFormation.text = event.strHomeFormation ?: " "
        homeGoals.text = parserGoal(event.strHomeGoalDetails ?: " ")
        homeShots.text = event.intHomeShots?.toString()
        homeGk.text = parserGoal(event.strHomeLineupGoalkeeper ?: " ")
        homeDef.text = parser(event.strHomeLineupDefense ?: " ")
        homeMdf.text = parser(event.strHomeLineupMidfield ?: " ")
        homeOfen.text = parser(event.strHomeLineupForward ?: " ")
        homeSubs.text = parser(event.strHomeLineupSubstitutes ?: " ")

//        AWAY
        awayTeam.text = event.strAwayTeam ?: " "
        awayScore.text = event.intAwayScore ?: " "
        awayFormation.text = event.strAwayFormation ?: " "
        awayGoals.text = parserGoal(event.strAwayGoalDetails ?: " ")
        awayShots.text = event.intAwayShots?.toString()
        awayGk.text = parserGoal(event.strAwayLineupGoalkeeper ?: " ")
        awayDef.text = parser(event.strAwayLineupDefense ?: " ")
        awayMdf.text = parser(event.strAwayLineupMidfield ?: " ")
        awayOfen.text = parser(event.strAwayLineupForward ?: " ")
        awaySubs.text = parser(event.strAwayLineupSubstitutes ?: " ")

//        TO SHOW GRAB THE TEAM EMBLEM
        presenter.getSepecificTeam(event.strHomeTeam)
        presenter.getSepecificTeam(event.strAwayTeam)
        getFavoriteState()
    }

    override fun showTeamEmblem(team: Team) {
        if (team.teamName.equals(event.strHomeTeam)) {
            Glide.with(this).load(team.teamBadge).into(homeEmblem)
        } else if (team.teamName.equals(event.strAwayTeam)) {
            Glide.with(this).load(team.teamBadge).into(awayEmblem)
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
