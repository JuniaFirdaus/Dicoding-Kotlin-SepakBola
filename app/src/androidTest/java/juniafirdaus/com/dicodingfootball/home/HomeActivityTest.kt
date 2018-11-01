package juniafirdaus.com.dicodingfootball.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import juniafirdaus.com.dicodingfootball.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.action.ViewActions.swipeDown


@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)


    @Test
    fun testAppBehaviour() {
        //Runing app pertama mencari team dari Seria A melalui spinner
        Thread.sleep(5000)
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText("Italian Serie A")).perform(click())
                .check(matches(isDisplayed()))
        Thread.sleep(5000)

        //kemudian klik team berdasarkan posisi
        onView(withId(list_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        onView(withId(list_team)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        Thread.sleep(5000)
        pressBack()

        //pindah ke menu match
        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(event)).perform(click())
        onView(withId(rcv_last))
                .check(matches(isDisplayed()))

        //pilih pertandingan sesuai posisi
        onView(withId(rcv_last)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))
        Thread.sleep(5000)

        //detail pertandingan yang dipilih
        onView(withId(rcv_last)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(9, click()))
        Thread.sleep(5000)

        //kemudian simpan sebagai favorite
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        pressBack()

        //pindah tablayout ke next match
        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(event)).perform(click())
        Thread.sleep(5000)

        onView(withText("NEXT MATCH"))
                .check(matches(isDisplayed()))
        onView(withText("NEXT MATCH")).perform(click())
        Thread.sleep(5000)
        onView(withId(rcv_next))
                .check(matches(isDisplayed()))

        //klik bedasarkan posisi yang dipilih
        onView(withId(rcv_next)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        // detail pertandingan yang dipilih
        onView(withId(rcv_next)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        Thread.sleep(5000)
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        //kemudian tandai sebagai favorite pertandingan yang dipilih
        onView(withId(add_to_favorite)).perform(click())
        pressBack()

        //back pindah ke menu favorite
        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(favorites)).perform(click())
        Thread.sleep(5000)

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(favorites)).perform(click())
        Thread.sleep(5000)

        //selesai

    }

}
