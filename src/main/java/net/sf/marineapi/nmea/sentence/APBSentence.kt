/*
 * APBSentence.java
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
package net.sf.marineapi.nmea.sentence

import net.sf.marineapi.nmea.util.*

/**
 *
 *
 * Autopilot sentence "type B", cross-track error, bearings and heading towards
 * destination waypoint. This is a fixed form of the APA sentence with some
 * ambiguities removed.
 *
 *
 *
 * Note: Some autopilots, Robertson in particular, misinterpret
 * "bearing from origin to destination" as "bearing from present position to
 * destination".
 *
 *
 *
 * Example:<br></br>
 * `$GPAPB,A,A,0.10,R,N,V,V,011,M,DEST,011,M,011,M*82`
 *
 * @author Kimmo Tuukkanen
 */
interface APBSentence : Sentence {
    /**
     * Returns the bearing from current position to destination waypoint.
     *
     * @return bearing in degrees
     */
    /**
     * Sets the bearing from current position to destination waypoint.
     *
     * @param bearing bearing in degrees
     */
    var bearingPositionToDestination: Double
    /**
     * Returns bearing from origin to destination.
     *
     * @return bearing in degrees
     */
    /**
     * Sets the bearing from origin to destination.
     *
     * @param bearing bearing in degrees
     */
    var bearingOriginToDestination: Double
    /**
     * Returns the cross-track error magnitude/distance.
     *
     * @return Cross-track error distance in nautical miles
     */
    /**
     * Sets the cross-track error magnitude/distance.
     *
     * @param distance Cross-track error distance in nautical miles
     */
    var crossTrackError: Double
    /**
     * Returns the unit of cross-track error.
     *
     * @return Unit char indicator
     */
    /**
     * Sets the unit of cross-track error.
     *
     * @param unit Unit char to set
     */
    var crossTrackUnits: Char
    /**
     * Returns the Loran-C cycle lock status, not used for GPS.
     *
     * @return Loran-C cycle lock status.
     */
    /**
     * Sets the Loran-C cycle lock status. Not used for GPS, may be omitted or
     * [DataStatus.VOID].
     *
     * @param status DataStatus to set
     */
    var cycleLockStatus: DataStatus

    /**
     * Returns the destination waypoint id/name.
     *
     * @return Waypoint id
     */
    val destionationWaypointId: String?

    /**
     * Returns the heading to steer to destination waypoint.
     *
     * @return Heading in degrees
     */
    val headingToDestionation: Double
    /**
     * Returns the signal/fix status, LORAN-C Blink or SNR warning.
     *
     * @return DataStatus
     */
    /**
     * Sets the signal/fix status, LORAN-C Blink or SNR warning.
     *
     * @param status DataStatus to set.
     */
    var status: DataStatus
    /**
     * Returns the direction in which to steer in order to get back on route.
     *
     * @return [Direction.LEFT] or [Direction.RIGHT]
     */
    /**
     * Set direction in which to steer in order to get back on route.
     *
     * @param direction [Direction.RIGHT] or [Direction.LEFT]
     */
    var steerTo: Direction
    /**
     * Tells if vessel has entered the arrival circle.
     *
     * @return True if entered, otherwise false.
     */
    /**
     * Sets the arrival circle enter status.
     *
     * @param isEntered True if entered, otherwise false.
     */
    var isArrivalCircleEntered: Boolean
    /**
     * Tells if the bearing from origin to destination is true or magnetic.
     *
     * @return True if true heading, false for magnetic.
     */
    /**
     * Sets the bearing from origin to destination true or magnetic.
     *
     * @param isTrue True if true bearing, false for magnetic.
     */
    var isBearingOriginToDestionationTrue: Boolean
    /**
     * Tells if the bearing from current position to destionation is true or
     * magnetic.
     *
     * @return True if true heading, false for magnetic.
     */
    /**
     * Sets the bearing from current position to destination true or magnetic.
     *
     * @param isTrue True if true bearing, false for magnetic.
     */
    var isBearingPositionToDestinationTrue: Boolean
    /**
     * Tells if the heading to destionation is true or magnetic.
     *
     * @return True if true heading, false for magnetic.
     */
    /**
     * Sets the heading to destionation true or magnetic.
     *
     * @param isTrue True if true heading, false for magnetic.
     */
    var isHeadingToDestinationTrue: Boolean
    /**
     * Tells if vessel has passed perpendicular at waypoint.
     *
     * @return True if passed, otherwise false.
     */
    /**
     * Sets the perpendicular to waypoint pass status.
     *
     * @param isPassed True if passed, otherwise false.
     */
    var isPerpendicularPassed: Boolean

    /**
     * Returns the destination waypoint id/name.
     *
     * @param id Waypoint ID to set.
     */
    fun setDestinationWaypointId(id: String?)

    /**
     * Sets the heading to destination waypoint.
     *
     * @param heading heading in degrees
     */
    fun setHeadingToDestination(heading: Double)

    companion object {
        /** Kilometers  */
        const val KM = 'K'

        /** Nautical miles  */
        const val NM = 'N'
    }
}