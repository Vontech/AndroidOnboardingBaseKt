package org.vontech.rollout.utils

import android.content.Context
import android.provider.ContactsContract
import android.util.Log


/**
 * A class to be used in retrieving contacts from the user's phone
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class ContactsHelper(val hasPermission: Boolean, val context: Context) {

    /**
     * Returns a list of all contacts, if the user has permission
     * @return The list of contacts as pairs of names to phone numbers
     */
    fun getAllContacts(): List<Pair<String, String>> {
        return if (hasPermission) {
            val results = ArrayList<Pair<String, String>>()
            val phones = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            while (phones.moveToNext()) {
                val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                results.add(Pair(name, phoneNumber))
                Log.e(name, phoneNumber)
            }
            phones.close()
            results
        } else {
            ArrayList()
        }
    }

}
