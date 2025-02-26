/*
 * DTMSentence.java
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

/**
 *
 * Datum reference.
 *
 *
 * Example:<br></br>
 * `$GPDTM,W84,,0.000000,N,0.000000,E,0.0,W84*6F`
 *
 * @author Kimmo Tuukkanen
 */
interface DTMSentence : Sentence {
    /**
     * Returns the altitude offset.
     *
     * @return Altitude offset, in meters.
     */
    fun getAltitudeOffset(): Double

    /**
     * Returns the local datum code.
     *
     * @return Datum code
     */
    fun getDatumCode(): String?

    /**
     * Returns the local datum subcode, may be blank.
     *
     * @return Datum subcode
     */
    fun getDatumSubCode(): String?

    /**
     * Returns the latitude offset. Positive values depict northern offset,
     * negative for southern.
     *
     * @return Offset value in minutes.
     */
    fun getLatitudeOffset(): Double

    /**
     * Returns the longitude offset. Positive values for east, negative west.
     *
     * @return Longitude offset in minutes.
     */
    fun getLongitudeOffset(): Double

    /**
     * Returns the datum name, e.g. "W84" for WGS84 used by GPS.
     *
     * @return Datum name
     */
    fun getName(): String?

    /**
     * Sets the local datum code.
     *
     * @param code Code to set
     */
    fun setDatumCode(code: String?)

    /**
     * Sets the local datum code, may be blank.
     *
     * @param code Code to set
     */
    fun setDatumSubCode(code: String?)

    /**
     * Sets the latitude offset. Positive values depict northern offset,
     * negative for southern.
     *
     * @param offset Latitude offset in minutes.
     */
    fun setLatitudeOffset(offset: Double)

    /**
     * Sets the longitude offset. Positive values for east, negative west.
     *
     * @param offset Longitude offset in minutes.
     */
    fun setLongitudeOffset(offset: Double)

    /**
     * Sets the datum name.
     *
     * @param name Name to set.
     */
    fun setName(name: String?)
}