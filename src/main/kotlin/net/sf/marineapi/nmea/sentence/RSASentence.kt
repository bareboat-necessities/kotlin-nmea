/*
 * RSASentence.java
 * Copyright (C) 2014 Lázár József, Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Side

/**
 *
 *
 * Rudder angle, measured in degrees. Negative value represents port side,
 * positive starboard side turn. May contain value for both port and starboard
 * rudder. [Side.PORT] is used for vessels with single rudder.
 *
 *
 *
 * Example:<br></br>
 * `$IIRSA,9,A,,*38`
 *
 *
 * @author Lázár József, Kimmo Tuukkanen
 */
interface RSASentence : Sentence {
    /**
     * Returns the rudder angle for specified side.
     *
     * @param side Rudder side
     * @return Rudder angle in degrees.
     */
    fun getRudderAngle(side: Side): Double

    /**
     * Sets the rudder's angle for specified side.
     *
     * @param side Rudder side
     * @param angle Rudder angle in degrees
     */
    fun setRudderAngle(side: Side, angle: Double)

    /**
     * Returns the data status (valid/invalid) for specified side.
     *
     * @param side Rudder side
     * @return Data status
     */
    fun getStatus(side: Side): DataStatus

    /**
     * Set data status for specified side.
     * @param side Rudder side
     * @param status Data status to set
     */
    fun setStatus(side: Side, status: DataStatus)
}