/* 
 * RMBSentence.java
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
package net.sf.marineapi.nmea.sentence

import net.sf.marineapi.nmea.util.*

/**
 * Recommended minimum navigation information. This sentence is transmitted by a
 * GPS receiver when a destination waypoint is active (GOTO mode).
 *
 *
 * Example:<br></br>
 * `$GPRMB,A,0.00,R,,RUSKI,5536.200,N,01436.500,E,432.3,234.9,,V*58`
 *
 * @author Kimmo Tuukkanen
 */
interface RMBSentence : Sentence {
    /**
     * Get the arrival to waypoint status. Status is [DataStatus.VOID]
     * (false) while not arrived at destination, otherwise
     * [DataStatus.ACTIVE] (true).
     *
     * @return [DataStatus.ACTIVE] or [DataStatus.VOID]
     * @see .hasArrived
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set the arrival to waypoint status. Set [DataStatus.VOID] if not
     * arrived at destination, otherwise [DataStatus.ACTIVE].
     *
     * @param status [DataStatus.VOID] or [DataStatus.ACTIVE].
     * @throws IllegalArgumentException If status is `null`.
     */
    var arrivalStatus: DataStatus
    /**
     * Get true bearing to destination.
     *
     * @return True bearing in degrees.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set true bearing to destination, in degrees.
     *
     * @param bearing Bearing value, will be rounded to one decimal.
     * @throws IllegalArgumentException If bearing value is out of bounds 0..360
     * degrees.
     */
    var bearing: Double
    /**
     * Get cross track error (XTE).
     *
     * @return Cross track error, in nautical miles.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set cross track error (XTE), in nautical miles. Negative values are
     * translated to positive, set Steer-To to indicate the direction of error.
     *
     * @param xte Cross track error value, will be rounded to one decimal.
     * @see .setSteerTo
     */
    var crossTrackError: Double

    /**
     * Get the destination waypoint.
     *
     * @return Waypoint
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getDestination(): Waypoint?
    /**
     * Get the ID of origin waypoint.
     *
     * @return Id String.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set the ID of origin waypoint.
     *
     * @param id ID to set
     */
    var originId: String?
    /**
     * Get range to destination waypoint.
     *
     * @return Range to destination, in nautical miles.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set range to destination waypoint.
     *
     * @param range Range value, in nautical miles.
     */
    var range: Double
    /**
     * Get the sentence data status, valid or invalid.
     *
     * @return [DataStatus.ACTIVE] or [DataStatus.VOID]
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set status of sentence data, valid or invalid.
     *
     * @param status [DataStatus.ACTIVE] or [DataStatus.VOID]
     */
    var status: DataStatus
    /**
     * Get the direction to steer to correct error (left/right).
     *
     * @return Direction.LEFT or Direction.RIGHT
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set the direction to steer to correct error (left/right).
     *
     * @param steerTo [Direction.LEFT] or [Direction.RIGHT]
     * @throws IllegalArgumentException If specified direction is any other than
     * defined valid for param `steer`.
     */
    var steerTo: Direction
    /**
     * Get velocity towards destination. Notice that returned value may also be
     * negative if vehicle is moving away from destination.
     *
     * @return Velocity value, in knots (nautical miles per hour).
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set velocity towards destination. Notice that value may also be negative
     * if vehicle is moving away from the destination.
     *
     * @param velocity Velocity, in knots (nautical miles per hour).
     */
    var velocity: Double

    /**
     * Tells if the destination waypoint has been reached or not.
     *
     * @return True if has arrived to waypoint, otherwise false.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If arrival
     * status is not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun hasArrived(): Boolean

    /**
     * Set the destination waypoint.
     *
     * @param dest Waypoint to set
     */
    fun setDestination(dest: Waypoint)
}