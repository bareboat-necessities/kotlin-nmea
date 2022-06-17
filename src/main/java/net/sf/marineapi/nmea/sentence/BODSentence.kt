/* 
 * BODSentence.java
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

/**
 * True and magnetic bearing from origin to destination in degrees. This
 * sentence is transmitted by a GPS in the GOTO mode (with or without active
 * route).
 *
 *
 * Example:<br></br>
 * `$GPBOD,234.9,T,228.8,M,RUSKI,*1D`
 *
 * @author Kimmo Tuukkanen
 */
interface BODSentence : Sentence {
    /**
     * Get the ID of destination waypoint. This field should be always available
     * in GOTO mode.
     *
     * @return waypoint id
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getDestinationWaypointId(): String?

    /**
     * Get the magnetic bearing from origin to destination.
     *
     *
     * *Notice: The bearing is calculated from the origin when GOTO is
     * activated and it is **not** updated dynamically.*
     *
     * @return magnetic bearing value
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getMagneticBearing(): Double

    /**
     * Get the ID of origin waypoint. This field is available only when route is
     * active.
     *
     * @return waypoint id
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getOriginWaypointId(): String?

    /**
     * Get the true bearing from origin to destination.
     *
     *
     * *Notice: Typically the bearing is calculated when GOTO mode is
     * activated and it is **not** updated dynamically.*
     *
     * @return True bearing
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getTrueBearing(): Double

    /**
     * Sets the destination waypoint ID.
     *
     * @param id ID to set
     */
    fun setDestinationWaypointId(id: String?)

    /**
     * Sets the true bearing from origin to destination, in degrees.
     *
     * @param bearing Bearing value
     * @throws IllegalArgumentException If bearing value out range 0..360
     * degrees.
     */
    fun setMagneticBearing(bearing: Double)

    /**
     * Sets the ID of origin waypoint.
     *
     * @param id ID to set.
     */
    fun setOriginWaypointId(id: String?)

    /**
     * Sets the true bearing from origin to destination, in degrees.
     *
     * @param bearing Bearing value
     * @throws IllegalArgumentException If bearing value out range 0..360
     * degrees.
     */
    fun setTrueBearing(bearing: Double)
}