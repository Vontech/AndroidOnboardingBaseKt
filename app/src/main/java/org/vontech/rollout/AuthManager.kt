package org.vontech.rollout

/**
 * Manages the authentication information of a user.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class AuthManager() {

    fun isAccessTokenAvailable() : Boolean {
        return prefs.accessToken != null
    }

    fun saveAccessToken(token: String) {
        prefs.accessToken = token
    }

    fun revokeAccessToken() {
        prefs.accessToken = null
    }

    fun getAccessToken() : String? {
        return prefs.accessToken
    }

}
