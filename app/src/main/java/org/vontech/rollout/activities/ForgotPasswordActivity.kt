package org.vontech.rollout.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.text.InputType
import android.view.View
import com.yarolegovich.lovelydialog.LovelyInfoDialog
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.vontech.rollout.R

/**
 * Activity which provides a flow for resetting the user's password
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class ForgotPasswordActivity : AppCompatActivity() {

    // TODO: inputType

    /**
     *  RESET PASSWORD FLOW
     *
     *      SKIP TO STEP 9 IF INTENT FILTER USED TO START ACTIVITY
     *
     *      1)  User comes to the reset password activity
     *      2)  User presented with screen for inserting existing email
     *      3)  User enters email and clicks submit
     *      4)  Query made to server with given email
     *           - If email does not exist, tell user about the error
     *           - Else, return success
     *      5)  Check email screen shown
     *      6)  Server generates reset URL with ?id = Base64(email:special_hash), sends in email
     *      7)  User receives email with link, user clicks link
     *      8)  Intent filter used to capture link
     *
     *      9)  Grab id from intent filter result
     *      10) Decode from base 64 to get email and hash
     *      11) Display reset components of screen
     *      12) User enters new password
     *      13) Send server email, new password, and validation hash from decoding
     *      14) If everything good, send back 200 and display success, otherwise say error
     *
     *  RELEVANT VIEWS
     *
     *      - .forgot_title
     *      - .forgot_content
     *      - .forgot_first_label
     *      - .forgot_first_edit
     *      - .forgot_button
     *
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // TODO: Parse the intent
        showInsertEmailScreen()

    }

    private fun decideState() {

    }

    /**
     * Resets the components of the screen to a usable state for each step
     */
    private fun resetScreen() {

        forgot_first_label.visibility = View.VISIBLE
        forgot_first_edit.visibility = View.VISIBLE

    }

    /**
     * Configures the screen for the state in steps 1 - 4
     */
    private fun showInsertEmailScreen() {

        resetScreen()
        forgot_title.setText(R.string.forgot_title)
        forgot_content.setText(R.string.forgot_content)
        forgot_first_label.setText(R.string.forgot_edit_label)
        forgot_first_edit.setHint(R.string.forgot_edit_hint)
        forgot_first_edit.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        forgot_button.setText(R.string.forgot_edit_submit)
        forgot_button.setOnClickListener {
            showEmailCheckScreen() // TODO
        }

    }

    /**
     * A process which kicks off the reset password HTTP request, and continues the flow
     */
    private fun submitEmailForReset() {

    }

    private fun showEmailCheckScreen() {

        resetScreen()
        forgot_title.setText(R.string.forgot_check_title)
        forgot_content.setText(R.string.forgot_check_content)
        forgot_first_label.visibility = View.GONE
        forgot_first_edit.visibility = View.GONE
        forgot_button.setText(R.string.forgot_wait_button)
        forgot_button.setOnClickListener {
            displayResetScreen() // TODO
        }

    }

    private fun parseIntentRequest() {

    }

    private fun displayResetScreen() {

        resetScreen()
        forgot_title.setText(R.string.forgot_reset_title)
        forgot_content.setText(R.string.forgot_reset_content)
        forgot_first_label.setText(R.string.forgot_reset_edit_label)
        forgot_first_edit.setHint(R.string.forgot_reset_edit_hint)
        forgot_first_edit.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        forgot_button.setText(R.string.forgot_reset_confirm_button)
        forgot_button.setOnClickListener {
            displayFinishScreen() // TODO
        }

    }

    private fun finalResetPasswordRequest() {

    }

    private fun displayFinishScreen() {

        resetScreen()
        forgot_title.setText(R.string.forgot_reset_title)
        forgot_content.setText(R.string.forgot_reset_success)
        forgot_first_label.visibility = View.GONE
        forgot_first_edit.visibility = View.GONE
        forgot_button.setText(R.string.forgot_proceed_success)
        forgot_button.setOnClickListener {
            showInsertEmailScreen() // TODO
        }

    }

    /**
     * Displays an error dialog with the given message
     * @param error The error to display, as a string resource
     */
    private fun showEmailResetError(@StringRes error: Int) {
        LovelyInfoDialog(this)
                .setTopColorRes(R.color.registration_error_dialog_top)
                .setIcon(R.drawable.ic_error_outline_white_36dp)
                .setTitle(R.string.forgot_password_error_title)
                .setMessage(error)
                .setConfirmButtonText(R.string.dialog_ok)
                .show()
    }

}
