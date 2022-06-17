/* 
 * MWVSentence.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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
 * Wind speed and angle. Speed in km/h, m/s, or knots. Wind angle is given in
 * degrees relative to bow or true north.
 *
 *
 * Example:<br></br>`$IIMWV,199,R,5.9,N,A*2E`
 *
 * @author Kimmo Tuukkanen
 */
interface MWVSentence : Sentence {
    /**
     * Get wind angle.
     *
     * @return Wind angle in degrees.
     */
    /**
     * Set wind angle.
     *
     * @param angle Wind angle in degrees.
     * @see .setTrue
     */
    var angle: Double
    /**
     * Returns the wind speed.
     *
     * @return Wind speed value
     */
    /**
     * Set the wind speed value.
     *
     * @param speed Wind speed to set.
     */
    var speed: Double
    /**
     * Returns the wind speed unit.
     *
     * @return [Units.METER] for meters per second, [Units.KILOMETERS] for
     * kilometers per hour and [Units.NAUTICAL_MILES] for knots.
     */
    /**
     * Set wind speed unit.
     *
     * @param unit [Units.METER] for meters per second, [Units.KILOMETERS]
     * for kilometers per hour and [Units.NAUTICAL_MILES] for knots.
     * @throws IllegalArgumentException If trying to set invalid unit
     */
    var speedUnit: Units
    /**
     * Get data validity status.
     *
     * @return Data status
     */
    /**
     * Set data validity status.
     *
     * @param status Data status to set.
     */
    var status: DataStatus

    /**
     * Tells if the angle is relative or true.
     *
     * @return True if relative to true north, otherwise false (relative to bow)
     */
    val isTrue: Boolean

    /**
     * Set angle to relative or true.
     *
     * @param isTrue True for true angle, false for relative to bow.
     * @see .setAngle
     */
    fun setTrue(isTrue: Boolean)
}