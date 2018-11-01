package juniafirdaus.com.dicodingfootball.search

import com.google.gson.annotations.SerializedName

data class SearchEventResponse(

	@field:SerializedName("event")
	val event: List<EventItem?>? = null
)