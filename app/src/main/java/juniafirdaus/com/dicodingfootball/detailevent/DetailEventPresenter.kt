package juniafirdaus.com.dicodingfootball.detailevent

import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.model.TeamResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
/**
 * Created by ${JUNIA} on 9/2/2018.
 */

class DetailEventPresenter (
        private val view: DetailView,
        private val apiRepository: ApiRepository
) {

    fun loadDetailMatch(homeId: String?, awayId: String?, matchId: String?) {
        view.showLoading()

        doAsync {
            val reqEventHome = apiRepository.reqEvent(homeId)
            val reqEventAwayTeam = apiRepository.reqEvent(awayId)
            val reqEventDetail = apiRepository.reqDetail(matchId)
            val homeEvent = Gson().fromJson(reqEventHome, TeamResponse::class.java)
            val awayEvent = Gson().fromJson(reqEventAwayTeam, TeamResponse::class.java)
            val detailEvent = Gson().fromJson(reqEventDetail, DetailEventResponse::class.java
            )
            uiThread {
                view.hideLoading()
                view.showDetail(detailEvent?.detailItems, homeEvent?.teams, awayEvent?.teams)
            }

        }

    }

}