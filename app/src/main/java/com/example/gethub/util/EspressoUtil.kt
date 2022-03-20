package com.example.gethub.util

object EspressoUtil {
    fun increment() {
        EspressoIdlingResource.increment()
    }

    fun decrement() {
        if (!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
    }
}