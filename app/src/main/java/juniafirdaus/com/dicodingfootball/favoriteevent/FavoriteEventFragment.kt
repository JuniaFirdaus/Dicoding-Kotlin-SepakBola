package juniafirdaus.com.dicodingfootball.favoriteevent

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.db.FavoriteMatch
import juniafirdaus.com.dicodingfootball.db.database
import juniafirdaus.com.dicodingfootball.detailevent.DetailActivityEvent
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteEventFragment : Fragment(), AnkoComponent<Context> {
    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteEventAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteEventAdapter(context, favoriteMatches){
            ctx.startActivity<DetailActivityEvent>(
                    "idHome" to it.homeId, "idAway" to it.teamId,
                    "goalHome" to it.scoreHome, "goalAway" to it.scoreAway,
                    "homeTeam" to it.homeTeam, "awayTeam" to it.awayTeam,
                    "dateMatch" to it.dateMatch,
                    "matchId" to it.eventId
            )
        }

        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favoriteMatches.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favoriteMatches.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                id = R.id.swipe
                setColorSchemeResources(android.R.color.holo_orange_dark,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                listEvent = recyclerView {
                    id = R.id.list_favorite
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}
