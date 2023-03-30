package org.dicoding.githubuser.ui

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.dicoding.githubuser.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val dummySearch = "dicoding"
    private val dummySearch2 = "ilham"

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testSearchActionBar() {
        // Click on the search icon to open the search view
        onView(withId(R.id.search)).perform(click())

        // Type the search query and press the enter key
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(
            typeText(dummySearch),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )

        // Verify that the search results are displayed on the screen
        onView(withText(dummySearch)).check(matches(isDisplayed()))

        // Click on the X button to clear the search query
        onView(withId(androidx.appcompat.R.id.search_close_btn)).perform(click())

        // Type another search query and press the enter key
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(
            typeText(dummySearch2),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )

        // Verify that the search results are displayed on the screen
        onView(withText(dummySearch2)).check(matches(isDisplayed()))
    }


}