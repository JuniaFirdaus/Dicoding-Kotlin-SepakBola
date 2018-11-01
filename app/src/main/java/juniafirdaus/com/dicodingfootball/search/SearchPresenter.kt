package juniafirdaus.com.dicodingfootball.search

import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.api.TheSportDBApi
import juniafirdaus.com.dicodingfootball.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchPresenter(private val view: SearchEventView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEventSearch(playerId:String){
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getSearchEvent(playerId)),
                        SearchEventResponse::class.java)

            }

            view.hideLoading()
            view.showSearchEvent(data.await().event as List<EventItem>)
        }
    }
}