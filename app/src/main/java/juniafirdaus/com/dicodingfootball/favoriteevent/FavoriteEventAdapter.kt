package juniafirdaus.com.dicodingfootball.favoriteevent

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.db.FavoriteMatch
import org.jetbrains.anko.sdk25.coroutines.onClick


class FavoriteEventAdapter(private val context: Context?, private var favoriteMatch: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<FavoriteEventAdapter.FavoriteEventViewHolder>() {

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        holder.bindItem(favoriteMatch[position], listener)
    }

    override fun getItemCount(): Int {
        return favoriteMatch.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        return FavoriteEventViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_main_design, parent, false))
    }


    class FavoriteEventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dateMatch = view.findViewById(R.id.txt_date) as TextView
        private val homeTeam = view.findViewById(R.id.txt_home) as TextView
        private val awayTeam = view.findViewById(R.id.txt_away) as TextView
        private val scoreTeam = view.findViewById(R.id.txt_score) as TextView

        @SuppressLint("SetTextI18n")
        fun bindItem(favoriteMatch: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {

            dateMatch.text = favoriteMatch.dateMatch
            homeTeam.text = favoriteMatch.homeTeam
            awayTeam.text = favoriteMatch.awayTeam
            scoreTeam.text = favoriteMatch.scoreHome +" : "+ favoriteMatch.scoreAway
            itemView.onClick { listener(favoriteMatch) }

        }

    }
}