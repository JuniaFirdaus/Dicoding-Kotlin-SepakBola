package juniafirdaus.com.dicodingfootball.teamevent

import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.TestProvider
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.api.TheSportDBApi
import juniafirdaus.com.dicodingfootball.model.EventsItem
import juniafirdaus.com.dicodingfootball.model.MatchResponse
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class EventPresenterTest {

    @Mock
    private
    lateinit var view: EventView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(view, apiRepository, gson, TestProvider())
    }

    @Test
    fun getLastEvent() {
        val teams: MutableList<EventsItem> = mutableListOf()
        val response = MatchResponse(teams)
        val league = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeam(league)),
                MatchResponse::class.java
        )).thenReturn(response)

        presenter.getLastEvent(league)

    }

    @Test
    fun getNextEvent() {
        val teams: MutableList<EventsItem> = mutableListOf()
        val response = MatchResponse(teams)
        val league = "432"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeam(league)),
                MatchResponse::class.java
        )).thenReturn(response)

        presenter.getNextEvent(league)
    }
}