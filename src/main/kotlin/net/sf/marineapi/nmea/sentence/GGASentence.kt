/* 
 * GGASentence.java
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

import net.sf.marineapi.nmea.util.GpsFixQuality
import net.sf.marineapi.nmea.util.Units


/**
 * Global Positioning System fix data. Current position, time and other fix
 * related data for a GPS receiver.
 *
 *
 * Example:<br></br>
 * `$GPGGA,120044,6011.552,N,02501.941,E,1,00,2.0,28.0,M,19.6,M,,*79`
 *
 * @author Kimmo Tuukkanen
 */
interface GGASentence : PositionSentence, TimeSentence {
    /**
     * Get antenna altitude above mean sea level.
     *
     * @return Altitude value
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getAltitude(): Double

    /**
     * Gets the altitude units, meters or feet.
     *
     * @return Units enum
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getAltitudeUnits(): Units?

    /**
     * Gets the age of differential GPS data (DGPS).
     *
     * @return Seconds since last valid RTCM transmission
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getDgpsAge(): Double

    /**
     * Gets the ID of DGPS station.
     *
     * @return Station ID (0000-1024)
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getDgpsStationId(): String?

    /**
     * Get the GPS fix quality.
     *
     * @return GpsFixQuality enum
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getFixQuality(): GpsFixQuality?

    /**
     * Get height/separation of geoid above WGS84 ellipsoid, i.e. difference
     * between WGS-84 earth ellipsoid and mean sea level. Negative values are
     * below WGS-84 ellipsoid.
     *
     * @return Height value
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getGeoidalHeight(): Double

    /**
     * Get units of height above geoid.
     *
     * @return Units of geoidal height value
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getGeoidalHeightUnits(): Units?

    /**
     * Get the horizontal dilution of precision (HDOP), i.e. the relative
     * accuracy of horizontal position.
     *
     * @return Horizontal dilution
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getHorizontalDOP(): Double

    /**
     * Get the number of active satellites in use.
     *
     * @return Number of satellites
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getSatelliteCount(): Int

    /**
     * Set the antenna altitude.
     *
     * @param alt Altitude to set
     */
    fun setAltitude(alt: Double)

    /**
     * Sets the unit of altitude.
     *
     * @param unit Units to set
     */
    fun setAltitudeUnits(unit: Units?)

    /**
     * Sets the age of differential GPS data (DGPS).
     *
     * @param age Seconds since last valid RTCM transmission to set.
     */
    fun setDgpsAge(age: Double)

    /**
     * Sets the ID of DGPS station.
     *
     * @param id Station ID to set
     */
    fun setDgpsStationId(id: String?)

    /**
     * Sets the GPS fix quality.
     *
     * @param quality Fix quality to set
     */
    fun setFixQuality(quality: GpsFixQuality?)

    /**
     * Set height/separation of geoid above WGS84 ellipsoid, i.e. difference
     * between WGS-84 earth ellipsoid and mean sea level. Negative values are
     * below WGS-84 ellipsoid.
     *
     * @param height Height value to set
     */
    fun setGeoidalHeight(height: Double)

    /**
     * Get unit of height above geoid.
     *
     * @param unit Unit to set
     */
    fun setGeoidalHeightUnits(unit: Units?)

    /**
     * Set the horizontal dilution of precision (HDOP), i.e. the relative
     * accuracy of horizontal position.
     *
     * @param hdop Horizontal dilution
     */
    fun setHorizontalDOP(hdop: Double)

    /**
     * Sets the number of active satellites in use.
     *
     * @param count Number of satellites to set.
     * @throws IllegalArgumentException If given count is negative.
     */
    fun setSatelliteCount(count: Int)

    companion object {
        /**
         * Altitude presented in meters.
         */
        const val ALT_UNIT_METERS = 'M'

        /**
         * Altitude presented in feet.
         */
        const val ALT_UNIT_FEET = 'f'
    }
}