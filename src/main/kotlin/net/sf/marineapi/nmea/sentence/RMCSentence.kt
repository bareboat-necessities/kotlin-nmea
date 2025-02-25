/* 
 * RMCSentence.java
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

import net.sf.marineapi.nmea.util.CompassPoint
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.FaaMode


/**
 * Recommended minimum navigation information type C. Current time and date,
 * position, speed over ground, true course over ground and magnetic variation.
 *
 *
 * Example:<br></br>
 * `$GPRMC,120044,A,6011.552,N,02501.941,E,000.0,360.0,160705,006.1,E*7C`
 *
 * @author Kimmo Tuukkanen
 */
interface RMCSentence : PositionSentence, TimeSentence, DateSentence {
    /**
     * Get the corrected course over ground. Correction is done by subtracting
     * or adding the magnetic variation from true course (easterly variation
     * subtracted and westerly added).
     *
     * @return Corrected true course
     * @see .getCourse
     * @see .getVariation
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If course or
     * variation data is not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If course or variation
     * field contains unexpected or illegal value.
     */
    fun getCorrectedCourse(): Double

    /**
     * Get true course over ground (COG).
     *
     * @return True course in degrees
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getCourse(): Double

    /**
     * Get the direction of magnetic variation; east or west.
     *
     * @return Direction.EAST or Direction.WEST
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getDirectionOfVariation(): CompassPoint?

    /**
     * Get the FAA operating mode for GPS.
     *
     * @return FaaMode enum
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getMode(): FaaMode?

    /**
     * Get current speed over ground (SOG).
     *
     * @return Speed in knots (nautical miles per hour).
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getSpeed(): Double

    /**
     * Gets the data status, valid or invalid.
     *
     * @return [DataStatus.ACTIVE] or [DataStatus.VOID]
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getStatus(): DataStatus?

    /**
     * Get the magnetic variation. Easterly variation subtracts from true
     * course, and is thus returned as negative value. Otherwise, the value is
     * positive.
     *
     * @return Magnetic variation in degrees
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getVariation(): Double

    /**
     * Set true course over ground (COG).
     *
     * @param cog True course in degrees
     */
    fun setCourse(cog: Double)

    /**
     * Set the direction of magnetic variation, east or west.
     *
     * @param dir [CompassPoint.EAST] or [CompassPoint.WEST]
     * @throws IllegalArgumentException If specified Direction is other than
     * defined as valid for param `dir`.
     */
    fun setDirectionOfVariation(dir: CompassPoint?)

    /**
     * Set the FAA operation mode of GPS.
     *
     * @param mode FaaMode enum to set
     */
    fun setMode(mode: FaaMode?)

    /**
     * Set current speed over ground (SOG).
     *
     * @param sog Speed in knots (nautical miles per hour).
     */
    fun setSpeed(sog: Double)

    /**
     * Set the data status, valid or invalid.
     *
     * @param status [DataStatus.ACTIVE] or [DataStatus.VOID]
     */
    fun setStatus(status: DataStatus?)

    /**
     * Set the magnetic variation.
     *
     * @param var Magnetic variation in degrees
     */
    fun setVariation(`var`: Double)
}