package juniafirdaus.com.dicodingfootball.playerteam

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerTeam(data: List<PlayerItem>)
}