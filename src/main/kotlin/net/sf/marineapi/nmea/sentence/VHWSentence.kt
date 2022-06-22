/* 
 * VHWSentence.java
 * Copyright (C) 2011 Warren Zahra, Kimmo Tuukkanen
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
 * Water speed and heading in respect to true and magnetic north.
 *
 *
 * Example:<br></br>`$IIVHW,,,213,M,0.00,N,,K*2F`
 *
 * @author Warren Zahra, Kimmo Tuukkanen
 */
interface VHWSentence : HeadingSentence {
    /**
     * Returns the current magnetic heading.
     *
     * @return Heading in degrees magnetic.
     */
    fun getMagneticHeading(): Double

    /**
     * Returns the current water speed.
     *
     * @return Speed in km/h (kilmetres per hour)
     */
    fun getSpeedKmh(): Double

    /**
     * Returns the current water speed.
     *
     * @return Speed in knots (nautical miles per hour)
     */
    fun getSpeedKnots(): Double

    /**
     * Sets the magnetic heading.
     *
     * @param hdg Heading in degrees magnetic.
     * @throws IllegalArgumentException If value is out of bounds [0..360]
     */
    fun setMagneticHeading(hdg: Double)

    /**
     * Sets the water speed in km/h.
     *
     * @param kmh Speed in kilmetres per hour.
     */
    fun setSpeedKmh(kmh: Double)

    /**
     * Sets the water speed in knots.
     *
     * @param knots Speed in knots (nautical miles per hour)
     */
    fun setSpeedKnots(knots: Double)
}