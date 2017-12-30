package org.vontech.rollout

import android.content.Context
import android.content.SharedPreferences

/**
 * A collection of preference getters and setters for the Rollout application
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class RolloutPrefs (context: Context) {

    val PREFS_FILENAME = "org.vontech.rollout.prefs"
    val ACCESS_TOKEN = "access_token"
    val APP_ACCESSED = "app_accessed"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var accessToken: String?
        get() = prefs.getString(ACCESS_TOKEN, null)
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    var appAccessed: Boolean
        get() = prefs.getBoolean(APP_ACCESSED, false)
        set(value) = prefs.edit().putBoolean(APP_ACCESSED, value).apply()

}