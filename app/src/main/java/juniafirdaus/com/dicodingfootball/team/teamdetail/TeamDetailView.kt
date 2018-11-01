package juniafirdaus.com.dicodingfootball.team.teamdetail

import juniafirdaus.com.dicodingfootball.model.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(team: List<Team>)
}