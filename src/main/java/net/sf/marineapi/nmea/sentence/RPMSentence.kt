/*
 * RPMSentence.java
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

import net.sf.marineapi.nmea.util.DataStatus

/**
 * Revolutions, measured from shaft or engine.
 *
 * @author Kimmo Tuukkanen
 */
interface RPMSentence : Sentence {
    /**
     * Returns the engine or shaft number/id.
     *
     * @return Engine of shaft number
     */
    /**
     * Sets the engine or shaft number/id.
     *
     * @param id ID to set.
     */
    var id: Int
    /**
     * Returns the propeller pitch, % of maximum.
     *
     * @return Pitch value, negative values denote astern.
     */
    /**
     * Sets the propeller pitch, % of maximum.
     *
     * @param pitch Pitch value to set, negative values denote astern.
     */
    var pitch: Double
    /**
     * Returns the revolutions value.
     *
     * @return Speed, revolutions per minute.
     */
    /**
     * Sets the revolutions value.
     *
     * @param rpm Revolutions per minute.
     */
    var rPM: Double
    /**
     * Returns the measurement source, engine or shaft.
     *
     * @return 'E' for engine, 'S' for shaft.
     */
    /**
     * Sets the source indicator, engine or shaft.
     *
     * @param source 'E' for engine or 'S' for shaft.
     * @throws IllegalArgumentException If specified char is not 'E' or 'S'.
     */
    var source: Char
    /**
     * Returns the data validity status.
     *
     * @return DataStatus
     */
    /**
     * Sets the data validity status.
     *
     * @param status DataStatus to set.
     */
    var status: DataStatus

    /**
     * Tells if the data source is engine.
     *
     * @return True if engine, otherwise false.
     */
    val isEngine: Boolean

    /**
     * Tells if the data source is shaft.
     *
     * @return True for shaft, otherwise false.
     */
    val isShaft: Boolean

    companion object {
        /** Source indicator for engine  */
        const val ENGINE = 'E'

        /** Source indicator for shaft  */
        const val SHAFT = 'S'
    }
}