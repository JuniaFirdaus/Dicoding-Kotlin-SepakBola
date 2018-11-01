package juniafirdaus.com.dicodingfootball.search

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import juniafirdaus.com.dicodingfootball.R
import org.jetbrains.anko.sdk25.coroutines.onClick

class SearchEventAdapter(private val context: Context?, private var matchItems: List<EventItem>,
                         private val listener: (EventItem) -> Unit) : RecyclerView.Adapter<SearchEventAdapter.LastHolder>() {

    override fun onBindViewHolder(holder: LastHolder, position: Int) {
        holder.bindItem(matchItems[position], listener)
    }

    override fun getItemCount(): Int {
        return matchItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastHolder {
        return LastHolder(LayoutInflater.from(context).inflate(R.layout.activity_main_design, parent, false))
    }

    fun refresh(fill: List<EventItem>) {
        matchItems = fill
        notifyDataSetChanged()
    }

    class LastHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dateMatch = view.findViewById(R.id.txt_date) as TextView
        private val homeTeam = view.findViewById(R.id.txt_home) as TextView
        private val awayTeam = view.findViewById(R.id.txt_away) as TextView
        private val scoreTeam = view.findViewById(R.id.txt_score) as TextView

        @SuppressLint("SetTextI18n")
        fun bindItem(matchItem: EventItem, listener: (EventItem) -> Unit) {

            if (matchItem.intHomeScore == null) {
                if (matchItem.intAwayScore == null) {
                    scoreTeam.text = "" + ":" + ""
                }
            } else {
                scoreTeam.text = matchItem.intHomeScore.toString() + ":" + matchItem.intAwayScore
            }
            dateMatch.text = matchItem.strDate
            homeTeam.text = matchItem.strHomeTeam
            awayTeam.text = matchItem.strAwayTeam
            itemView.onClick { listener(matchItem) }
        }
    }
}