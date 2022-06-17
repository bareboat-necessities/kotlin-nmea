/*
 * MWDParser.java
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

import net.sf.marineapi.nmea.sentence.MWDSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId

/**
 * Wind speed and direction.
 *
 * @author Richard van Nieuwenhoven
 */
internal class MWDParser : SentenceParser, MWDSentence {
    /**
     * Creates a new instance of MWDParser.
     *
     * @param nmea MWV sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.MWD) {}

    /**
     * Creates a new empty instance of MWDParser.
     *
     * @param talker Talker id to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.MWD, 8) {
        setCharValue(WIND_DIRECTION_TRUE_UNIT, 'T')
        setCharValue(WIND_DIRECTION_MAGNETIC_UNIT, 'M')
        setCharValue(WIND_SPEED_METERS_UNIT, 'M')
        setCharValue(WIND_SPEED_KNOTS_UNIT, 'N')
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#getMagneticWindDirection()
     */
    override fun getMagneticWindDirection(): Double {
        return if (hasValue(WIND_DIRECTION_MAGNETIC) && hasValue(WIND_DIRECTION_MAGNETIC_UNIT) && getStringValue(
                WIND_DIRECTION_MAGNETIC_UNIT
            ).equals("M", ignoreCase = true)
        ) {
            getDoubleValue(WIND_DIRECTION_MAGNETIC)
        } else {
            Double.NaN
        }
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#getTrueWindDirection()
     */
    override fun getTrueWindDirection(): Double {
        return if (hasValue(WIND_DIRECTION_TRUE) && hasValue(WIND_DIRECTION_TRUE_UNIT) && getStringValue(
                WIND_DIRECTION_TRUE_UNIT
            ).equals("T", ignoreCase = true)
        ) {
            getDoubleValue(WIND_DIRECTION_TRUE)
        } else {
            Double.NaN
        }
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#getWindSpeed()
     */
    override fun getWindSpeed(): Double {
        return if (hasValue(WIND_SPEED_METERS) && hasValue(WIND_SPEED_METERS_UNIT) && getStringValue(
                WIND_SPEED_METERS_UNIT
            ).equals("M", ignoreCase = true)
        ) {
            getDoubleValue(WIND_SPEED_METERS)
        } else {
            Double.NaN
        }
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#getWindSpeedKnots()
     */
    override fun getWindSpeedKnots(): Double {
        return if (hasValue(WIND_SPEED_KNOTS) && hasValue(WIND_SPEED_KNOTS_UNIT) && getStringValue(
                WIND_SPEED_KNOTS_UNIT
            ).equals("N", ignoreCase = true)
        ) {
            getDoubleValue(WIND_SPEED_KNOTS)
        } else {
            Double.NaN
        }
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#setMagneticWindDirections(double)
     */
    override fun setMagneticWindDirection(direction: Double) {
        setDegreesValue(WIND_DIRECTION_MAGNETIC, direction)
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#setTrueWindDirection(double)
     */
    override fun setTrueWindDirection(direction: Double) {
        setDegreesValue(WIND_DIRECTION_TRUE, direction)
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#setWindSpeed(double)
     */
    override fun setWindSpeed(speed: Double) {
        setDoubleValue(WIND_SPEED_METERS, speed)
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#setWindSpeedKnots(double)
     */
    override fun setWindSpeedKnots(speed: Double) {
        setDoubleValue(WIND_SPEED_KNOTS, speed)
    }

    companion object {
        /**
         * Wind direction, degrees True, to the nearest 0,1 degree.
         */
        private const val WIND_DIRECTION_TRUE = 0

        /**
         * T = true
         */
        private const val WIND_DIRECTION_TRUE_UNIT = 1

        /**
         * Wind direction, degrees Magnetic, to the nearest 0,1 degree.
         */
        private const val WIND_DIRECTION_MAGNETIC = 2

        /**
         * M = magnetic.
         */
        private const val WIND_DIRECTION_MAGNETIC_UNIT = 3

        /**
         * Wind speed, knots, to the nearest 0,1 knot.
         */
        private const val WIND_SPEED_KNOTS = 4

        /**
         * N = knots.
         */
        private const val WIND_SPEED_KNOTS_UNIT = 5

        /**
         * Wind speed, meters per second, to the nearest 0,1 m/s.
         */
        private const val WIND_SPEED_METERS = 6

        /**
         * M = meters per second
         */
        private const val WIND_SPEED_METERS_UNIT = 7
    }
}