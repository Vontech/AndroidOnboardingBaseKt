package org.vontech.rollout.domain

import org.vontech.rollout.interfaces.SimpleNamedOrder

/**
 * A data structure representing a group of contacts or friends within the CreateGroupActivity.
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */

class GroupEntry(val profileURI: String, val name: String) : SimpleNamedOrder {

    override fun getSimpleName() : String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GroupEntry

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + "THIS IS GROUP".hashCode()
        result = 31 * result + profileURI.hashCode().hashCode()
        return result
    }


}