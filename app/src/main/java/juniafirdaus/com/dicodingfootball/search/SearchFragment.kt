package juniafirdaus.com.dicodingfootball.search


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.BuildConfig

import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.detailevent.DetailActivityEvent
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx

class SearchFragment : Fragment(), SearchEventView {

    private var teams: MutableList<EventItem> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var mainAdapter: SearchEventAdapter
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var recyclerView: RecyclerView

    override fun showSearchEvent(data: List<EventItem>) {
        teams.clear()
        teams.addAll(data)
        mainAdapter.refresh(data)
     }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = inflater.inflate(R.layout.fragment_last_match, container, false)
        setHasOptionsMenu(true)

        progressBar = views?.findViewById(R.id.prograss) as ProgressBar
        recyclerView = views.findViewById(R.id.rcv_last) as RecyclerView

        views.let { it ->

            mainAdapter = SearchEventAdapter(context, teams) {
                ctx.startActivity<DetailActivityEvent>(
                        "idHome" to it.idHomeTeam, "idAway" to it.idAwayTeam,
                        "goalHome" to it.intHomeScore, "goalAway" to it.intAwayScore,
                        "homeTeam" to it.strHomeTeam, "awayTeam" to it.strAwayTeam,
                        "dateMatch" to it.strDate, "matchId" to it.idEvent, "dateMatch" to it.dateEvent
                )
            }

            recyclerView.layoutManager = LinearLayoutManager(ctx)
            recyclerView.adapter = mainAdapter
            searchPresenter = SearchPresenter(this, ApiRepository(), Gson())
        }

        return views
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_menu, menu)
        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?
        searchPresenter.getEventSearch("Arsenal")
        searchView?.queryHint = "Search team"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchPresenter.getEventSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })

        searchView?.setOnCloseListener { true }
    }
}

