package juniafirdaus.com.dicodingfootball.playerteam


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.google.gson.Gson

import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.playerdetail.PlayerDetailActivity
import juniafirdaus.com.dicodingfootball.util.invisible
import juniafirdaus.com.dicodingfootball.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerFragment : Fragment(), AnkoComponent<Context>, PlayerView {

    private var teams: MutableList<PlayerItem> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var teamAdapter: PlayerAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        teamAdapter = PlayerAdapter(teams) {
            ctx.startActivity<PlayerDetailActivity>("idTeam" to "${it.idPlayer}")
        }

        val team = arguments?.getString("idTeam")
        val bundle = Bundle()
        val fragment1 = PlayerFragment()
        bundle.putString("idTeam", team.toString())
        fragment1.arguments = bundle
        listEvent.adapter = teamAdapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)
        team?.let {
            presenter.getPlayerDetail(it)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(android.R.color.holo_orange_dark,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.list_team
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayerTeam(data: List<PlayerItem>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        teamAdapter.notifyDataSetChanged()
    }
}