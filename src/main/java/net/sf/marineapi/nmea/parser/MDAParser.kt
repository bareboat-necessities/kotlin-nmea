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

import net.sf.marineapi.nmea.sentence.MDASentence
import net.sf.marineapi.nmea.sentence.TalkerId

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

    override fun getAbsoluteHumidity(): Double {
        return if (hasValue(ABSOLUTE_HUMIDITY)) {
            getDoubleValue(ABSOLUTE_HUMIDITY)
        } else {
            Double.NaN
        }
    }

    override fun getAirTemperature(): Double {
        return if (hasValue(AIR_TEMPERATURE)) {
            getDoubleValue(AIR_TEMPERATURE)
        } else {
            Double.NaN
        }
    }

    override fun getDewPoint(): Double {
        return if (hasValue(DEW_POINT)) {
            getDoubleValue(DEW_POINT)
        } else {
            Double.NaN
        }
    }

    override fun getMagneticWindDirection(): Double {
        return if (hasValue(WIND_DIRECTION_MAGNETIC)) {
            getDoubleValue(WIND_DIRECTION_MAGNETIC)
        } else {
            Double.NaN
        }
    }

    override fun getPrimaryBarometricPressure(): Double {
        return if (hasValue(PRIMARY_BAROMETRIC_PRESSURE)) {
            getDoubleValue(PRIMARY_BAROMETRIC_PRESSURE)
        } else {
            Double.NaN
        }
    }

    override fun getPrimaryBarometricPressureUnit(): Char {
        return getCharValue(PRIMARY_BAROMETRIC_PRESSURE_UNIT)
    }

    override fun getRelativeHumidity(): Double {
        return if (hasValue(RELATIVE_HUMIDITY)) {
            getDoubleValue(RELATIVE_HUMIDITY)
        } else {
            Double.NaN
        }
    }

    override fun getSecondaryBarometricPressure(): Double {
        return if (hasValue(SECONDARY_BAROMETRIC_PRESSURE)) {
            getDoubleValue(SECONDARY_BAROMETRIC_PRESSURE)
        } else {
            Double.NaN
        }
    }

    override fun getSecondaryBarometricPressureUnit(): Char {
        return getCharValue(SECONDARY_BAROMETRIC_PRESSURE_UNIT)
    }

    override fun getTrueWindDirection(): Double {
        return if (hasValue(WIND_DIRECTION_TRUE)) {
            getDoubleValue(WIND_DIRECTION_TRUE)
        } else {
            Double.NaN
        }
    }

    override fun getWaterTemperature(): Double {
        return if (hasValue(WATER_TEMPERATURE)) {
            getDoubleValue(WATER_TEMPERATURE)
        } else {
            Double.NaN
        }
    }

    override fun getWindSpeed(): Double {
        return if (hasValue(WIND_SPEED_METERS)) {
            getDoubleValue(WIND_SPEED_METERS)
        } else {
            Double.NaN
        }
    }

    override fun getWindSpeedKnots(): Double {
        return if (hasValue(WIND_SPEED_KNOTS)) {
            getDoubleValue(WIND_SPEED_KNOTS)
        } else {
            Double.NaN
        }
    }

    override fun setAbsoluteHumidity(humitidy: Double) {
        setDoubleValue(ABSOLUTE_HUMIDITY, humitidy)
    }

    override fun setAirTemperature(temp: Double) {
        setDoubleValue(AIR_TEMPERATURE, temp)
    }

    override fun setDewPoint(dewPoint: Double) {
        setDoubleValue(DEW_POINT, dewPoint)
    }

    override fun setMagneticWindDirection(direction: Double) {
        setDoubleValue(WIND_DIRECTION_MAGNETIC, direction)
    }

    override fun setPrimaryBarometricPressure(pressure: Double) {
        setDoubleValue(PRIMARY_BAROMETRIC_PRESSURE, pressure)
    }

    override fun setPrimaryBarometricPressureUnit(unit: Char) {
        setCharValue(PRIMARY_BAROMETRIC_PRESSURE_UNIT, unit)
    }

    override fun setRelativeHumidity(humidity: Double) {
        setDoubleValue(RELATIVE_HUMIDITY, humidity)
    }

    override fun setSecondaryBarometricPressure(pressure: Double) {
        setDoubleValue(SECONDARY_BAROMETRIC_PRESSURE, pressure)
    }

    override fun setSecondaryBarometricPressureUnit(unit: Char) {
        setCharValue(SECONDARY_BAROMETRIC_PRESSURE_UNIT, unit)
    }

    override fun setTrueWindDirection(direction: Double) {
        setDoubleValue(WIND_DIRECTION_TRUE, direction)
    }

    override fun setWaterTemperature(temp: Double) {
        setDoubleValue(WATER_TEMPERATURE, temp)
    }

    override fun setWindSpeed(speed: Double) {
        setDoubleValue(WIND_SPEED_METERS, speed)
    }

    override fun setWindSpeedKnots(speed: Double) {
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