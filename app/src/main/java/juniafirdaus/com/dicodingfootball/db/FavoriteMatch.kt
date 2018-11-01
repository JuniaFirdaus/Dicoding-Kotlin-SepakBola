package juniafirdaus.com.dicodingfootball.db

data class FavoriteMatch(val id: Long?,
                         val eventId: String?,
                         val teamId: String?,
                         val homeId: String?,
                         val dateMatch: String?,
                         val homeTeam: String?,
                         val awayTeam: String?,
                         val scoreHome: String?,
                         val scoreAway: String?


) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val TEAM_ID: String = "TEAM_ID"
        const val HOME_ID: String = "TEAM_NAME"
        const val DATE_MATCH: String = "DATE_MATCH"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val SCORE_HOME: String = "SCORE_TEAM"
        const val SCORE_AWAY: String = "SCORE_AWAY"
    }
}