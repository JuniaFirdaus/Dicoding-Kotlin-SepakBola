package juniafirdaus.com.dicodingfootball.detailevent

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.R.drawable.ic_add_to_favorites
import juniafirdaus.com.dicodingfootball.R.drawable.ic_added_to_favorites
import juniafirdaus.com.dicodingfootball.R.id.add_to_favorite
import juniafirdaus.com.dicodingfootball.R.menu.detail_menu
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.db.FavoriteMatch
import juniafirdaus.com.dicodingfootball.db.database
import juniafirdaus.com.dicodingfootball.model.Team
import kotlinx.android.synthetic.main.activity_detail_event.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by ${JUNIA} on 9/2/2018.
 */
class DetailActivityEvent : AppCompatActivity(), DetailView {
    private lateinit var detailEventPresenter: DetailEventPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var homeId: String
    private lateinit var awayId: String
    private lateinit var matchId: String

    private lateinit var dateMatch: String
    private lateinit var homeTeam: String
    private lateinit var awayTeam: String
    private lateinit var homeScore: String
    private lateinit var awayScore: String


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val i = intent
        detailEventPresenter = DetailEventPresenter(this, ApiRepository())

        txt_date.text = i.getStringExtra("dateMatch")
        txt_home.text = i.getStringExtra("homeTeam")
        txt_team_away.text = i.getStringExtra("awayTeam")

        txt_score_home.text = i.getStringExtra("goalHome")
        txt_score_away.text = i.getStringExtra("goalAway")

        if (TextUtils.isEmpty(i.getStringExtra("goalHome")) || TextUtils.isEmpty(i.getStringExtra("goalAway"))) {
            homeScore = "-"
            awayScore = "-"
            txt_score.text = "" + ":" + ""

        } else {
            txt_score.text = i.getStringExtra("goalHome") + ":" + i.getStringExtra("goalAway")
            homeScore = i.getStringExtra("goalHome")
            awayScore = i.getStringExtra("goalAway")
        }

        homeId = i.getStringExtra("idHome")
        awayId = i.getStringExtra("idAway")
        matchId = i.getStringExtra("matchId")

        dateMatch = i.getStringExtra("dateMatch")
        homeTeam = i.getStringExtra("homeTeam")
        awayTeam = i.getStringExtra("awayTeam")

        detailEventPresenter.loadDetailMatch(homeId, awayId, matchId)

        favoriteState()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        prograss.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        prograss.visibility = View.INVISIBLE
    }

    override fun showDetail(detail: List<DetailEvent>?, home: List<Team>?, away: List<Team>?) {
        try {
            let {
                val data = detail?.get(0)
                val homeData = home?.get(0)
                val awayData = away?.get(0)

                val goalHome: String? = data?.strHomeGoalDetails
                goalHome?.replace(";", "\n")
                val redHome: String? = data?.strHomeRedCards
                redHome?.replace(";", "\n")
                val yellowHome: String? = data?.strHomeYellowCards
                yellowHome?.replace(";", "\n")
                val gkHome: String? = data?.strHomeLineupGoalkeeper
                gkHome?.replace(";", "\n")
                val defHome: String? = data?.strHomeLineupDefense
                defHome?.replace(";", "\n")
                val midHome: String? = data?.strHomeLineupMidfield
                midHome?.replace(";", "\n")
                val forHome: String? = data?.strHomeLineupForward
                forHome?.replace(";", "\n")
                val subHome: String? = data?.strHomeLineupSubstitutes
                subHome?.replace(";", "\n")

                val goalAway: String? = data?.strAwayGoalDetails
                goalAway?.replace(";", "\n")
                val redAway: String? = data?.strAwayRedCards
                redAway?.replace(";", "\n")
                val yellowAway: String? = data?.strAwayYellowCards
                yellowAway?.replace(";", "\n")
                val gkAway: String? = data?.strAwayLineupGoalkeeper
                gkAway?.replace(";", "\n")
                val defAway: String? = data?.strAwayLineupDefense
                defAway?.replace(";", "\n")
                val midAway: String? = data?.strAwayLineupMidfield
                midAway?.replace(";", "\n")
                val forAway: String? = data?.strAwayLineupForward
                forAway?.replace(";", "\n")
                val subAway: String? = data?.strAwayLineupSubstitutes
                subAway?.replace(";", "\n")

                Glide.with(ctx).load(homeData?.teamBadge).into(logo_home)
                txt_couch.text = homeData?.strManager
                txt_goal_home.text = goalHome
                txt_shots_home.text = data?.intHomeShots
                txt_red_home.text = redHome
                txt_yellow_home.text = yellowHome
                txt_gk_home.text = gkHome
                txt_def_home.text = defHome
                txt_mid_home.text = midHome
                txt_for_home.text = forHome
                txt_sub_home.text = subHome

                Glide.with(ctx).load(awayData?.teamBadge).into(logo_away)
                txt_couch_away.text = awayData?.strManager
                txt_goal_away.text = goalAway
                txt_shots_away.text = data?.intAwayShots
                txt_red_away.text = redAway
                txt_yellow_away.text = yellowAway
                txt_gk_away.text = gkAway
                txt_def_away.text = defAway
                txt_mid_away.text = midAway
                txt_for_away.text = forAway
                txt_sub_away.text = subAway
            }
        } catch (e: Exception) {
            Log.d("TAG", e.toString())
        }
    }

    private fun favoriteState() {

        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {matchId})",
                            "matchId" to matchId)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE,
                        FavoriteMatch.EVENT_ID to matchId,
                        FavoriteMatch.TEAM_ID to awayId,
                        FavoriteMatch.HOME_ID to homeId,

                        FavoriteMatch.DATE_MATCH to dateMatch,
                        FavoriteMatch.HOME_TEAM to homeTeam,
                        FavoriteMatch.AWAY_TEAM to awayTeam,
                        FavoriteMatch.SCORE_HOME to homeScore,
                        FavoriteMatch.SCORE_AWAY to awayScore


                )
            }

        } catch (e: SQLiteConstraintException) {

        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE, "(EVENT_ID = {matchId})",
                        "matchId" to matchId)
            }
        } catch (e: SQLiteConstraintException) {

        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}
