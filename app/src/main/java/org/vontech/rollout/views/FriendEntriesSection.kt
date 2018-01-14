package org.vontech.rollout.views

import android.content.Context
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_friend_entry.view.*
import org.vontech.rollout.R
import org.vontech.rollout.domain.FriendEntry
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import org.vontech.rollout.domain.SortedListCallback

/**
 * A RecyclerView adapter that is used in the group creation and friend lookup screens
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class FriendEntriesSection(private var friends: List<FriendEntry>, private var context: Context, private var sorter: SortedListCallback<FriendEntry>) :
        FilteredGroupSection<FriendEntry>(SectionParameters.Builder(R.layout.view_friend_entry)
                            .headerResourceId(R.layout.view_friend_header)
                            .build()) {

    private var sortedFriends: SortedList<FriendEntry>

    init {
        // Use a SortedList for nice filtering
        this.sortedFriends = SortedList<FriendEntry>(FriendEntry::class.java, this.sorter)
        this.sortedFriends.addAll( this.friends.sortedWith(compareBy({ it.getSimpleName() })).toList())
    }

    override fun getContentItemsTotal(): Int {
        return this.sortedFriends.size() // number of items of this section
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        // return a custom instance of ViewHolder for the items of this section
        return FriendEntryHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return FriendsHeaderHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val headerHolder = holder as FriendsHeaderHolder
        headerHolder.bindEntry(context.getString(R.string.group_creation_contacts))
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as FriendEntryHolder
        itemHolder.bindEntry(position, this.sortedFriends.get(position))
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
            val firstLetter = if (position == 0 || sortedFriends.get(position - 1).name[0] != name[0]) name[0].toString() else ""

            view.friend_entry_name.text = name
            view.friend_entry_profile_image.loadUrlNoCache(image)
            view.friend_entry_online_indicator.visibility = if (online) View.VISIBLE else View.GONE
            view.friend_entry_optional_letter_label.text = firstLetter

        }

    }

    override fun add(model: FriendEntry) {
        this.sortedFriends.add(model)
    }

    override fun remove(model: FriendEntry) {
        this.sortedFriends.remove(model)
    }

    override fun add(models: List<FriendEntry>) {
        this.sortedFriends.addAll(models)
    }

    override fun remove(models: List<FriendEntry>) {
        this.sortedFriends.beginBatchedUpdates()
        for (model in models) {
            this.sortedFriends.remove(model)
        }
        this.sortedFriends.endBatchedUpdates()
    }

    override fun replaceAll(models: List<FriendEntry>) {
        this.sortedFriends.beginBatchedUpdates()
        (this.sortedFriends.size() - 1 downTo 0)
                .map { this.sortedFriends.get(it) }
                .filterNot { models.contains(it) }
                .forEach { this.sortedFriends.remove(it) }
        this.sortedFriends.addAll(models)
        this.sortedFriends.endBatchedUpdates()
    }

}