/* 
 * WPLParser.java
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

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.WPLSentence
import net.sf.marineapi.nmea.util.Waypoint

/**
 * WPL sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class WPLParser : PositionParser, WPLSentence {
    /**
     * Creates a new instance of WPLParser.
     *
     * @param nmea WPL sentence String.
     * @throws IllegalArgumentException If specified sentence is invalid.
     */
    constructor(nmea: String) : super(nmea, SentenceId.WPL)

    /**
     * Creates WPL parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.WPL, 5)

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.WPLSentence#getWaypoint()
	 */
    override fun getWaypoint(): Waypoint {
        val id = getStringValue(WAYPOINT_ID)
        val p = parsePosition(
            LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE
        )
        return p.toWaypoint(id)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.WPLSentence#setWaypoint(net.sf.marineapi
	 * .nmea.util.Waypoint)
	 */
    override fun setWaypoint(wp: Waypoint) {
        setStringValue(WAYPOINT_ID, wp.id)
        setPositionValues(wp, LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE)
    }

    companion object {
        // field ids
        private const val LATITUDE = 0
        private const val LAT_HEMISPHERE = 1
        private const val LONGITUDE = 2
        private const val LON_HEMISPHERE = 3
        private const val WAYPOINT_ID = 4
    }
}