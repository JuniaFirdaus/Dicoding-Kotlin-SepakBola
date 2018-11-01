package juniafirdaus.com.dicodingfootball.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.R.id.*
import juniafirdaus.com.dicodingfootball.R.layout.activity_home
import juniafirdaus.com.dicodingfootball.team.TeamFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                teams -> {
                    loadTeamFragment(savedInstanceState)
                }

                event->{
                    loadEventFragment(savedInstanceState)
                }

                favorites -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = teams
    }


    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteHomeFragment(), FavoriteHomeFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadTeamFragment(savedInstanceState: Bundle?){
        if (savedInstanceState==null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamFragment(), TeamFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadEventFragment(savedInstanceState: Bundle?){
        if (savedInstanceState==null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchHomeFragment(), MatchHomeFragment::class.java.simpleName)
                    .commit()
        }
    }
}
