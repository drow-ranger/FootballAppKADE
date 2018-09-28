package com.deonico.footballapp_kade.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deonico.footballapp_kade.R
import com.deonico.footballapp_kade.activity.EventDetailActivity
import com.deonico.footballapp_kade.adapter.EventAdapter
import com.deonico.footballapp_kade.model.Event
import com.deonico.footballapp_kade.presenter.EventView
import com.deonico.footballapp_kade.presenter.Presenter
import kotlinx.android.synthetic.main.fragment_common_layout.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class PrevMatchFragment : Fragment(), EventView {
    private lateinit var presenter: Presenter<EventView>
    private var eventList: MutableList<Event> = mutableListOf()
    private lateinit var adapter: EventAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val leagueId = "4328"

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = Presenter(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_common_layout, container, false)
        adapter = EventAdapter(activity!!.applicationContext, eventList) {
            startActivity<EventDetailActivity>("eventId" to it.idEvent)
        }
        recyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.swipeRefresh
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            presenter.getPrevMatch(leagueId)
        }
        presenter.getPrevMatch(leagueId)

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


}