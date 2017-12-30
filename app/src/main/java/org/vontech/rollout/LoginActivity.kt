package org.vontech.rollout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yarolegovich.lovelydialog.LovelyInfoDialog
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Activity for logging into the Rollout service
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            login(login_email_edit.text.toString(), login_password_edit.text.toString())
        }

        register_login_button.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("fromLogin", true)
            startActivity(intent)
        }

    }

    /**
     * Attempts to login the user after registration occurs
     * @param email The email to login with
     * @param password The password for this email
     */
    private fun login(email: String, password: String) {

        RolloutAPI.authenticateUser(email, password, {error ->

            if (error == null) {

                // Login successful; start main activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                this.finish()

            } else {
                LovelyInfoDialog(this)
                        .setTopColorRes(R.color.registration_error_dialog_top)
                        .setIcon(R.drawable.ic_error_outline_white_36dp)
                        .setTitle(R.string.login_error_title)
                        .setMessage(error)
                        .setConfirmButtonText(R.string.dialog_ok)
                        .show()
            }
        })

    }

    /**
     * TODO: Implement a function which starts a forgot password process
     */
    private fun forgotPassword() {
        throw RuntimeException("Forgot password is not yet implemented")
    }

}
