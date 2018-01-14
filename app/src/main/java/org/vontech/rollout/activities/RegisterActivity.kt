package org.vontech.rollout.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yarolegovich.lovelydialog.LovelyInfoDialog
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import kotlinx.android.synthetic.main.activity_register.*
import org.vontech.rollout.R
import org.vontech.rollout.RolloutAPI
import java.util.regex.Pattern
import org.jetbrains.anko.*

/**
 * The registration activity for making a user account on the Rollout platform.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        // Show the "Back to Login" button if from login
        if (!intent.extras.getBoolean("fromLogin", false)) {
            goto_login_button.visibility = View.GONE
        }

        goto_login_button.setOnClickListener {
            this.finish()
        }

        register_button.setOnClickListener {
            registerUser()
        }

    }

    /**
     * Starts the user registration process by validating and making a POST to the server.
     * If the request fails, or if the user already exists (based on email), then the user
     * will be notified.
     */
    private fun registerUser() {

        // Validate name
        var errored = false
        val name = registration_name_edit.text.toString().trim()
        if (name.equals("")) {
            registration_name_edit.error = getString(R.string.registration_name_error)
            errored = true
        }

        // Validate email
        val email = registration_email_edit.text.toString().trim()
        if (!isEmailValid(email)) {
            registration_email_edit.error = getString(R.string.registration_email_error)
            errored = true
        }

        // Validate phone
        var phone = registration_phone_edit.text.toString().trim()
        phone = phone.replace("[^0-9]".toRegex(), "")
        if (phone.equals("")) {
            registration_phone_edit.error = getString(R.string.registration_phone_error)
            errored = true
        }

        // Validate password
        val password = registration_password_edit.text.toString().trim()
        if (password.length < 8) {
            registration_password_edit.error = getString(R.string.registration_password_error)
            errored = true
        }

        if (errored) {
            return
        }

        // If no error was encountered, start the user creation process
        RolloutAPI.registerUser(name, email, phone, password, { error ->

            if (error == null) {

                // Registration was successful; attempt login
                login(email, password)

            } else {
                LovelyInfoDialog(this)
                        .setTopColorRes(R.color.registration_error_dialog_top)
                        .setIcon(R.drawable.ic_error_outline_white_36dp)
                        .setTitle(R.string.registration_error_title)
                        .setMessage(error)
                        .setConfirmButtonText(R.string.dialog_ok)
                        .show()
            }
        })

    }

    /**
     * Attempts to login the user after registration occurs
     * @param email The email to login with
     * @param password The password for this email
     */
    private fun login(email: String, password: String) {

        RolloutAPI.authenticateUser(email, password, { error ->

            if (error == null) {

                // Login successful; start main activity
                startActivity<MainActivity>()
                this.finish()

            } else {
                LovelyStandardDialog(this)
                        .setTopColorRes(R.color.registration_error_dialog_top)
                        .setIcon(R.drawable.ic_error_outline_white_36dp)
                        .setTitle(R.string.registration_error_title)
                        .setMessage(R.string.registration_login_error)
                        .setPositiveButton(R.string.dialog_ok, {
                            startActivity<LoginActivity>()
                            this.finish()
                        })
                        .show()
            }
        })

    }


    /**
     * Email validation regex, provided by from the following GitHub gist:
     *      https://gist.github.com/ironic-name/f8e8479c76e80d470cacd91001e7b45b
     * @param email The email to validate
     * @return true if the given string is an email, false otherwise.
     */
    private fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
                "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
        ).matcher(email).matches()
    }
}
