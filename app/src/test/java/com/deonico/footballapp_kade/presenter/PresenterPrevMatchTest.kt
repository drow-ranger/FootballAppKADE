package com.deonico.footballapp_kade.presenter

import com.deonico.footballapp_kade.TestContextProvider
import com.deonico.footballapp_kade.api.ApiRepository
import com.deonico.footballapp_kade.api.SportDBApi
import com.deonico.footballapp_kade.model.Event
import com.deonico.footballapp_kade.model.EventResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PresenterPrevMatchTest {

    @Mock
    private lateinit var viewEV: EventView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson


    private lateinit var presenterEV: Presenter<EventView>


    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenterEV = Presenter(viewEV, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPrevMatch() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)
        val league = "English Premiere League"

        //Testing response get data from server api
        `when`(gson.fromJson(apiRepository
                .doRequest(SportDBApi.getPrevMatches(league)),
                EventResponse::class.java
        )).thenReturn(response)

        //Testing get data
        presenterEV.getPrevMatch(league)

        verify(viewEV).showLoading()
        verify(viewEV).showEventList(event)
        verify(viewEV).hideLoading()
    }

}