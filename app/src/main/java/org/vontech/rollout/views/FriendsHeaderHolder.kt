package org.vontech.rollout.views

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_friend_header.view.*

/**
 * A RecyclerView.Holder to be used as headers for the Create Group and friend search screens. Uses
 * the view_friend_header layout file.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class FriendsHeaderHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v

    fun bindEntry(header: String) {
        view.section_header_label.text = header
    }

}