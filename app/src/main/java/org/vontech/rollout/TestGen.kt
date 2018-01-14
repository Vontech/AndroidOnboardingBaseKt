package org.vontech.rollout

import org.vontech.rollout.domain.FriendEntry
import org.vontech.rollout.domain.GroupEntry
import java.util.*

/**
 * A collection of functions and objects for filler and testing of the application
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class TestGen() {

    companion object {

        private val Beginning = arrayOf("Kr", "Ca", "Ra", "Mrok", "Cru", "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol", "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro", "Mar", "Luk")
        private val Middle = arrayOf("air", "ir", "mi", "sor", "mee", "clo", "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer", "marac", "zoir", "slamar", "salmar", "urak")
        private val End = arrayOf("d", "ed", "ark", "arc", "es", "er", "der", "tron", "med", "ure", "zur", "cred", "mur")

        private val rand = Random()

        fun getTestUserEntries() : ArrayList<FriendEntry> {

            var results = arrayListOf<FriendEntry>()
            for (i in 0..70) {
                results.add(
                        FriendEntry(
                        "https://picsum.photos/500/?random",
                        rand.nextBoolean(),
                        generateName() + " " + generateName(),
                        "(843) 932-8457",
                        generateName() + "@fake.com"))
            }

            return results
        }

        fun getTestGroupEntries() : ArrayList<GroupEntry> {

            var results = arrayListOf<GroupEntry>()
            for (i in 0..3) {
                results.add(
                        GroupEntry(
                                "https://picsum.photos/500/?random",
                                generateName()))
            }

            return results
        }

        fun generateName(): String {

            return Beginning[rand.nextInt(Beginning.size)] +
                    Middle[rand.nextInt(Middle.size)] +
                    End[rand.nextInt(End.size)]

        }

    }

}