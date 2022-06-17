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

import net.sf.marineapi.nmea.sentence.RMBSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.*
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
    constructor(nmea: String) : super(nmea, SentenceId.RMB) {}

    /**
     * Creates RMB parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.RMB, 13) {}

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getArrivalStatus()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setArrivalStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override var arrivalStatus: DataStatus
        get() = DataStatus.Companion.valueOf(getCharValue(ARRIVAL_STATUS))
        set(status) {
            setCharValue(ARRIVAL_STATUS, status.toChar())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getBearing()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#setBearing(double)
	 */
    override var bearing: Double
        get() = getDoubleValue(BEARING_TO_DEST)
        set(bearing) {
            setDegreesValue(BEARING_TO_DEST, bearing)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getCrossTrackError()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setCrossTrackError(double)
	 */
    override var crossTrackError: Double
        get() = getDoubleValue(CROSS_TRACK_ERROR)
        set(xte) {
            setDoubleValue(CROSS_TRACK_ERROR, xte, 1, 2)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getDestination()
	 */
    override fun getDestination(): Waypoint? {
        val id = getStringValue(DEST_WPT)
        val p = parsePosition(
            DEST_LAT, DEST_LAT_HEM, DEST_LON, DEST_LON_HEM
        )
        return p!!.toWaypoint(id)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getOriginId()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setOriginId(java.lang.String)
	 */
    override var originId: String?
        get() = getStringValue(ORIGIN_WPT)
        set(id) {
            setStringValue(ORIGIN_WPT, id)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getRange()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#setRange(double)
	 */
    override var range: Double
        get() = getDoubleValue(RANGE_TO_DEST)
        set(range) {
            setDoubleValue(RANGE_TO_DEST, range, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getStatus()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override var status: DataStatus
        get() = DataStatus.Companion.valueOf(getCharValue(STATUS))
        set(status) {
            setCharValue(STATUS, status.toChar())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getSteerTo()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setSteerTo(net.sf.marineapi
	 * .nmea.util.Direction)
	 */
    override var steerTo: Direction
        get() = Direction.Companion.valueOf(getCharValue(STEER_TO))
        set(steer) {
            require(!(steer != Direction.LEFT && steer != Direction.RIGHT)) { "Expected steer-to is LEFT or RIGHT." }
            setCharValue(STEER_TO, steer.toChar())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#getVelocity()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#setVelocity(double)
	 */
    override var velocity: Double
        get() = getDoubleValue(VELOCITY)
        set(velocity) {
            setDoubleValue(VELOCITY, velocity, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMBSentence#hasArrived()
	 */
    override fun hasArrived(): Boolean {
        return DataStatus.ACTIVE == arrivalStatus
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMBSentence#setDestination(net.sf.marineapi
	 * .nmea.util.Waypoint)
	 */
    override fun setDestination(dest: Waypoint) {
        setStringValue(DEST_WPT, dest.id)
        setPositionValues(dest, DEST_LAT, DEST_LAT_HEM, DEST_LON, DEST_LON_HEM)
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