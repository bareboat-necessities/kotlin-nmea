/*
 * MDAParser.java
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
package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.MDASentenceimport

net.sf.marineapi.nmea.sentence.TalkerId
/**
 * Meteorological Composite - Barometric pressure, air and water temperature,
 * humidity, dew point and wind speed and direction relative to the surface of
 * the earth.
 *
 * @author Richard van Nieuwenhoven
 */
internal class MDAParser : SentenceParser, MDASentence {
    /**
     * Creates a new instance of MWVParser.
     *
     * @param nmea
     * MWV sentence String
     */
    constructor(nmea: String) : super(nmea, MDA_SENTENCE_ID) {}

    /**
     * Creates a new empty instance of MWVParser.
     *
     * @param talker
     * Talker id to set
     */
    constructor(talker: TalkerId?) : super(talker, MDA_SENTENCE_ID, 20) {
        setCharValue(AIR_TEMPERATURE_UNIT, 'C')
        setCharValue(WATER_TEMPERATURE_UNIT, 'C')
        setCharValue(DEW_POINT_UNIT, 'C')
        setCharValue(WIND_DIRECTION_TRUE_UNIT, 'T')
        setCharValue(WIND_DIRECTION_MAGNETIC_UNIT, 'M')
        setCharValue(WIND_SPEED_KNOTS_UNIT, 'K')
        setCharValue(WIND_SPEED_METERS_UNIT, 'M')
        setCharValue(PRIMARY_BAROMETRIC_PRESSURE_UNIT, 'I')
        setCharValue(SECONDARY_BAROMETRIC_PRESSURE_UNIT, 'B')
    }

    override var absoluteHumidity: Double
        get() = if (hasValue(ABSOLUTE_HUMIDITY)) {
            getDoubleValue(ABSOLUTE_HUMIDITY)
        } else {
            Double.NaN
        }
        set(humitidy) {
            setDoubleValue(ABSOLUTE_HUMIDITY, humitidy)
        }
    override var airTemperature: Double
        get() = if (hasValue(AIR_TEMPERATURE)) {
            getDoubleValue(AIR_TEMPERATURE)
        } else {
            Double.NaN
        }
        set(temp) {
            setDoubleValue(AIR_TEMPERATURE, temp)
        }
    override var dewPoint: Double
        get() = if (hasValue(DEW_POINT)) {
            getDoubleValue(DEW_POINT)
        } else {
            Double.NaN
        }
        set(dewPoint) {
            setDoubleValue(DEW_POINT, dewPoint)
        }
    override var magneticWindDirection: Double
        get() = if (hasValue(WIND_DIRECTION_MAGNETIC)) {
            getDoubleValue(WIND_DIRECTION_MAGNETIC)
        } else {
            Double.NaN
        }
        set(direction) {
            setDoubleValue(WIND_DIRECTION_MAGNETIC, direction)
        }
    override var primaryBarometricPressure: Double
        get() = if (hasValue(PRIMARY_BAROMETRIC_PRESSURE)) {
            getDoubleValue(PRIMARY_BAROMETRIC_PRESSURE)
        } else {
            Double.NaN
        }
        set(pressure) {
            setDoubleValue(PRIMARY_BAROMETRIC_PRESSURE, pressure)
        }
    override var primaryBarometricPressureUnit: Char
        get() = getCharValue(PRIMARY_BAROMETRIC_PRESSURE_UNIT)
        set(unit) {
            setCharValue(PRIMARY_BAROMETRIC_PRESSURE_UNIT, unit)
        }
    override var relativeHumidity: Double
        get() = if (hasValue(RELATIVE_HUMIDITY)) {
            getDoubleValue(RELATIVE_HUMIDITY)
        } else {
            Double.NaN
        }
        set(humidity) {
            setDoubleValue(RELATIVE_HUMIDITY, humidity)
        }
    override var secondaryBarometricPressure: Double
        get() = if (hasValue(SECONDARY_BAROMETRIC_PRESSURE)) {
            getDoubleValue(SECONDARY_BAROMETRIC_PRESSURE)
        } else {
            Double.NaN
        }
        set(pressure) {
            setDoubleValue(SECONDARY_BAROMETRIC_PRESSURE, pressure)
        }
    override var secondaryBarometricPressureUnit: Char
        get() = getCharValue(SECONDARY_BAROMETRIC_PRESSURE_UNIT)
        set(unit) {
            setCharValue(SECONDARY_BAROMETRIC_PRESSURE_UNIT, unit)
        }
    override var trueWindDirection: Double
        get() = if (hasValue(WIND_DIRECTION_TRUE)) {
            getDoubleValue(WIND_DIRECTION_TRUE)
        } else {
            Double.NaN
        }
        set(direction) {
            setDoubleValue(WIND_DIRECTION_TRUE, direction)
        }
    override var waterTemperature: Double
        get() = if (hasValue(WATER_TEMPERATURE)) {
            getDoubleValue(WATER_TEMPERATURE)
        } else {
            Double.NaN
        }
        set(temp) {
            setDoubleValue(WATER_TEMPERATURE, temp)
        }
    override var windSpeed: Double
        get() = if (hasValue(WIND_SPEED_METERS)) {
            getDoubleValue(WIND_SPEED_METERS)
        } else {
            Double.NaN
        }
        set(speed) {
            setDoubleValue(WIND_SPEED_METERS, speed)
        }
    override var windSpeedKnots: Double
        get() = if (hasValue(WIND_SPEED_KNOTS)) {
            getDoubleValue(WIND_SPEED_KNOTS)
        } else {
            Double.NaN
        }
        set(speed) {
            setDoubleValue(WIND_SPEED_KNOTS, speed)
        }

