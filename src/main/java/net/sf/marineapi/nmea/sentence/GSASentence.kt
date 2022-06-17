/* 
 * GSASentence.java
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

import net.sf.marineapi.nmea.util.FaaMode
import net.sf.marineapi.nmea.util.GpsFixStatus


/**
 * Precision of GPS fix and list of active satellites. Dilution of precision
 * (DOP) is an indication of the effect of satellite geometry on the accuracy of
 * the fix. It is a unitless number where smaller is better.
 *
 *
 * Example:<br></br>
 * `$GPGSA,A,3,02,,,07,,09,24,26,,,,,1.6,1.6,1.0*3D`
 *
 * @author Kimmo Tuukkanen
 */
interface GSASentence : Sentence {
    /**
     * Get the GPS fix mode; 2D, 3D or no fix.
     *
     * @return GpsFixStatus enum
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getFixStatus(): GpsFixStatus?

    /**
     * Get the horizontal dilution Of precision (HDOP).
     *
     * @return double
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getHorizontalDOP(): Double

    /**
     * Get the FAA operation mode of GPS.
     *
     * @return FaaMode enum
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getMode(): FaaMode?

    /**
     * Get the dilution of precision (PDOP) for position.
     *
     * @return double
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getPositionDOP(): Double

    /**
     * Get list of satellites used for acquiring the GPS fix.
     *
     * @return String array containing satellite IDs.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getSatelliteIds(): Array<String?>?

    /**
     * Get the vertical dilution of precision (VDOP).
     *
     * @return double
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getVerticalDOP(): Double

    /**
     * Set the GPS fix mode; 2D, 3D or no fix.
     *
     * @param status Status to set
     */
    fun setFixStatus(status: GpsFixStatus?)

    /**
     * Set the horizontal dilution of precision (HDOP).
     *
     * @param hdop Precision value to set
     */
    fun setHorizontalDOP(hdop: Double)

    /**
     * Set the FAA operation mode of GPS.
     *
     * @param mode Mode to set
     */
    fun setMode(mode: FaaMode?)

    /**
     * Set the dilution of precision for position.
     *
     * @param pdop Precision value to set
     */
    fun setPositionDOP(pdop: Double)

    /**
     * Set list of satellites used for acquiring the GPS fix.
     *
     * @param ids List of satellite IDs, maximum length of array is 12.
     */
    fun setSatelliteIds(ids: Array<String?>?)

    /**
     * Set the vertical dilution of precision (VDOP).
     *
     * @param vdop Precision value to set
     */
    fun setVerticalDOP(vdop: Double)
}