package juniafirdaus.com.dicodingfootball.search

interface SearchEventView {
        fun showLoading()
        fun hideLoading()
        fun showSearchEvent(data: List<EventItem>)
    }