package juniafirdaus.com.dicodingfootball.team.teamdetail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.R.drawable.ic_add_to_favorites
import juniafirdaus.com.dicodingfootball.R.drawable.ic_added_to_favorites
import juniafirdaus.com.dicodingfootball.adapter.ViewPagerAdapter
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.db.FavoriteTeam
import juniafirdaus.com.dicodingfootball.db.database
import juniafirdaus.com.dicodingfootball.model.Team
import juniafirdaus.com.dicodingfootball.playerteam.PlayerFragment
import juniafirdaus.com.dicodingfootball.playerteam.PlayerOverview
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private var isFavorite: Boolean = false
    private lateinit var id: String
    private var menuItem: Menu? = null
    lateinit var presenter: TeamDetailPresenter

    private lateinit var idTeam: String
    private lateinit var nameTeam: String
    private lateinit var badgeTeam: String

    override fun showTeamDetail(team: List<Team>) {

        val imgGambar = team[0].teamBadge
        val imgBanner = team[0].strTeamFanart1
        val teamName = team[0].teamName

        idTeam = team[0].teamId.toString()
        nameTeam = team[0].teamName.toString()
        badgeTeam = team[0].teamBadge.toString()


        Glide.with(this).load(imgGambar).into(imageTeam)
        Glide.with(this).load(imgBanner).into(imageBanner)
        supportActionBar?.title = teamName

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        id = intent.getStringExtra("idTeam")
        favoriteState()


        val bind = Bundle()
        bind.putString("idTeam",id)

        Log.i("KEY",bind.toString())

        val adapter = ViewPagerAdapter(supportFragmentManager)
        val teamFragment = PlayerOverview()
        val playersFragment = PlayerFragment()
        teamFragment.arguments = bind
        playersFragment.arguments = bind
        adapter.populateFragment(teamFragment, "Team Overview")
        adapter.populateFragment(playersFragment, "Players")
        viewpagerTeam.adapter = adapter
        tabs.setupWithViewPager(viewpagerTeam)




        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
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
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    private fun favoriteState() {

        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.TEAM_ID to idTeam,
                        FavoriteTeam.TEAM_NAME to nameTeam,
                        FavoriteTeam.TEAM_BADGE to badgeTeam)
            }

        } catch (e: SQLiteConstraintException) {

        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {idTeam})",
                        "idTeam" to id)
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