    companion object {
        const val MDA_SENTENCE_ID = "MDA"

        /**
         * Barometric pressure, inches of mercury, to the nearest 0,01 inch.
         */
        private const val PRIMARY_BAROMETRIC_PRESSURE = 0

        /**
         * I = inches of mercury (inHg) P = pascal (1 bar = 100000 Pa = 29,53 inHg).
         */
        private const val PRIMARY_BAROMETRIC_PRESSURE_UNIT = 1

        /**
         * Barometric pressure, bars, to the nearest .001 bar.
         */
        private const val SECONDARY_BAROMETRIC_PRESSURE = 2

        /**
         * B = bars.
         */
        private const val SECONDARY_BAROMETRIC_PRESSURE_UNIT = 3

        /**
         * Air temperature, degrees C, to the nearest 0,1 degree C.
         */
        private const val AIR_TEMPERATURE = 4

        /**
         * C = degrees C.
         */
        private const val AIR_TEMPERATURE_UNIT = 5

        /**
         * Water temperature, degrees C.
         */
        private const val WATER_TEMPERATURE = 6

        /**
         * C = degrees C.
         */
        private const val WATER_TEMPERATURE_UNIT = 7

        /**
         * Relative humidity, percent, to the nearest 0,1 percent.
         */
        private const val RELATIVE_HUMIDITY = 8

        /**
         * Absolute humidity, percent .
         */
        private const val ABSOLUTE_HUMIDITY = 9

        /**
         * Dew point, degrees C, to the nearest 0,1 degree C.
         */
        private const val DEW_POINT = 10

        /**
         * C = degrees C.
         */
        private const val DEW_POINT_UNIT = 11

        /**
         * Wind direction, degrees True, to the nearest 0,1 degree.
         */
        private const val WIND_DIRECTION_TRUE = 12

        /**
         * T = true
         */
        private const val WIND_DIRECTION_TRUE_UNIT = 13

        /**
         * Wind direction, degrees Magnetic, to the nearest 0,1 degree.
         */
        private const val WIND_DIRECTION_MAGNETIC = 14

        /**
         * M = magnetic.
         */
        private const val WIND_DIRECTION_MAGNETIC_UNIT = 15

        /**
         * Wind speed, knots, to the nearest 0,1 knot.
         */
        private const val WIND_SPEED_KNOTS = 16

        /**
         * N = knots.
         */
        private const val WIND_SPEED_KNOTS_UNIT = 17

        /**
         * Wind speed, meters per second, to the nearest 0,1 m/s.
         */
        private const val WIND_SPEED_METERS = 18

        /**
         * M = meters per second
         */
        private const val WIND_SPEED_METERS_UNIT = 19
    }
}