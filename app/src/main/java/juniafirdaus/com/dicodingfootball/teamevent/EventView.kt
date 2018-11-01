package juniafirdaus.com.dicodingfootball.teamevent

import juniafirdaus.com.dicodingfootball.model.EventsItem

interface EventView {
        fun showLoading()
        fun hideLoading()
        fun showTeamList(data: List<EventsItem>)
    }