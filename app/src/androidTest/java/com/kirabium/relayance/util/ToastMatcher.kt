package com.kirabium.relayance.util

import android.view.View
import android.widget.Toast
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>() {

    override fun describeTo(description: Description?) {
        description?.appendText("is Toast")
    }

    override fun matchesSafely(root: Root?): Boolean {
        // Match Toast using root's window token properties.
        return root?.decorView?.javaClass?.simpleName == "Toast" // The decorView of a Toast window has this class name
    }

    companion object {
        fun withText(text: String): Matcher<Root> {
            return object : TypeSafeMatcher<Root>() {
                override fun matchesSafely(root: Root?): Boolean {
                    // Find the toast's message in the decorView
                    val toastView = root?.decorView?.findViewById<View>(android.R.id.message)
                    return toastView != null && toastView.toString().contains(text)
                }

                override fun describeTo(description: Description?) {
                    description?.appendText("Toast with text: $text")
                }
            }
        }
    }
}
