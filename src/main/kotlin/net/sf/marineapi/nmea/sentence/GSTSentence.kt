/* 
 * GSTSentence.java
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
 * GPS Pseudorange Noise Statistics.
 *
 *
 * Example:<br></br>
 * `$GPGST,172814.0,0.006,0.023,0.020,273.6,0.023,0.020,0.031*6A`
 *
 * @author Tero Laitinen
 */
interface GSTSentence : TimeSentence {
    /**
     * Get total RMS standard deviation of ranges inputs to the navigation
     * solution.
     *
     * @return RMS value
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getPseudoRangeResidualsRMS(): Double

    /**
     * Get standard deviation (meters) of semi-major axis of error ellipse.
     *
     * @return error ellipse error value
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getSemiMajorError(): Double

    /**
     * Get standard deviation (meters) of semi-minor axis of error ellipse.
     *
     * @return error ellipse error value
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getSemiMinorError(): Double

    /**
     * Get error ellipse orientation, degrees from true north
     *
     * @return error ellipse orientation
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getErrorEllipseOrientation(): Double

    /**
     * Get standard deviation (meters) of latitude error.
     *
     * @return latitude 1 sigma error
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getLatitudeError(): Double

    /**
     * Get standard deviation (meters) of longitude error.
     *
     * @return longitude error
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getLongitudeError(): Double

    /**
     * Get standard deviation (meters) of altitude error.
     *
     * @return altitude error
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getAltitudeError(): Double


    /**
     * Sets the RMS value of the pseudorange residuals.
     *
     * @param rms RMS value of the pseudorange residuals
     */
    fun setPseudoRangeResidualsRMS(rms: Double)

    /**
     * Sets the standard deviation (meters) of semi-major axis of error ellipse.
     *
     * @param error semi-major error ellipse error
     */
    fun setSemiMajorError(error: Double)

    /**
     * Sets the standard deviation (meters) of semi-minor axis of error ellipse.
     *
     * @param error semi-minor error ellipse error
     */
    fun setSemiMinorError(error: Double)

    /**
     * Sets the error ellipse orientation, degrees from true north.
     *
     * @param orientation error ellipse orientation
     */
    fun setErrorEllipseOrientation(orientation: Double)

    /**
     * Sets the standard deviation (meters) of latitude error.
     *
     * @param error latitude 1 sigma error
     */
    fun setLatitudeError(error: Double)

    /**
     * Sets the standard deviation (meters) of longitude error.
     *
     * @param error longitude 1 sigma error
     */
    fun setLongitudeError(error: Double)

    /**
     * Sets the standard deviation (meters) of altitude error.
     *
     * @param error height 1 sigma error
     */
    fun setAltitudeError(error: Double)
}