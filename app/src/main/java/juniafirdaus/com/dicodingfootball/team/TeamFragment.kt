package juniafirdaus.com.dicodingfootball.team

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.BuildConfig
import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.R.array.league
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.model.Team
import juniafirdaus.com.dicodingfootball.playerteam.PlayerFragment
import juniafirdaus.com.dicodingfootball.team.teamdetail.TeamDetailActivity
import juniafirdaus.com.dicodingfootball.util.invisible
import juniafirdaus.com.dicodingfootball.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamFragment : Fragment(), AnkoComponent<Context>, MainView {

    private var team: MutableList<Team> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var teamAdapter: MainAdapter
    private lateinit var spinner: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.support_simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
        teamAdapter = MainAdapter(team) {
            ctx.startActivity<TeamDetailActivity>("idTeam" to "${it.teamId}")
        }

        val bundle = Bundle()
        val fragment1 = PlayerFragment()
        bundle.putString("idTeam", id.toString())
        fragment1.arguments = bundle
        listEvent.adapter = teamAdapter


        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(android.R.color.holo_orange_dark,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.list_team
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
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

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        team.clear()
        team.addAll(data)
        teamAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_menu, menu)
        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?
        searchView?.queryHint = "Search team"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.getSearchTeam(newText)
                return false
            }
        })

        searchView?.setOnCloseListener {
            presenter.getTeamList(BuildConfig.EPL   )
            true
        }
    }
}