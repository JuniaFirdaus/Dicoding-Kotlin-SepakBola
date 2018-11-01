package juniafirdaus.com.dicodingfootball.teamevent

import juniafirdaus.com.dicodingfootball.api.TheSportDBApi
import juniafirdaus.com.dicodingfootball.model.MatchResponse
import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg

class EventPresenter(val view: EventView,
                     val apiRepository: ApiRepository,
                     val gson: Gson, val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLastEvent(league: String?) {
        view.showLoading()

        launch(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getLastEvent(league)),
                        MatchResponse::class.java
                )
            }
            view.hideLoading()
            view.showTeamList(data.await().events)
        }
    }

    fun getNextEvent(next: String?) {
        view.showLoading()

        launch(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextEvent(next)),
                        MatchResponse::class.java
                )
            }
            view.hideLoading()
            view.showTeamList(data.await().events)
        }
    }
}