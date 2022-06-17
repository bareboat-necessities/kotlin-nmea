/* 
 * TLBParser.java
 * Copyright (C) 2020 Joshua Sweaney
 * 
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 * 
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.SentenceIdimport

net.sf.marineapi.nmea.sentence.TLBSentenceimport net.sf.marineapi.nmea.sentence.TalkerId
/**
 * TLB sentence parser
 *
 * @author Joshua Sweaney
 */
internal class TLBParser : SentenceParser, TLBSentence {
    /**
     * Creates a new instance of TLB parser
     *
     * @param nmea TLB sentence string.
     */
    constructor(nmea: String) : super(nmea, SentenceId.TLB) {
        require(fieldCount % 2 == 0) { "Invalid TLB sentence. Must contain pairs of target numbers and labels." }
    }

    /**
     * Creates TLB parser with empty sentence. The created TLB sentence contains
     * no target id,label pairs.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.TLB, 0)

    /**
     * @see net.sf.marineapi.nmea.sentence.TLBSentence.getTargetIds
     */
    override fun getTargetIds(): IntArray {
        val ids = IntArray((fieldCount / 2))
        var i = 0
        var j = 0
        while (j < ids.size) {
            ids[j] = getIntValue(i)
            i += 2
            j++
        }
        return ids
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.TLBSentence.getTargetLabels
     */
    override fun getTargetLabels(): Array<String?> {
        val labels = arrayOfNulls<String>((fieldCount / 2))
        var i = 1
        var j = 0
        while (j < labels.size) {
            try {
                labels[j] = getStringValue(i)
            } catch (ex: DataNotAvailableException) {
                labels[j] = ""
            }
            i += 2
            j++
        }
        return labels
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.TLBSentence.addTargetLabel
     */
    override fun addTargetLabel(targetId: Int, targetLabel: String?) {
        val ids = targetIds
        val labels = targetLabels
        val newFields = arrayOfNulls<String>((ids.size + 1) * 2)

        // Since the ID part of each (ID,label) pair comes first, we will consider that
        // to be authoratative about the number of pairs that should exist.
        // If the labels array is shorter, empty strings are used. If longer, only
        // pairs up to ids.length are added.
        var i = 0
        var j = 0
        while (i < ids.size) {
            newFields[j] = ids[i].toString()
            if (i < labels.size) {
                newFields[j + 1] = labels[i]
            } else {
                newFields[j + 1] = ""
            }
            i++
            j += 2
        }

        // Finally, add the new id,label pair
        newFields[newFields.size - 2] = targetId.toString()
        newFields[newFields.size - 1] = targetLabel
        setStringValues(FIRST_PAIR, newFields)
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.TLBSentence.setTargetPairs
     */
    override fun setTargetPairs(ids: IntArray, labels: Array<String?>) {
        require(ids.size == labels.size) { "The ID and Label arrays must be the same length." }
        val newFields = arrayOfNulls<String>(ids.size * 2)
        var i = 0
        var j = 0
        while (i < ids.size) {
            newFields[j] = ids[i].toString()
            newFields[j + 1] = labels[i]
            i++
            j += 2
        }
        setStringValues(FIRST_PAIR, newFields)
    }

    companion object {
        // field indices
        private const val FIRST_PAIR = 0
    }
}