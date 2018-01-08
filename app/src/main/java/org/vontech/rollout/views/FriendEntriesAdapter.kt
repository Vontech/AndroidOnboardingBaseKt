package org.vontech.rollout.views

import android.app.Activity
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.view_friend_entry.view.*
import org.jetbrains.anko.*
import org.vontech.rollout.R
import org.vontech.rollout.activities.inflate
import org.vontech.rollout.domain.FriendEntry

/**
 * A RecyclerView adapter that is used in the group creation and friend lookup screens
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */

class FriendEntriesAdapter(private var friends: List<FriendEntry>, private val activity: Activity) :
        RecyclerView.Adapter<FriendEntriesAdapter.FriendEntryHolder>() {

    init {
        this.friends = this.friends.sortedWith(compareBy({ it.name })).toList()
    }

    override fun getItemCount() = friends.size

    override fun onBindViewHolder(holder: FriendEntriesAdapter.FriendEntryHolder, position: Int) {
        val friendEntry = friends[position]
        holder.bindEntry(position, friendEntry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            FriendEntriesAdapter.FriendEntryHolder {

        val layout = parent.inflate(R.layout.view_friend_entry)
        return FriendEntryHolder(layout)

    }

    inner class FriendEntryHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var entry: FriendEntry? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun bindEntry(position: Int, entry: FriendEntry) {
            this.entry = entry

            val name = entry.name
            val image = entry.profileURI
            val online = entry.online
            val firstLetter = if (position == 0 || friends[position - 1].name[0] != name[0]) name[0].toString() else ""

            view.friend_entry_name.text = name
            view.friend_entry_profile_image.loadUrlNoCache(image)
            view.friend_entry_online_indicator.visibility = if (online) View.VISIBLE else View.GONE
            view.friend_entry_optional_letter_label.text = firstLetter

        }

    }

}