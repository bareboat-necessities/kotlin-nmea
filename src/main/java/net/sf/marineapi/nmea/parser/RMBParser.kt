/* 
 * RMBParser.java
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

import net.sf.marineapi.nmea.sentence.RMBSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction
import net.sf.marineapi.nmea.util.Waypoint

/**
 * RMB sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class RMBParser : PositionParser, RMBSentence {
    /**
     * Constructor.
     *
     * @param nmea RMB sentence string
     */
    constructor(nmea: String) : super(nmea, SentenceId.RMB)

    /**
     * Creates RMB parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.RMB, 13)

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getArrivalStatus()
	 */
    override fun getArrivalStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(ARRIVAL_STATUS))
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getBearing()
	 */
    override fun getBearing(): Double {
        return getDoubleValue(BEARING_TO_DEST)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getCrossTrackError()
	 */
    override fun getCrossTrackError(): Double {
        return getDoubleValue(CROSS_TRACK_ERROR)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getDestination()
	 */
    override fun getDestination(): Waypoint {
        val id = getStringValue(DEST_WPT)
        val p = parsePosition(
            DEST_LAT, DEST_LAT_HEM, DEST_LON, DEST_LON_HEM
        )
        return p.toWaypoint(id)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getOriginId()
	 */
    override fun getOriginId(): String {
        return getStringValue(ORIGIN_WPT)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getRange()
	 */
    override fun getRange(): Double {
        return getDoubleValue(RANGE_TO_DEST)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getStatus()
	 */
    override fun getStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(STATUS))
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getSteerTo()
	 */
    override fun getSteerTo(): Direction {
        return Direction.valueOf(getCharValue(STEER_TO))
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getVelocity()
	 */
    override fun getVelocity(): Double {
        return getDoubleValue(VELOCITY)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#hasArrived()
	 */
    override fun hasArrived(): Boolean {
        return DataStatus.ACTIVE == getArrivalStatus()
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setArrivalStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override fun setArrivalStatus(status: DataStatus?) {
        setCharValue(ARRIVAL_STATUS, status!!.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#setBearing(double)
	 */
    override fun setBearing(bearing: Double) {
        setDegreesValue(BEARING_TO_DEST, bearing)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setCrossTrackError(double)
	 */
    override fun setCrossTrackError(xte: Double) {
        setDoubleValue(CROSS_TRACK_ERROR, xte, 1, 2)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setDestination(net.sf.marineapi
	 * .nmea.util.Waypoint)
	 */
    override fun setDestination(dest: Waypoint?) {
        setStringValue(DEST_WPT, dest!!.id)
        setPositionValues(dest, DEST_LAT, DEST_LAT_HEM, DEST_LON, DEST_LON_HEM)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setOriginId(java.lang.String)
	 */
    override fun setOriginId(id: String?) {
        setStringValue(ORIGIN_WPT, id)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#setRange(double)
	 */
    override fun setRange(range: Double) {
        setDoubleValue(RANGE_TO_DEST, range, 1, 1)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override fun setStatus(status: DataStatus?) {
        setCharValue(STATUS, status!!.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setSteerTo(net.sf.marineapi
	 * .nmea.util.Direction)
	 */
    override fun setSteerTo(steerTo: Direction?) {
        require(steerTo == Direction.LEFT || steerTo == Direction.RIGHT) { "Expected steer-to is LEFT or RIGHT." }
        setCharValue(STEER_TO, steerTo!!.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#setVelocity(double)
	 */
    override fun setVelocity(velocity: Double) {
        setDoubleValue(VELOCITY, velocity, 1, 1)
    }

    companion object {
        // field indexes
        private const val STATUS = 0
        private const val CROSS_TRACK_ERROR = 1
        private const val STEER_TO = 2
        private const val ORIGIN_WPT = 3
        private const val DEST_WPT = 4
        private const val DEST_LAT = 5
        private const val DEST_LAT_HEM = 6
        private const val DEST_LON = 7
        private const val DEST_LON_HEM = 8
        private const val RANGE_TO_DEST = 9
        private const val BEARING_TO_DEST = 10
        private const val VELOCITY = 11
        private const val ARRIVAL_STATUS = 12
    }
}