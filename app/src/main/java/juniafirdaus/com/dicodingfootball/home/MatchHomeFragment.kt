package juniafirdaus.com.dicodingfootball.home


import android.view.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import juniafirdaus.com.dicodingfootball.R
import android.support.design.widget.TabLayout
import juniafirdaus.com.dicodingfootball.search.SearchFragment
import juniafirdaus.com.dicodingfootball.adapter.ViewPagerAdapter
import juniafirdaus.com.dicodingfootball.teamevent.LastEventFragment
import juniafirdaus.com.dicodingfootball.teamevent.NextEventFragment

class MatchHomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager>(R.id.viewpager)
        val viewTabs = view.findViewById<TabLayout>(R.id.tabs)
        val adapter = ViewPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
        adapter.populateFragment(LastEventFragment(), "Last Match")
        adapter.populateFragment(NextEventFragment(), "Next Match")
        viewPager.adapter = adapter
        viewTabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                true
            }
            R.id.actionSearch -> {
                val searchFragment = SearchFragment()
                fragmentManager?.beginTransaction()?.replace(R.id.main_container, searchFragment,
                        SearchFragment::class.java.simpleName)?.addToBackStack(null)?.commit()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
