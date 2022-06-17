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

import net.sf.marineapi.nmea.util.*

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
    /**
     * Set the antenna altitude.
     *
     * @param alt Altitude to set
     */
    var altitude: Double
    /**
     * Gets the altitude units, meters or feet.
     *
     * @return Units enum
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Sets the unit of altitude.
     *
     * @param unit Units to set
     */
    var altitudeUnits: Units
    /**
     * Gets the age of differential GPS data (DGPS).
     *
     * @return Seconds since last valid RTCM transmission
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Sets the age of differential GPS data (DGPS).
     *
     * @param age Seconds since last valid RTCM transmission to set.
     */
    var dgpsAge: Double
    /**
     * Gets the ID of DGPS station.
     *
     * @return Station ID (0000-1024)
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Sets the ID of DGPS station.
     *
     * @param id Station ID to set
     */
    var dgpsStationId: String?
    /**
     * Get the GPS fix quality.
     *
     * @return GpsFixQuality enum
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Sets the GPS fix quality.
     *
     * @param quality Fix quality to set
     */
    var fixQuality: GpsFixQuality
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
    /**
     * Set height/separation of geoid above WGS84 ellipsoid, i.e. difference
     * between WGS-84 earth ellipsoid and mean sea level. Negative values are
     * below WGS-84 ellipsoid.
     *
     * @param height Height value to set
     */
    var geoidalHeight: Double
    /**
     * Get units of height above geoid.
     *
     * @return Units of geoidal height value
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Get unit of height above geoid.
     *
     * @param unit Unit to set
     */
    var geoidalHeightUnits: Units
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
    /**
     * Set the horizontal dilution of precision (HDOP), i.e. the relative
     * accuracy of horizontal position.
     *
     * @param hdop Horizontal dilution
     */
    var horizontalDOP: Double
    /**
     * Get the number of active satellites in use.
     *
     * @return Number of satellites
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Sets the number of active satellites in use.
     *
     * @param count Number of satellites to set.
     * @throws IllegalArgumentException If given count is negative.
     */
    var satelliteCount: Int

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