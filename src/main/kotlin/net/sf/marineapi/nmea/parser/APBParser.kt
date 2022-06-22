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

import net.sf.marineapi.nmea.sentence.APBSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction

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
    constructor(nmea: String) : super(nmea)

    /**
     * Creates a new empty APBParser.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, "APB", 14)

    override fun getBearingPositionToDestination(): Double {
        return getDoubleValue(BEARING_POS_DEST)
    }

    override fun getBearingOriginToDestination(): Double {
        return getDoubleValue(BEARING_ORIGIN_DEST)
    }

    override fun getCrossTrackError(): Double {
        return getDoubleValue(XTE_DISTANCE)
    }

    override fun getCrossTrackUnits(): Char {
        return getCharValue(XTE_UNITS)
    }

    override fun getCycleLockStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(CYCLE_LOCK_STATUS))
    }

    override fun getDestionationWaypointId(): String {
        return getStringValue(DEST_WAYPOINT_ID)
    }

    override fun getHeadingToDestionation(): Double {
        return getDoubleValue(HEADING_TO_DEST)
    }

    override fun getStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(SIGNAL_STATUS))
    }

    override fun getSteerTo(): Direction {
        return Direction.valueOf(getCharValue(XTE_STEER_TO))
    }

    override fun isArrivalCircleEntered(): Boolean {
        return getCharValue(CIRCLE_STATUS) == 'A'
    }

    override fun isBearingOriginToDestionationTrue(): Boolean {
        return getCharValue(BEARING_ORIGIN_DEST_TYPE) == 'T'
    }

    override fun isBearingPositionToDestinationTrue(): Boolean {
        return getCharValue(BEARING_POS_DEST_TYPE) == 'T'
    }

    override fun isHeadingToDestinationTrue(): Boolean {
        return getCharValue(HEADING_TO_DEST_TYPE) == 'T'
    }

    override fun isPerpendicularPassed(): Boolean {
        return getCharValue(PERPENDICULAR_STATUS) == 'A'
    }

    override fun setArrivalCircleEntered(isEntered: Boolean) {
        val s = if (isEntered) DataStatus.ACTIVE else DataStatus.VOID
        setCharValue(CIRCLE_STATUS, s.toChar())
    }

    override fun setBearingOriginToDestination(bearing: Double) {
        setDegreesValue(BEARING_ORIGIN_DEST, bearing)
    }

    override fun setBearingOriginToDestionationTrue(isTrue: Boolean) {
        val c = if (isTrue) 'T' else 'M'
        setCharValue(BEARING_ORIGIN_DEST_TYPE, c)
    }

    override fun setBearingPositionToDestination(bearing: Double) {
        setDegreesValue(BEARING_POS_DEST, bearing)
    }

    override fun setBearingPositionToDestinationTrue(isTrue: Boolean) {
        val c = if (isTrue) 'T' else 'M'
        setCharValue(BEARING_POS_DEST_TYPE, c)
    }

    override fun setCrossTrackError(distance: Double) {
        setDoubleValue(XTE_DISTANCE, distance, 1, 1)
    }

    override fun setCrossTrackUnits(unit: Char) {
        if (unit != APBSentence.KM && unit != APBSentence.NM) {
            throw IllegalAccessError(
                "Invalid distance unit char, expected 'K' or 'N'"
            )
        }
        setCharValue(XTE_UNITS, unit)
    }

    override fun setCycleLockStatus(status: DataStatus?) {
        setCharValue(CYCLE_LOCK_STATUS, status!!.toChar())
    }

    override fun setDestinationWaypointId(id: String?) {
        setStringValue(DEST_WAYPOINT_ID, id)
    }

    override fun setHeadingToDestination(heading: Double) {
        setDoubleValue(HEADING_TO_DEST, heading)
    }

    override fun setHeadingToDestinationTrue(isTrue: Boolean) {
        val c = if (isTrue) 'T' else 'M'
        setCharValue(HEADING_TO_DEST_TYPE, c)
    }

    override fun setPerpendicularPassed(isPassed: Boolean) {
        val s = if (isPassed) DataStatus.ACTIVE else DataStatus.VOID
        setCharValue(PERPENDICULAR_STATUS, s.toChar())
    }

    override fun setStatus(status: DataStatus?) {
        setCharValue(SIGNAL_STATUS, status!!.toChar())
    }

    override fun setSteerTo(direction: Direction?) {
        setCharValue(XTE_STEER_TO, direction!!.toChar())
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