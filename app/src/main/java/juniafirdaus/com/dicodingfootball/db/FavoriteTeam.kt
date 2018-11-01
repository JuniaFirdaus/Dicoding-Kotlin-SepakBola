package juniafirdaus.com.dicodingfootball.db

data class FavoriteTeam(val id: Long?,
                        val idTeam: String,
                        val teamName: String?,
                        val teamBadge: String?

) {

    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}