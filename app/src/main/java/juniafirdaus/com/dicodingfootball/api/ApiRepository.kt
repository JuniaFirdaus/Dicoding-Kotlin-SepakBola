package juniafirdaus.com.dicodingfootball.api

import juniafirdaus.com.dicodingfootball.BuildConfig
import java.net.URL

class ApiRepository {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }

    fun reqEvent(teamId: String?): String {
        return URL(BuildConfig.TEAM_EVENT + teamId).readText()
    }

    fun reqDetail(matchId: String?): String {
        return URL(BuildConfig.DETAIL_EVENT + matchId).readText()
    }
}