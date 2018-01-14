package org.vontech.rollout.views

import android.content.Context
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.view.View
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import kotlinx.android.synthetic.main.view_friend_entry.view.*
import org.vontech.rollout.R
import org.vontech.rollout.domain.GroupEntry
import org.vontech.rollout.domain.SortedListCallback

/**
 * A RecyclerView adapter that is used in the group creation and friend lookup screens
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class GroupEntriesSection(private var groups: List<GroupEntry>, private var context: Context, private var sorter: SortedListCallback<GroupEntry>) :
        FilteredGroupSection<GroupEntry>(SectionParameters.Builder(R.layout.view_friend_entry)
                .headerResourceId(R.layout.view_friend_header)
                .build()) {

    private var sortedGroups: SortedList<GroupEntry>

    init {
        // Use a SortedList for nice filtering
        this.sortedGroups = SortedList<GroupEntry>(GroupEntry::class.java, this.sorter)
        this.sortedGroups.addAll( this.groups.sortedWith(compareBy({ it.getSimpleName() })).toList())
    }

    override fun getContentItemsTotal(): Int {
        return this.sortedGroups.size() // number of items of this section
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        // return a custom instance of ViewHolder for the items of this section
        return GroupEntryHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return FriendsHeaderHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val headerHolder = holder as FriendsHeaderHolder
        headerHolder.bindEntry(context.getString(R.string.group_creation_groups))
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as GroupEntryHolder
        itemHolder.bindEntry(position, this.sortedGroups.get(position))
    }

    inner class GroupEntryHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var entry: GroupEntry? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun bindEntry(position: Int, entry: GroupEntry) {
            this.entry = entry

            val name = entry.name
            val image = entry.profileURI
            val firstLetter = if (position == 0 || sortedGroups.get(position - 1).name[0] != name[0]) name[0].toString() else ""

            view.friend_entry_name.text = name
            view.friend_entry_profile_image.loadUrlNoCache(image)
            view.friend_entry_online_indicator.visibility = View.GONE // TODO
            view.friend_entry_optional_letter_label.text = firstLetter

        }

    }

    override fun add(model: GroupEntry) {
        this.sortedGroups.add(model)
    }

    override fun remove(model: GroupEntry) {
        this.sortedGroups.remove(model)
    }

    override fun add(models: List<GroupEntry>) {
        this.sortedGroups.addAll(models)
    }

    override fun remove(models: List<GroupEntry>) {
        this.sortedGroups.beginBatchedUpdates()
        for (model in models) {
            this.sortedGroups.remove(model)
        }
        this.sortedGroups.endBatchedUpdates()
    }

    override fun replaceAll(models: List<GroupEntry>) {
        this.sortedGroups.beginBatchedUpdates()
        (this.sortedGroups.size() - 1 downTo 0)
                .map { this.sortedGroups.get(it) }
                .filterNot { models.contains(it) }
                .forEach { this.sortedGroups.remove(it) }
        this.sortedGroups.addAll(models)
        this.sortedGroups.endBatchedUpdates()
    }

}