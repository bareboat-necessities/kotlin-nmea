/*
 * MHUSentence.java
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
 * Relative and absolute humidity with dew point.
 *
 *
 * *Notice: not recommended as of Oct 2008, should use `XDR`
 * instead.*
 *
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.nmea.sentence.XDRSentence
 */
interface MHUSentence : Sentence {
    /**
     * Returns the humidity relative to temperature of air.
     *
     * @return Relative humitidy, percent.
     */
    /**
     * Returns the relative humidity.
     *
     * @param humidity Relative humidity, percent.
     */
    var relativeHumidity: Double
    /**
     * Returns the absolute humidity value.
     *
     * @return Absolute humidity, g/m³.
     */
    /**
     * Returns the absolute humidity value.
     *
     * @param humidity Absolute humidity, percent.
     */
    var absoluteHumidity: Double
    /**
     * Returns the dew point value.
     *
     * @return Dew point, degrees Celcius.
     * @see .getDewPointUnit
     */
    /**
     * Sets the dew point value.
     *
     * @param dewPoint Dew point in degrees Celcius.
     */
    var dewPoint: Double
    /**
     * Returns the unit of dew point temperature, by default degrees Celsius.
     *
     * @return Temperature unit char, defaults to `'c'`.
     */
    /**
     * Sets the unit of dew point temperature, by default degrees Celsius.
     *
     * @param unit Temperature unit char, defaults to `'c'`.
     */
    var dewPointUnit: Char
}