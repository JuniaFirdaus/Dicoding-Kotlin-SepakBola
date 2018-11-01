package juniafirdaus.com.dicodingfootball.team.teamdetail

import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.api.TheSportDBApi
import juniafirdaus.com.dicodingfootball.model.TeamResponse
import juniafirdaus.com.dicodingfootball.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        launch(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamResponse::class.java)

            }

            view.hideLoading()
            view.showTeamDetail(data.await().teams)
        }
    }
}