package juniafirdaus.com.dicodingfootball.detailevent

import juniafirdaus.com.dicodingfootball.model.Team

/**
 * Created by ${JUNIA} on 9/2/2018.
 */

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(detail: List<DetailEvent>?, home: List<Team>?, away: List<Team>?)
}