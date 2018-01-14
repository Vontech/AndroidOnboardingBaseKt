package org.vontech.rollout.interfaces

/**
 * An interface that simply forces an object to return a name representation of themselves
 * @author Aaron Vontell
 * @owner Vontech Sofware, LLC
 */
interface SimpleNamedOrder {
    fun getSimpleName() : String

    companion object {

        // A comparator to use for these objects
        fun <T> getComparator() : Comparator<T> {
            return Comparator { o1, o2 ->
                o1 as SimpleNamedOrder
                o2 as SimpleNamedOrder
                o1.getSimpleName().compareTo(o2.getSimpleName())
            }
        }
    }

}
