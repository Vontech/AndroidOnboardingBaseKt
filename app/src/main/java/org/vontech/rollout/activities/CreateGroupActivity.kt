package org.vontech.rollout.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_create_group.*
import org.jetbrains.anko.*
import org.vontech.rollout.R
import org.vontech.rollout.TestGen
import org.vontech.rollout.views.FriendEntriesAdapter
import org.vontech.rollout.views.load

/**
 * Activity which provides the user abilities to search for friends and
 * create groups.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class CreateGroupActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var friendAdapter : FriendEntriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        linearLayoutManager = LinearLayoutManager(this)
        create_group_recycler.layoutManager = linearLayoutManager

        var friends = TestGen.getTestUserEntries()
        friendAdapter = FriendEntriesAdapter(friends, this)
        create_group_recycler.adapter = friendAdapter

    }

}
