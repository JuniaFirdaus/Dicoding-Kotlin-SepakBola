package juniafirdaus.com.dicodingfootball.detailevent

import com.google.gson.annotations.SerializedName

/**
 * Created by ${JUNIA} on 9/2/2018.
 */

data class DetailEventResponse(
        @SerializedName("events") val detailItems: List<DetailEvent>?
)