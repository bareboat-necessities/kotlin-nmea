/*
 * MMBSentence.java
 * Copyright (C) 2016 Kimmo Tuukkanen
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
 * Barometer - Barometric pressure in bars and inches of mercury.
 *
 *
 * *Notice: not recommended as of Oct 2008, should use `XDR`
 * instead.*
 *
 *
 * Example:<br></br>
 * `$IIMMB,29.9870,I,1.0154,B*75`
 *
 * @author Kimmo Tuukkanen
 */
interface MMBSentence : Sentence {
    /**
     * Returns the barometric pressure in inches of mercury.
     *
     * @return Barometric pressure, inHg.
     */
    fun getInchesOfMercury(): Double

    /**
     * Returns the barometric pressure in bars.
     *
     * @return Barometric pressure, bars.
     */
    fun getBars(): Double

    /**
     * Sets the barometric pressure in inches of mercury.
     *
     * @param inhg Barometric pressure, inHg.
     */
    fun setInchesOfMercury(inhg: Double)

    /**
     * Sets the barometric pressure in bars.
     *
     * @param bars Barometric pressure, bars.
     */
    fun setBars(bars: Double)
}