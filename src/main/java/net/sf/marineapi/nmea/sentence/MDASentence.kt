/*
 * MDASentence.java
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
 * Meteorological Composite - Barometric pressure, air and water temperature,
 * humidity, dew point and wind speed and direction relative to the surface of
 * the earth.
 *
 * @author Richard van Nieuwenhoven
 */
interface MDASentence : Sentence {
    /**
     * Returns the absolute humidity.
     *
     * @return Absolute humidity, percent, to the nearest 0,1 percent. NaN if
     * not available.
     */
    /**
     * Sets the absolute humidity.
     *
     * @param humitidy Humidity percent to set.
     */
    var absoluteHumidity: Double
    /**
     * Returns the air temperature.
     *
     * @return Air temperature, degrees C, to the nearest 0,1 degree C. NaN if
     * not available.
     */
    /**
     * Sets the air temperature.
     *
     * @param temp Temperature to set, degrees Celsius.
     */
    var airTemperature: Double
    /**
     * Returns the dew point.
     *
     * @return Dew point, degrees C, to the nearest 0,1 degree C. NaN if not
     * available.
     */
    /**
     * Sets the dew point temperature.
     *
     * @param dewPoint Dew point in degrees Celsius.
     */
    var dewPoint: Double
    /**
     * Returns the magnetic wind direction.
     *
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     * not available.
     */
    /**
     * Sets the magnetic wind direction.
     *
     * @param direction Direction to set, degrees Magnetic [0..360]
     */
    var magneticWindDirection: Double
    /**
     * Returns the primary barometric pressure.
     *
     * @return Barometric pressure, inches of mercury. NaN if not available.
     */
    /**
     * Sets the primary barometric pressure value.
     *
     * @param pressure Pressure value to set, usually in inches of mercury.
     * @see .setPrimaryBarometricPressureUnit
     */
    var primaryBarometricPressure: Double
    /**
     * Returns the unit of primary barometric pressure.
     *
     * @return B = bar I = inches of mercury (inHg) P = pascal (1 bar = 100000
     * Pa = 29,53 inHg).
     */
    /**
     * Sets the barometric pressure unit.
     *
     * @param unit Pressure unit to set, usually in inches of mercury 'I'.
     * @see .setSecondaryBarometricPressureUnit
     */
    var primaryBarometricPressureUnit: Char
    /**
     * Returns the relative humidity.
     *
     * @return Relative humidity, percent, to the nearest 0,1 percent. NaN if
     * not available.
     */
    /**
     * Sets the relative humidity
     *
     * @param humidity Humidity percent to set.
     */
    var relativeHumidity: Double
    /**
     * Returns the secondary barometric pressure.
     *
     * @return Barometric pressure, bars, to the nearest .001 bar. NaN if not
     * available.
     */
    /**
     * Sets the barometric pressure value.
     *
     * @param pressure Pressure to set, usually in bars.
     */
    var secondaryBarometricPressure: Double
    /**
     * Returns the unit of secondary barometric pressure.
     *
     * @return B = bar I = inches of mercury (inHg) P = pascal (1 bar = 100000
     * Pa = 29,53 inHg). The secondary unit is almost always in bars.
     */
    /**
     * Sets the secondary barometric pressure unit.
     *
     * @param unit Pressure unit set, usually in bars 'B'.
     */
    var secondaryBarometricPressureUnit: Char
    /**
     * Returns the wind direction.
     *
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     * not available.
     */
    /**
     * Sets the True wind direction.
     *
     * @param direction Direction to set, degrees True [0..360]
     */
    var trueWindDirection: Double
    /**
     * Returns the water temperature.
     *
     * @return Water temperature, degrees C. NaN if not available.
     */
    /**
     * Sets the Water temperature.
     *
     * @param temp Temperature in degrees Celsius.
     */
    var waterTemperature: Double
    /**
     * Returns the wind speed.
     *
     * @return Wind speed, meters per second, to the nearest 0,1 m/s. NaN if not
     * available.
     * @see .getWindSpeedKnots
     */
    /**
     * Sets the wind speed.
     *
     * @param speed Wind speed in meters per second.
     * @see .setWindSpeedKnots
     */
    var windSpeed: Double
    /**
     * Returns the wind speed.
     *
     * @return Wind speed, knots. NaN if not available.
     * @see .getWindSpeed
     */
    /**
     * Sets the wind speed, in knots.
     *
     * @param speed Wind speed in knots.
     * @see .setWindSpeed
     */
    var windSpeedKnots: Double
}