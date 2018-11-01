package juniafirdaus.com.dicodingfootball.playerteam


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import juniafirdaus.com.dicodingfootball.R.color.colorAccent
import juniafirdaus.com.dicodingfootball.R.color.colorPrimaryText
import juniafirdaus.com.dicodingfootball.api.ApiRepository
import juniafirdaus.com.dicodingfootball.model.Team
import juniafirdaus.com.dicodingfootball.team.teamdetail.TeamDetailPresenter
import juniafirdaus.com.dicodingfootball.team.teamdetail.TeamDetailView
import juniafirdaus.com.dicodingfootball.util.invisible
import juniafirdaus.com.dicodingfootball.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerOverview : Fragment(),AnkoComponent<Context>, TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val team = arguments?.getString("idTeam")
        val bundle = Bundle()
        val fragment1 = PlayerOverview()
        bundle.putString("idTeam", team.toString())
        fragment1.arguments = bundle

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        team?.let {
            presenter.getTeamDetail(it)

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
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            teamBadge = imageView {}.lparams(height = dip(75))

                            teamName = textView {
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context, colorAccent)
                            }.lparams {
                                topMargin = dip(5)
                            }

                            teamFormedYear = textView {
                                this.gravity = Gravity.CENTER
                            }

                            teamStadium = textView {
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, colorPrimaryText)
                            }

                            teamDescription = textView().lparams {
                                topMargin = dip(20)
                            }
                        }
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }
    }

    override fun showTeamDetail(team: List<Team>) {
        swipeRefresh.isRefreshing = false
        Picasso.get().load(team[0].teamBadge).into(teamBadge)
        teamName.text = team[0].teamName
        teamDescription.text = team[0].teamDescription
        teamFormedYear.text = team[0].teamFormedYear
        teamStadium.text = team[0].teamStadium
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

}