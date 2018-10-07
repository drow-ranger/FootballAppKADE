package com.deonico.footballapp_kade.presenter

import com.deonico.footballapp_kade.TestContextProvider
import com.deonico.footballapp_kade.api.ApiRepository
import com.deonico.footballapp_kade.api.SportDBApi
import com.deonico.footballapp_kade.model.Team
import com.deonico.footballapp_kade.model.TeamResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PresenterTeamListTest {

    @Mock
    private lateinit var viewTV: TeamView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenterTV: Presenter<TeamView>


    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenterTV = Presenter(viewTV, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val league = "English Premiere League"
        //Testing response get data from server api
        `when`(gson.fromJson(apiRepository
                .doRequest(SportDBApi.getTeams(league)),
                TeamResponse::class.java
        )).thenReturn(response)

        //Testing get data
        presenterTV.getTeamList(league)

        verify(viewTV).showLoading()
        verify(viewTV).showTeamList(teams)
        verify(viewTV).hideLoading()
    }

}