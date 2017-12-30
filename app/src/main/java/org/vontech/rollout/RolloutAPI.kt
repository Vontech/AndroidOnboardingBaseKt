package org.vontech.rollout

import android.content.res.Resources
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson

/**
 * A file which contains all relevant data structures and methods for interacting with the
 * Rollout API.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class RolloutAPI {

    companion object {

        const val ROOT_URL = "https://rolloutserver.herokuapp.com"
        const val BEARER_TOKEN = "cm9sbG91dDpKQlVZOVZFNjkyNDNCWUM5MDI0Mzg3SEdWWTNBUUZL"

        const val REGISTER_USER_ENDPOINT = "/api/users"
        const val AUTHENTICATE_USER_ENDPOINT = "/oauth/token/"
        const val VALIDATE_USER_ENDPOINT = "/oauth/validate/"

        /**
         * Registers the user given the requested parameters.
         * @param name The user's full name
         * @param email The user's full email address
         * @param phone The user's full phone number
         * @param password The user's full password
         * @param callback The code block to execute once finished (should take in an error String?)
         */
        fun registerUser(name: String, email: String, phone: String, password: String, callback: (error: String?) -> Unit) {

            // Construct the appropriate endpoint
            val endpoint = ROOT_URL + REGISTER_USER_ENDPOINT

            // Construct the parameter body for the POST
            val params = listOf("name" to name, "email" to email,
                                "phone" to phone, "password" to password)

            // Make the call
            Fuel.post(endpoint, params).responseJson( { _, _, result ->

                val (_, error) = result
                if (error == null) {
                    val jsonResult = result.get().obj()
                    if (!jsonResult.get("status").equals("success")) {
                        callback(jsonResult.getString("message"))
                    } else {
                        callback(null)
                    }
                } else {
                    // Error handling
                    callback(Resources.getSystem().getString(R.string.unknown_server_error))
                }
            })

        }

        /**
         * Sends a request to obtain an accessToken from the server. Note that this will
         * automatically save the accessToken.
         * @param email The email of the user who is attempting to login
         * @param password The password of this user
         * @param callback The code block to execute once finished (should take in an error String?)
         */
        fun authenticateUser(email: String,
                             password: String,
                             callback: (error: String?) -> Unit) {

            // Construct the appropriate endpoint
            val endpoint = ROOT_URL + AUTHENTICATE_USER_ENDPOINT

            // Construct the parameter body for the POST
            val params = listOf("username" to email, "password" to password,
                                "grant_type" to "password")

            // Make the call
            Fuel.post(endpoint, params)
                    .header("Authorization" to "Basic $BEARER_TOKEN")
                    .responseJson( { _, _, result ->

                val (_, error) = result
                if (error == null) {
                    val jsonResult = result.get().obj()
                    if (jsonResult.has("access_token")) {
                        prefs.appAccessed = true // Indicate that the
                        auth.saveAccessToken(jsonResult.getString("access_token"))
                        callback(null)
                    } else {
                        callback(Resources.getSystem().getString(R.string.login_error_content))
                    }
                } else {
                    // Error handling
                    callback(Resources.getSystem().getString(R.string.login_error_content))
                }
            })

        }

        /**
         * Sends a request to validate an accessToken from the server.
         * @param callback The code block to execute once finished (should take in a boolean)
         */
        fun validateUser(callback: (valid: Boolean) -> Unit) {

            // Construct the appropriate endpoint
            val endpoint = ROOT_URL + VALIDATE_USER_ENDPOINT

            // Make the call
            Fuel.get(endpoint)
                    .header("Authorization" to "Bearer ${prefs.accessToken}")
                    .responseJson( { _, _, result ->

                        val (_, error) = result
                        if (error == null) {
                            val jsonResult = result.get().obj()
                            if (jsonResult.has("authorized")) {
                                callback(jsonResult.getBoolean("authorized"))
                            } else {
                                callback(false)
                            }
                        } else {
                            // Error handling
                            Log.e("VALIDATE ERROR", error.message.toString())
                            callback(false)
                        }
                    })

        }

    }

}