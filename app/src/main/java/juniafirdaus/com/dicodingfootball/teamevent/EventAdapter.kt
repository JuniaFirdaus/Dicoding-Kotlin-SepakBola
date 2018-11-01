package juniafirdaus.com.dicodingfootball.teamevent

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.model.EventsItem
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private val context: Context?, private var matchItems: List<EventsItem>, private val listener: (EventsItem) -> Unit)
    : RecyclerView.Adapter<EventAdapter.LastHolder>() {

    override fun onBindViewHolder(holder: LastHolder, position: Int) {
        holder.bindItem(matchItems[position], listener)
    }

    override fun getItemCount(): Int {
        return matchItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastHolder {
        return LastHolder(LayoutInflater.from(context).inflate(R.layout.activity_main_design, parent, false))
    }

    fun refresh(fill: List<EventsItem>) {
        matchItems = fill
        notifyDataSetChanged()
    }

    class LastHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dateMatch = view.findViewById(R.id.txt_date) as TextView
        private val homeTeam = view.findViewById(R.id.txt_home) as TextView
        private val awayTeam = view.findViewById(R.id.txt_away) as TextView
        private val scoreTeam = view.findViewById(R.id.txt_score) as TextView

        @SuppressLint("SetTextI18n")
        fun bindItem(matchItem: EventsItem, listener: (EventsItem) -> Unit) {

            if (TextUtils.isEmpty(matchItem.intHomeScore)) {
                if (TextUtils.isEmpty(matchItem.intAwayScore)) {
                    scoreTeam.text = "" + ":" + ""
                }
            } else {
                scoreTeam.text = matchItem.intHomeScore + ":" + matchItem.intAwayScore
            }
            dateMatch.text = matchItem.strDate.toString()
            homeTeam.text = matchItem.strHomeTeam
            awayTeam.text = matchItem.strAwayTeam
            itemView.onClick { listener(matchItem) }

        }

    }


}

