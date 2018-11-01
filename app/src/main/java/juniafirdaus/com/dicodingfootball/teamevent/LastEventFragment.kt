package juniafirdaus.com.dicodingfootball.teamevent


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import juniafirdaus.com.dicodingfootball.detailevent.DetailActivityEvent
import juniafirdaus.com.dicodingfootball.model.EventsItem
import com.google.gson.Gson
import juniafirdaus.com.dicodingfootball.BuildConfig
import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import kotlinx.android.synthetic.main.fragment_last_match.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx


class LastEventFragment : Fragment(), EventView {


    private var teams: MutableList<EventsItem> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var mainAdapter: EventAdapter
    private lateinit var matchEventPresenter: EventPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var leagueName: String


    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun showTeamList(data: List<EventsItem>) {
       mainAdapter.refresh(data)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerLast.adapter = spinnerAdapter

        spinnerLast.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinnerLast.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> matchEventPresenter.getLastEvent(BuildConfig.EPL)
                    "German Bundesliga" -> matchEventPresenter.getLastEvent(BuildConfig.BUNDESLIGA)
                    "Italian Serie A" -> matchEventPresenter.getLastEvent(BuildConfig.SERI_A)
                    "French Ligue 1" -> matchEventPresenter.getLastEvent(BuildConfig.FRENCE_LEAGUE)
                    "Spanish La Liga" -> matchEventPresenter.getLastEvent(BuildConfig.LALIGA)
                    "Netherlands Eredivisie" -> matchEventPresenter.getLastEvent(BuildConfig.Eredivisie)
                    else -> matchEventPresenter.getLastEvent(BuildConfig.EPL)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = inflater.inflate(R.layout.fragment_last_match, container, false)

        progressBar = views?.findViewById(R.id.prograss) as ProgressBar
        recyclerView = views.findViewById(R.id.rcv_last) as RecyclerView


        views.let {

            mainAdapter = EventAdapter(context, teams) {
                ctx.startActivity<DetailActivityEvent>(
                        "idHome" to it.idHomeTeam,
                        "idAway" to it.idAwayTeam,
                        "goalHome" to it.intHomeScore,
                        "goalAway" to it.intAwayScore,
                        "homeTeam" to it.strHomeTeam,
                        "awayTeam" to it.strAwayTeam,
                        "dateMatch" to it.strDate,
                        "matchId" to it.idEvent
                )
            }

            recyclerView.layoutManager = LinearLayoutManager(ctx)
            recyclerView.adapter = mainAdapter
            matchEventPresenter = EventPresenter(this, ApiRepository(), Gson())
            matchEventPresenter.getLastEvent(BuildConfig.EPL)
        }

        return views
    }

    }

