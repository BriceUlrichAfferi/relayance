package com.kirabium.relayance.util

import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.test.espresso.Espresso
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>() {
    private var currentToast: Toast? = null

    override fun describeTo(description: Description) {
        description.appendText("is toast with text: ")
    }

    override fun matchesSafely(root: Root): Boolean {
        val windowType = root.windowLayoutParams.get()?.type
        if (windowType == WindowManager.LayoutParams.TYPE_TOAST) {
            val toast = root.decorView.rootView
            if (toast is Toast) {
                currentToast = toast
                return true
            }
        }
        return false
    }

    companion object {
        fun hasToast() = ToastMatcher()
    }
}