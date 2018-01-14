package org.vontech.rollout.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_create_group.*
import org.vontech.rollout.R
import org.vontech.rollout.TestGen
import org.vontech.rollout.domain.FriendEntry
import org.vontech.rollout.domain.GroupEntry
import org.vontech.rollout.domain.SortedListCallback
import org.vontech.rollout.interfaces.SimpleNamedOrder
import org.vontech.rollout.views.FriendEntriesSection
import org.vontech.rollout.views.GroupEntriesSection

/**
 * Activity which provides the user abilities to search for friends and
 * create groups.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class CreateGroupActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var adapter : SectionedRecyclerViewAdapter

    private lateinit var friendSection : FriendEntriesSection
    private lateinit var friendsList: List<FriendEntry>
    private lateinit var groupsSection : GroupEntriesSection
    private lateinit var groupsList: List<GroupEntry>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        linearLayoutManager = LinearLayoutManager(this)
        create_group_recycler.layoutManager = linearLayoutManager

        // Construct the sections
        adapter = SectionedRecyclerViewAdapter()
        val sorter = SortedListCallback(SimpleNamedOrder.getComparator<FriendEntry>(), adapter)
        val sorter2 = SortedListCallback(SimpleNamedOrder.getComparator<GroupEntry>(), adapter)

        friendsList = TestGen.getTestUserEntries()
        friendSection = FriendEntriesSection(friendsList, this, sorter)
        groupsList = TestGen.getTestGroupEntries()
        groupsSection = GroupEntriesSection(groupsList, this, sorter2)

        // Add each section to the main adapter
        adapter.addSection(groupsSection)
        adapter.addSection(friendSection)

        create_group_recycler.adapter = adapter

        attachAddPersonListeners()

    }

    fun attachAddPersonListeners() {

        add_person_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var newString = p0.toString().trim()
                filterFriendsList(newString)
                filterGroupsList(newString)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

    }

    fun filterFriendsList(newString: String) {

        val lowerCaseQuery = newString.toLowerCase()
        val filteredFriends = ArrayList<FriendEntry>()
        for (entry in this.friendsList) {
            val text = entry.name.toLowerCase()
            val email = entry.email.toLowerCase()
            val phone = entry.phone.replace("[^0-9]".toRegex(), "")
            if (text.contains(lowerCaseQuery) || email.contains(lowerCaseQuery) || phone.contains(lowerCaseQuery)) {
                filteredFriends.add(entry)
            }
        }
        friendSection.replaceAll(filteredFriends)
        create_group_recycler.scrollToPosition(0)

    }

    fun filterGroupsList(newString: String) {

        val lowerCaseQuery = newString.toLowerCase()
        val filteredGroups = ArrayList<GroupEntry>()
        for (entry in this.groupsList) {
            val text = entry.name.toLowerCase()
            if (text.contains(lowerCaseQuery)) {
                filteredGroups.add(entry)
            }
        }
        groupsSection.replaceAll(filteredGroups)
        create_group_recycler.scrollToPosition(0)

    }

}
