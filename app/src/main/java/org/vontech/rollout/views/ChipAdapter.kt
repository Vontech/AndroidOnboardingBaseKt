package org.vontech.rollout.views

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_friend_chip.view.*
import org.vontech.rollout.domain.FriendEntry

/**
 * An adapter for managing a collection of chips given FriendEntry(ies)
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class ChipAdapter(private var friends: List<FriendEntry>) : RecyclerView.Adapter<ChipAdapter.ChipViewHolder>() {

    override fun onBindViewHolder(holder: ChipViewHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChipViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return this.friends.size
    }

    inner class ChipViewHolder (v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

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

            view.chip_name.text = entry.name
            view.chip_image.loadUrlNoCache(entry.profileURI)

        }

    }

}