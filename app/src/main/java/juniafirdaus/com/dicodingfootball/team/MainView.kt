package juniafirdaus.com.dicodingfootball.team

import juniafirdaus.com.dicodingfootball.model.Team

/**
 * Created by ${JUNIA} on 8/30/2018.
 */
interface MainView {

                fun showLoading()
                fun hideLoading()
                fun showTeamList(data: List<Team>)
}