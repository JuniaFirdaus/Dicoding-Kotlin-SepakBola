package juniafirdaus.com.dicodingfootball.playerdetail

import com.google.gson.annotations.SerializedName

data class PlayerDetailResponse(

	@field:SerializedName("players")
	val players: List<PlayerDetailItem?>? = null
)