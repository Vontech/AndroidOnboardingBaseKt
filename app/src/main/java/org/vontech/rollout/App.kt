package org.vontech.rollout

import android.app.Application

/**
 * An application class for the Rollout Application
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */

// A global prefs variable for accessing stored preferences
val prefs: RolloutPrefs by lazy {
    App.prefs!!
}

// A global auth variable for handling authentication
val auth: AuthManager by lazy {
    App.auth!!
}

class App : Application() {
    companion object {
        var prefs: RolloutPrefs? = null
        var auth: AuthManager? = null
    }

    override fun onCreate() {
        prefs = RolloutPrefs(applicationContext)
        auth = AuthManager()
        super.onCreate()
    }
}