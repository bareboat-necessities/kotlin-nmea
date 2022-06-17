/*
 * APBParser.java
 * Copyright (C) 2014 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.APBSentenceimport

net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.*
/**
 * APB parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class APBParser : SentenceParser, APBSentence {
    /**
     * Creates a new instance of APBParser.
     *
     * @param nmea NMEA sentence String.
     */
    constructor(nmea: String) : super(nmea) {}

    /**
     * Creates a new empty APBParser.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, "APB", 14) {}

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#getBearginPositionToDestination
	 * ()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setBearingPositionToDestination
	 * (double)
	 */
    override var bearingPositionToDestination: Double
        get() = getDoubleValue(BEARING_POS_DEST)
        set(bearing) {
            setDegreesValue(BEARING_POS_DEST, bearing)
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#getBearingOriginToDestination
	 * ()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setBearingOriginToDestination
	 * (double)
	 */
    override var bearingOriginToDestination: Double
        get() = getDoubleValue(BEARING_ORIGIN_DEST)
        set(bearing) {
            setDegreesValue(BEARING_ORIGIN_DEST, bearing)
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getCrossTrackError()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setCrossTrackError(double)
	 */
    override var crossTrackError: Double
        get() = getDoubleValue(XTE_DISTANCE)
        set(distance) {
            setDoubleValue(XTE_DISTANCE, distance, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getCrossTrackUnits()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#setCrossTrackUnits(char)
	 */
    override var crossTrackUnits: Char
        get() = getCharValue(XTE_UNITS)
        set(unit) {
            if (unit != APBSentence.Companion.KM && unit != APBSentence.Companion.NM) {
                throw IllegalAccessError(
                    "Invalid distance unit char, expected 'K' or 'N'"
                )
            }
            setCharValue(XTE_UNITS, unit)
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getCycleLockStatus()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setCycleLockStatus(net.sf.
	 * marineapi.nmea.util.DataStatus)
	 */
    override var cycleLockStatus: DataStatus
        get() = DataStatus.Companion.valueOf(getCharValue(CYCLE_LOCK_STATUS))
        set(status) {
            setCharValue(CYCLE_LOCK_STATUS, status.toChar())
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#getDestionationWaypointId()
	 */
    override val destionationWaypointId: String?
        get() = getStringValue(DEST_WAYPOINT_ID)

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#getHeadingToDestionation()
	 */
    override val headingToDestionation: Double
        get() = getDoubleValue(HEADING_TO_DEST)

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getStatus()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override var status: DataStatus
        get() = DataStatus.Companion.valueOf(getCharValue(SIGNAL_STATUS))
        set(status) {
            setCharValue(SIGNAL_STATUS, status.toChar())
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getSteerTo()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setSteerTo(net.sf.marineapi
	 * .nmea.util.Direction)
	 */
    override var steerTo: Direction
        get() = Direction.Companion.valueOf(getCharValue(XTE_STEER_TO))
        set(direction) {
            setCharValue(XTE_STEER_TO, direction.toChar())
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#isArrivalCircleEntered()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setArrivalCircleEntered(boolean
	 * )
	 */
    override var isArrivalCircleEntered: Boolean
        get() = getCharValue(CIRCLE_STATUS) == 'A'
        set(isEntered) {
            val s = if (isEntered) DataStatus.ACTIVE else DataStatus.VOID
            setCharValue(CIRCLE_STATUS, s.toChar())
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#isBearingOriginToDestionationTrue
	 * ()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setBearingOriginToDestionationTrue
	 * (boolean)
	 */
    override var isBearingOriginToDestionationTrue: Boolean
        get() = getCharValue(BEARING_ORIGIN_DEST_TYPE) == 'T'
        set(isTrue) {
            val c = if (isTrue) 'T' else 'M'
            setCharValue(BEARING_ORIGIN_DEST_TYPE, c)
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#isBearingPositionToDestinationTrue
	 * ()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#
	 * setBearingPositionToDestinationTrue(boolean)
	 */
    override var isBearingPositionToDestinationTrue: Boolean
        get() = getCharValue(BEARING_POS_DEST_TYPE) == 'T'
        set(isTrue) {
            val c = if (isTrue) 'T' else 'M'
            setCharValue(BEARING_POS_DEST_TYPE, c)
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#isHeadingTrue()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setHeadingToDestinationTrue
	 * (boolean)
	 */
    override var isHeadingToDestinationTrue: Boolean
        get() = getCharValue(HEADING_TO_DEST_TYPE) == 'T'
        set(isTrue) {
            val c = if (isTrue) 'T' else 'M'
            setCharValue(HEADING_TO_DEST_TYPE, c)
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#isPerpendicularPassed()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setPerpendicularPassed(boolean
	 * )
	 */
    override var isPerpendicularPassed: Boolean
        get() = getCharValue(PERPENDICULAR_STATUS) == 'A'
        set(isPassed) {
            val s = if (isPassed) DataStatus.ACTIVE else DataStatus.VOID
            setCharValue(PERPENDICULAR_STATUS, s.toChar())
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setDestinationWaypointId(java
	 * .lang.String)
	 */
    override fun setDestinationWaypointId(id: String?) {
        setStringValue(DEST_WAYPOINT_ID, id)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setHeadingToDestination(double
	 * )
	 */
    override fun setHeadingToDestination(heading: Double) {
        setDoubleValue(HEADING_TO_DEST, heading)
    }

    companion object {
        private const val SIGNAL_STATUS = 0
        private const val CYCLE_LOCK_STATUS = 1
        private const val XTE_DISTANCE = 2
        private const val XTE_STEER_TO = 3
        private const val XTE_UNITS = 4
        private const val CIRCLE_STATUS = 5
        private const val PERPENDICULAR_STATUS = 6
        private const val BEARING_ORIGIN_DEST = 7
        private const val BEARING_ORIGIN_DEST_TYPE = 8
        private const val DEST_WAYPOINT_ID = 9
        private const val BEARING_POS_DEST = 10
        private const val BEARING_POS_DEST_TYPE = 11
        private const val HEADING_TO_DEST = 12
        private const val HEADING_TO_DEST_TYPE = 13
    }
}