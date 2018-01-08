package org.vontech.rollout.activities

import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_voting.*
import org.vontech.rollout.R
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener



/**
 * Facilitates the voting procedure for deciding where to meet
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class VotingActivity : AppCompatActivity() {

    private var optionButtons: Array<TextView> = emptyArray()
    private var selectedButton: TextView? = null
    private var textAnimationInProgress: Boolean = false
    private var selectedColor : Int = 0
    private var unselectedColor : Int = 0
    private val textAnimationTime = 250L // in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting)

        // Instantiate some variables
        selectedColor = resources.getColor(R.color.voting_selected_color)
        unselectedColor = resources.getColor(R.color.voting_unselected_color)

        // Modify the time picker
        optionButtons = arrayOf(
                bar_option,
                book_option,
                cafe_option,
                library_option,
                club_option,
                park_option,
                restaurant_option,
                school_option,
                mall_option,
                university_option
        )

        for (button in optionButtons) {

            button.setOnClickListener {

                // If button is already selected, do nothing
                // Otherwise, start animation
                if (button != selectedButton && !textAnimationInProgress) {

                    textAnimationInProgress = true
                    val colorSelectAnimation = ValueAnimator.ofObject(ArgbEvaluator(), unselectedColor, selectedColor)
                    val colorUnselectAnimation = ValueAnimator.ofObject(ArgbEvaluator(), selectedColor, unselectedColor)
                    colorSelectAnimation.duration = textAnimationTime
                    colorUnselectAnimation.duration = textAnimationTime

                    colorSelectAnimation.addUpdateListener {
                        animator -> button.setTextColor(animator.animatedValue as Int)
                        if (animator.animatedFraction == 1.0f) {
                            selectedButton = button
                            textAnimationInProgress = false
                        }
                    }
                    if (selectedButton != null) {
                        colorUnselectAnimation.addUpdateListener {
                            animator -> selectedButton!!.setTextColor(animator.animatedValue as Int)
                        }
                        colorUnselectAnimation.start()
                    }
                    colorSelectAnimation.start()

                }

            }

        }

    }

}
