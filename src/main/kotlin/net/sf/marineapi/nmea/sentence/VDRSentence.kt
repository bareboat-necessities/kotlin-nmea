/*
 * VDRSentence.java
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
 * Set and drift, true/magnetic direction and speed of current.
 *
 * @author Kimmo Tuukkanen
 */
interface VDRSentence : Sentence {
    /**
     * Returns the magnetic current direction.
     *
     * @return Direction in degrees
     * @see .getTrueDirection
     */
    fun getMagneticDirection(): Double

    /**
     * Returns the current flow speed.
     *
     * @return Speed in knots
     */
    fun getSpeed(): Double

    /**
     * Returns the true direction of current.
     *
     * @return Direction in degrees
     * @see .getMagneticDirection
     */
    fun getTrueDirection(): Double

    /**
     * Sets the magnetic direction of current.
     *
     * @param direction Direction to set, in degrees.
     */
    fun setMagneticDirection(direction: Double)

    /**
     * Sets the current flow speed.
     *
     * @param speed Speed in knots
     */
    fun setSpeed(speed: Double)

    /**
     * Sets the true direction of current.
     *
     * @param direction Direction in degrees
     * @see .setMagneticDirection
     */
    fun setTrueDirection(direction: Double)
}