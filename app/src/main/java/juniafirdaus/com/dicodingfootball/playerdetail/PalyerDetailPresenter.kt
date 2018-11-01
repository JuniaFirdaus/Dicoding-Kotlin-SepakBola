package juniafirdaus.com.dicodingfootball.playerdetail

import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.api.TheSportDBApi
import juniafirdaus.com.dicodingfootball.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerDetailPresenter(private val view: PlayerDetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getPlayerDetail(playerId:String){
        view.showLoading()

        launch(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayerDetail(playerId)),
                        PlayerDetailResponse::class.java)

            }
            view.hideLoading()
            view.showPlayerDetail(data.await().players as List<PlayerDetailItem>)
        }
    }
}