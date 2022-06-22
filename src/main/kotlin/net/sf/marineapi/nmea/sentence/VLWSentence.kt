/*
 * VLWSentence.java
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
 * Distance traveled through water, cumulative and trip.
 *
 *
 * Example:<br></br>
 * `$VWVLW,2.8,N,2.8,N*4C`
 *
 * @author Kimmo Tuukkanen
 */
interface VLWSentence : Sentence {
    /**
     * Returns the total cumulative distance traveled.
     *
     * @return Distance
     * @see .getTotalUnits
     */
    fun getTotal(): Double

    /**
     * Returns the unit of measurement for cumulative total distance.
     *
     * @return Char indicator for unit
     * @see .KM
     *
     * @see .NM
     */
    fun getTotalUnits(): Char

    /**
     * Returns the distance traveled since last reset.
     *
     * @return Trip distance
     * @see .getTripUnits
     */
    fun getTrip(): Double

    /**
     * Returns the unit of measurement for distance since last reset.
     *
     * @return Char indicator for unit
     * @see .KM
     *
     * @see .NM
     */
    fun getTripUnits(): Char

    /**
     * Sets the total cumulative distance traveled.
     *
     * @param distance Total distance to set.
     * @see .setTotalUnits
     */
    fun setTotal(distance: Double)

    /**
     * Sets the units of measure for cumulative total distance.
     *
     * @param unit Unit to set; 'K' for kilomters, 'N' for nautical miles.
     * @throws IllegalArgumentException If trying to set invalid units char.
     * @see .KM
     *
     * @see .NM
     */
    fun setTotalUnits(unit: Char)

    /**
     * Sets the distance traveled since last reset.
     *
     * @param distance Trip distance to set.
     * @see .setTripUnits
     */
    fun setTrip(distance: Double)

    /**
     * Sets the units of measure for distance since last reset.
     *
     * @param unit Unit to set; 'K' for kilomters, 'N' for nautical miles.
     * @throws IllegalArgumentException If trying to set invalid units char.
     * @see .KM
     *
     * @see .NM
     */
    fun setTripUnits(unit: Char)

    companion object {
        /** Kilometers  */
        const val KM = 'K'

        /** Nautical miles  */
        const val NM = 'N'
    }
}