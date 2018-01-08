package org.vontech.rollout.activities

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A collection of useful extensions for use in activities and other context-enabled stuff
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}