package juniafirdaus.com.dicodingfootball.home

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*

import juniafirdaus.com.dicodingfootball.R
import juniafirdaus.com.dicodingfootball.adapter.ViewPagerAdapter
import juniafirdaus.com.dicodingfootball.favoriteevent.FavoriteEventFragment
import juniafirdaus.com.dicodingfootball.favoriteteam.FavoriteTeamsFragment


class FavoriteHomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vPager = view.findViewById<ViewPager>(R.id.viewpager)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        val adapter = ViewPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
        adapter.populateFragment(FavoriteTeamsFragment(), "Team Favorite")
        adapter.populateFragment(FavoriteEventFragment(), "Match Favorite")
        vPager.adapter = adapter
        tabs.setupWithViewPager(vPager)
    }
}
