package juniafirdaus.com.dicodingfootball.playerdetail

interface PlayerDetailView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerDetail(data: List<PlayerDetailItem>)
}