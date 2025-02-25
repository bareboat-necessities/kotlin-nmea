/* 
 * HeadingSentence.java
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

/**
 * Interface for sentences that provide vessel's true or magnetic heading.
 *
 * @author Kimmo Tuukkanen
 */
interface HeadingSentence : Sentence {
    /**
     * Returns the vessel's current heading.
     *
     * @return Heading in degrees.
     * @see .isTrue
     */
    fun getHeading(): Double

    /**
     * Tells if the heading returned and set by [.getHeading] and
     * [.setHeading] methods is *true* or *magnetic*
     * .
     *
     * @return `true` if true heading, otherwise `false`
     * for magnetic heading.
     */
    fun isTrue(): Boolean

    /**
     * Sets the heading value.
     *
     * @param hdt Heading in degrees
     * @see .isTrue
     * @throws IllegalArgumentException If heading value out of range [0..360]
     */
    fun setHeading(hdt: Double)
}