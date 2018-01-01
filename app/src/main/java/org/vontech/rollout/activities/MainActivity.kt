package org.vontech.rollout.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.vontech.rollout.*

/**
 * The main map-like activity of the Rollout application
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        decideWhereToGo()

        setContentView(R.layout.activity_main)

    }

    private fun decideWhereToGo() {

        //Log.e("DECIDER [APP-ACCESSED]", prefs.appAccessed.toString())
        //Log.e("DECIDER [ACCESS-TOKEN]", prefs.accessToken)

        // First, if never opened, or logged out, automatically go to the login screen
        if (!prefs.appAccessed || !auth.isAccessTokenAvailable()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()

        // Otherwise, start a process to check if the
        } else {

            RolloutAPI.validateUser { valid ->
                //Log.e("DECIDER [ACCESS-VALID]", valid.toString())
                if (!valid) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
            }

        }



    }
}
