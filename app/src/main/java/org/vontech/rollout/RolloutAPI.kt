package org.vontech.rollout

import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.getAs

/**
 * A file which contains all relevant data structures and methods for interacting with the
 * Rollout API.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class RolloutAPI {

    companion object {

        const val ROOT_URL = "https://rolloutserver.herokuapp.com"

        const val REGISTER_USER_ENDPOINT = "/api/users"

        /**
         * Registers the user given the requested parameters.
         * @param name The user's full name
         * @param email The user's full email address
         * @param phone The user's full phone number
         * @param password The user's full password
         * @param callback The codeblock to call once finished (should take in an error String?)
         */
        fun registerUser(name: String, email: String, phone: String, password: String, callback: (error: String?) -> Unit) {

            // Construct the appropriate endpoint
            val endpoint = ROOT_URL + REGISTER_USER_ENDPOINT

            // Construct the parameter body for the POST
            val params = listOf("name" to name, "email" to email,
                                "phone" to phone, "password" to password)

            // Make the call
            Fuel.post(endpoint, params).responseJson( { _, _, result ->
                //make a GET to http://httpbin.org/get and do something with response

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

    }

}