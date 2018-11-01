package juniafirdaus.com.dicodingfootball.team.teamdetail

import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.TestProvider
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.api.TheSportDBApi
import juniafirdaus.com.dicodingfootball.model.Team
import juniafirdaus.com.dicodingfootball.model.TeamResponse
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamDetailPresenterTest {

    @Mock
    private
    lateinit var view: TeamDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamDetailPresenter(view, apiRepository, gson, TestProvider())
    }

    //testing pengambilan data detail
    @Test
    fun funGetTeamDetail() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val id = "4328"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(id)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeamDetail(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamDetail(teams)
        Mockito.verify(view).hideLoading()
    }

}