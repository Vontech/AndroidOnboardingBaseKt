package org.vontech.rollout.domain

import org.vontech.rollout.interfaces.SimpleNamedOrder

/**
 * A data structure representing a contact or friend within the CreateGroupActivity. This may be
 * created from the contact list or from a user account.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */

class FriendEntry(val profileURI: String, val online: Boolean, val name: String, val phone: String, val email: String) : SimpleNamedOrder {


    override fun getSimpleName() : String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FriendEntry

        if (profileURI != other.profileURI) return false
        if (name != other.name) return false
        if (phone != other.phone) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = profileURI.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }
}
