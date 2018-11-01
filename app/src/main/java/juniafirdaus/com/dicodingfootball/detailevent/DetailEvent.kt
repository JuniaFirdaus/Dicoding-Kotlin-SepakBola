package juniafirdaus.com.dicodingfootball.detailevent

import com.google.gson.annotations.SerializedName
/**
 * Created by ${JUNIA} on 9/2/2018.
 */

data class DetailEvent (


        //Match Details AWAY
        @SerializedName("strAwayGoalDetails")
        val strAwayGoalDetails: String? = null,

        @SerializedName("strAwayRedCards")
        val strAwayRedCards: String? = null,

        @SerializedName("strAwayYellowCards")
        val strAwayYellowCards: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        val strAwayLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupDefense")
        val strAwayLineupDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        val strAwayLineupMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        val strAwayLineupForward: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        val strAwayLineupSubstitutes: String? = null,

        @SerializedName("strAwayFormation")
        val strAwayFormation: String? = null,

        @SerializedName("intAwayShots")
        val intAwayShots: String? = null,

        //Detail Team Home
        @SerializedName("strHomeGoalDetails")
        val strHomeGoalDetails: String? = null,

        @SerializedName("strHomeRedCards")
        val strHomeRedCards: String? = null,

        @SerializedName("strHomeYellowCards")
        val strHomeYellowCards: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        val strHomeLineupGoalkeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        val strHomeLineupDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        val strHomeLineupMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        val strHomeLineupForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        val strHomeLineupSubstitutes: String? = null,

        @SerializedName("strHomeFormation")
        val strHomeFormation: String? = null,

        @SerializedName("intHomeShots")
        val intHomeShots: String? = null

)