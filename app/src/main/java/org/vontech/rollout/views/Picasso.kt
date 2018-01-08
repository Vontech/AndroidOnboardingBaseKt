package org.vontech.rollout.views

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Some extensions and helpers for the Picasso library.
 * Adapted from http://www.pawegio.com/2015/07/29/using-picasso-in-kotlin/
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */

val Context.picasso: Picasso
    get() = Picasso.with(this)



fun ImageView.load(path: String, request: (RequestCreator) -> RequestCreator) {
    request(context.picasso.load(path)).into(this)
}

fun ImageView.loadUrl(url: String) {
    Picasso.with(this.context).load(url).into(this)
}

fun ImageView.loadUrlNoCache(url: String) {
    Picasso.with(this.context).load(url)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .into(this)
}