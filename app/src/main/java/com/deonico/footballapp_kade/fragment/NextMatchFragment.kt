package com.deonico.footballapp_kade.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.activity.EventDetailActivity
import com.deonico.footballapp_kade.adapter.EventAdapter
import com.deonico.footballapp_kade.api.ApiRepository
import com.deonico.footballapp_kade.model.Event
import com.deonico.footballapp_kade.model.League
import com.deonico.footballapp_kade.presenter.EventView
import com.deonico.footballapp_kade.presenter.Presenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_common_layout.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class NextMatchFragment : Fragment(), EventView {
    private lateinit var spinnerAdapeter: ArrayAdapter<String>
    private lateinit var presenter: Presenter<EventView>
    private lateinit var adapter: EventAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val leagueIds: MutableList<String> = mutableListOf()
    private val leagueNames: MutableList<String> = mutableListOf()
    private var eventList: MutableList<Event> = mutableListOf()
    private var leagueId = "4328"

    override fun onCreate(savedInstanceState: Bundle?) {
        val request = ApiRepository()
        val getData = Gson()
        presenter = Presenter(this, request, getData)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_common_layout, container, false)
        presenter.getLeagueList()
        spinnerAdapeter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, leagueNames)
        adapter = EventAdapter(activity!!.applicationContext, eventList) {
            startActivity<EventDetailActivity>("eventId" to it.idEvent)
        }
        recyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        view.spinner.adapter = spinnerAdapeter
        view.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueId = leagueIds[position]
                presenter.getNextMatch(leagueId)
            }

        }

        swipeRefreshLayout = view.swipeRefresh
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            presenter.getNextMatch(leagueId)
        }
        presenter.getNextMatch(leagueId)

        return view
    }

    override fun showEventList(events: List<Event>) {
        eventList.clear()
        eventList.addAll(events)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showLeagueList(leagues: List<League>) {
        leagueNames.clear()
        leagueIds.clear()
        for (league in leagues) {
            leagueNames.add(league.leagueNameMain!!.toString())
            leagueIds.add(league.leagueMain!!.toString())
        }
        spinnerAdapeter.notifyDataSetChanged()

    }
}