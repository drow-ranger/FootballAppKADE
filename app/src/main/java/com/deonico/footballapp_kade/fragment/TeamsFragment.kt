package com.deonico.footballapp_kade.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.activity.TeamDetailActivity
import com.deonico.footballapp_kade.adapter.TeamAdapter
import com.deonico.footballapp_kade.api.ApiRepository
import com.deonico.footballapp_kade.model.Team
import com.deonico.footballapp_kade.presenter.Presenter
import com.deonico.footballapp_kade.presenter.TeamView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_teams.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class TeamsFragment : Fragment(), TeamView {

    private lateinit var adapter: TeamAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private var teamList: MutableList<Team> = mutableListOf()
    private var leagueList: MutableList<String> = mutableListOf()
    private lateinit var swRefresh: SwipeRefreshLayout
    private lateinit var presenter: Presenter<TeamView>
    private var leagueName = "English Premiere league" //default league

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leagueList.addAll(resources.getStringArray(R.array.league))
        //Get data
        val request = ApiRepository()
        val getData = Gson()
        presenter = Presenter(this, request, getData)
        spinnerAdapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, leagueList)
        adapter = TeamAdapter(ctx, teamList) {
            ctx.startActivity(intentFor<TeamDetailActivity>("team" to it.teamName))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_teams, container, false)
        swRefresh = view.swipeRefresh
        swRefresh.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )
        swRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }
        view.spinnerLeague.adapter = spinnerAdapter
        view.spinnerLeague.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinnerAdapter.getItem(position).toString()
                presenter.getTeamList(leagueName)
            }

        }
        view.rvTeams.adapter = adapter
        view.rvTeams.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        presenter.getTeamList(leagueName)
        return view
    }


    override fun showLoading() {
        swRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swRefresh.isRefreshing = false
    }


    override fun showTeamList(teams: List<Team>) {
        teamList.clear()
        teamList.addAll(teams)
        adapter.notifyDataSetChanged()
    }

}
