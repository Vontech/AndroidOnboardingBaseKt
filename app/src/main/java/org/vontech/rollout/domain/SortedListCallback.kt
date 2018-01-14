package org.vontech.rollout.domain

import android.support.v7.util.SortedList
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import org.vontech.rollout.interfaces.SimpleNamedOrder

/**
 * Created by vontell on 1/13/18.
 */
class SortedListCallback<T>(val comparator: Comparator<T>, val adapter: SectionedRecyclerViewAdapter) : SortedList.Callback<T>() {

    override fun compare(a: T, b: T): Int {
        return comparator.compare(a, b)
    }

    override fun onInserted(position: Int, count: Int) {
        adapter.notifyItemRangeInserted(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.notifyItemRangeRemoved(position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int) {
        adapter.notifyItemRangeChanged(position, count)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(item1: T, item2: T): Boolean {
        return item1 === item2
    }


}