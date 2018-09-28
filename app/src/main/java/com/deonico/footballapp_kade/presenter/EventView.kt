package com.deonico.footballapp_kade.presenter

import com.deonico.footballapp_kade.model.Event

interface EventView : BaseView {
    fun showEventList(events: List<Event>)
}