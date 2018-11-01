package juniafirdaus.com.dicodingfootball.playerteam

import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.api.TheSportDBApi
import juniafirdaus.com.dicodingfootball.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getPlayerDetail(playerId:String){
        view.showLoading()

        launch(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayerTeam(playerId)),
                        PlayerResponse::class.java)

            }

            view.hideLoading()
            view.showPlayerTeam(data.await().player as List<PlayerItem>)
        }
    }
}