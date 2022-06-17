/* 
 * RTEParser.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.*


/**
 * RTE sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class RTEParser : SentenceParser, RTESentence {
    /**
     * Creates a new instance of RTE parser.
     *
     * @param nmea RTE sentence string.
     */
    constructor(nmea: String) : super(nmea, SentenceId.RTE)

    /**
     * Creates RTE parser with empty sentence. The created RTE sentence contains
     * none waypoint ID fields.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.RTE, 4)

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RTESentence#addWaypointId(java.lang.String
	 * )
	 */
    override fun addWaypointId(id: String?): Int {
        val ids = waypointIds
        val newIds = arrayOfNulls<String>(ids.size + 1)
        System.arraycopy(ids, 0, newIds, 0, ids.size)
        newIds[newIds.size - 1] = id
        setStringValues(FIRST_WPT, newIds)
        return newIds.size
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#getRouteId()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RTESentence#setRouteId(java.lang.String)
	 */
    override var routeId: String?
        get() = getStringValue(ROUTE_ID)
        set(id) {
            setStringValue(ROUTE_ID, id)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#getSentenceCount()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#setSentenceCount(int)
	 */
    override var sentenceCount: Int
        get() = getIntValue(NUMBER_OF_SENTENCES)
        set(count) {
            require(count >= 0) { "Count cannot be negative" }
            setIntValue(NUMBER_OF_SENTENCES, count)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#getSentenceIndex()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#setSentenceIndex(int)
	 */
    override var sentenceIndex: Int
        get() = getIntValue(SENTENCE_NUMBER)
        set(index) {
            require(index >= 0) { "Index cannot be negative" }
            setIntValue(SENTENCE_NUMBER, index)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#getWaypointCount()
	 */
    override fun getWaypointCount(): Int {
        return waypointIds.size
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#getWaypointIds()
	 */
    override fun getWaypointIds(): Array<String?> {
        val temp: MutableList<String?> = ArrayList()
        for (i in FIRST_WPT until fieldCount) {
            try {
                temp.add(getStringValue(i))
            } catch (e: DataNotAvailableException) {
                // nevermind empty fields
            }
        }
        return temp.toTypedArray()
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#isActiveRoute()
	 */
    override fun isActiveRoute(): Boolean {
        return getCharValue(STATUS) == RouteType.ACTIVE.toChar()
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#isFirst()
	 */
    override fun isFirst(): Boolean {
        return sentenceIndex == 1
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#isLast()
	 */
    override fun isLast(): Boolean {
        return sentenceIndex == sentenceCount
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RTESentence#isWorkingRoute()
	 */
    override fun isWorkingRoute(): Boolean {
        return getCharValue(STATUS) == RouteType.WORKING.toChar()
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RTESentence#setRouteType(net.sf.marineapi
	 * .nmea.util.RouteType)
	 */
    override fun setRouteType(type: RouteType) {
        setCharValue(STATUS, type.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RTESentence#setWaypointIds(java.lang.String
	 * [])
	 */
    override fun setWaypointIds(ids: Array<String?>?) {
        setStringValues(FIRST_WPT, ids!!)
    }

    companion object {
        // fields indices
        private const val NUMBER_OF_SENTENCES = 0
        private const val SENTENCE_NUMBER = 1
        private const val STATUS = 2
        private const val ROUTE_ID = 3
        private const val FIRST_WPT = 4
    }
}