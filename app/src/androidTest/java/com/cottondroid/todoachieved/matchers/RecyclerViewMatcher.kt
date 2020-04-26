package com.cottondroid.todoachieved.matchers

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * Created by dannyroa on 5/10/15.
 */
class RecyclerViewMatcher(private val recyclerViewId: Int) {
    fun atPosition(position: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            private lateinit var resources: Resources

            override fun describeTo(description: Description) {
                val idDescription = resources.getResourceName(recyclerViewId)
                        ?: recyclerViewId.toString()
                description.appendText("with id: $idDescription")
            }

            public override fun matchesSafely(view: View): Boolean {
                resources = view.resources
                val recyclerView: RecyclerView = view.rootView.findViewById<View>(recyclerViewId) as RecyclerView
                return view === if (recyclerView.id == recyclerViewId) {
                    recyclerView.findViewHolderForAdapterPosition(position)?.itemView
                } else {
                    return false
                }
            }
        }
    }
}