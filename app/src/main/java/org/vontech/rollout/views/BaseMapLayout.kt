package org.vontech.rollout.views

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.view.View
import android.widget.RelativeLayout
import org.vontech.rollout.R
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.view.*

/**
 * A draggable ViewGroup (RelativeLayout) which is used in the MainActivity as a suggestion
 * panel that can be dragged into view from the bottom of the screen.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */

class BaseMapLayout(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private val AUTO_OPEN_SPEED_LIMIT = 800.0
    private var draggingState = 0
    private var draggingBorder: Int = 0
    private var verticalRange: Int = 0
    private var isOpen: Boolean = false
    private var dragHelper: ViewDragHelper? = null

    inner class DragHelperCallback : ViewDragHelper.Callback() {

        override fun onViewDragStateChanged(state: Int) {
            if (state == draggingState) { // no change
                return
            }
            if ((draggingState == ViewDragHelper.STATE_DRAGGING || draggingState == ViewDragHelper.STATE_SETTLING) &&
                    state == ViewDragHelper.STATE_IDLE) {
                // the view stopped from moving.

                if (draggingBorder == 0) {
                    onStopDraggingToClosed()
                } else if (draggingBorder == verticalRange) {
                    isOpen = true
                }
            }
            if (state == ViewDragHelper.STATE_DRAGGING) {
                onStartDragging()
            }
            draggingState = state
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            draggingBorder = top

            val progress = 1.0 - (top.toFloat() / verticalRange.toFloat())
            //suggestion_drawer_handle.text = ((255 * progress).toInt()).toString()

            suggestion_drawer_handle.setTextColor(suggestion_drawer_handle.textColors.withAlpha((255 * (1.0-progress)).toInt()))

            // What else should we do for this animation flow

        }

        override fun getViewVerticalDragRange(child: View?): Int {
            return verticalRange
        }

        override fun tryCaptureView(view: View, i: Int): Boolean {
            return view.id == R.id.suggestion_drawer
        }

        override fun clampViewPositionVertical(child: View?, top: Int, dy: Int): Int {
            val topBound = paddingTop
            val bottomBound = verticalRange
            return Math.min(Math.max(top, topBound), bottomBound)
        }

        override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
            val rangeToCheck = verticalRange
            if (draggingBorder == 0) {
                isOpen = false
                return
            }
            if (draggingBorder == rangeToCheck) {
                isOpen = true
                return
            }
            var settleToOpen = false
            if (yvel > AUTO_OPEN_SPEED_LIMIT) { // speed has priority over position
                settleToOpen = true
            } else if (yvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false
            } else if (draggingBorder > rangeToCheck / 2) {
                settleToOpen = true
            } else if (draggingBorder < rangeToCheck / 2) {
                settleToOpen = false
            }

            val settleDestY = if (settleToOpen) verticalRange else 0

            if (dragHelper!!.settleCapturedViewAt(0, settleDestY)) {
                ViewCompat.postInvalidateOnAnimation(this@BaseMapLayout)
            }
        }

    }

    init {
        isOpen = false
    }

    override fun onFinishInflate() {
        dragHelper = ViewDragHelper.create(this, 1.0f, DragHelperCallback())
        isOpen = false
        super.onFinishInflate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        verticalRange = (h * 1.00 - suggestion_drawer_handle.measuredHeight).toInt() //0.66
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun onStopDraggingToClosed() {
        // To be implemented
    }

    private fun onStartDragging() {

    }

    private fun isHandleTarget(event: MotionEvent): Boolean {
        val handleLocation = IntArray(2)
        suggestion_drawer_handle.getLocationOnScreen(handleLocation)
        val upperLimit = handleLocation[1] + suggestion_drawer_handle.getMeasuredHeight()
        val lowerLimit = handleLocation[1]
        val y = event.rawY.toInt()
        return y > lowerLimit && y < upperLimit
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isHandleTarget(event) && dragHelper!!.shouldInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isHandleTarget(event) || isMoving()) {
            dragHelper!!.processTouchEvent(event)
            return true
        } else {
            return super.onTouchEvent(event)
        }
    }

    override fun computeScroll() { // needed for automatic settling.
        if (dragHelper!!.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    fun isMoving(): Boolean {
        return draggingState == ViewDragHelper.STATE_DRAGGING || draggingState == ViewDragHelper.STATE_SETTLING
    }

    fun isOpen(): Boolean {
        return isOpen
    }

}