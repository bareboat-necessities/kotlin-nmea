/*
 * MWDSentence.java
 * Copyright (C) 2015 INDI for Java NMEA 0183 stream driver
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
 * Wind speed and true/magnetic direction, speed given in meters per second and
 * knots.
 *
 * @author Richard van Nieuwenhoven
 */
interface MWDSentence : Sentence {
    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     * not available.
     */
    /**
     * Sets the magnetic wind direction.
     *
     * @param direction Wind direction in degrees [0..360]
     */
    var magneticWindDirection: Double
    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     * not available.
     */
    /**
     * Sets the true wind direction.
     *
     * @param direction Wind direction in degrees [0..360].
     */
    var trueWindDirection: Double
    /**
     * @return Wind speed, meters per second, to the nearest 0,1 m/s. NaN if not
     * available.
     */
    /**
     * Sets the wind speed in meters per second.
     *
     * @param speed Wind speed to set.
     */
    var windSpeed: Double
    /**
     * @return Wind speed, in knots, to the nearest 0,1 m/s. NaN if not
     * available.
     */
    /**
     * Sets the wind speed in knots.
     *
     * @param speed Wind speed to set.
     */
    var windSpeedKnots: Double
}