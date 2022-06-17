/*
 * RMCParser.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.RMCSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.*
/**
 * RMC sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class RMCParser : PositionParser, RMCSentence {
    /**
     * Creates a new instance of RMCParser.
     *
     * @param nmea RMC sentence String.
     * @throws IllegalArgumentException If specified sentence is invalid.
     */
    constructor(nmea: String) : super(nmea, SentenceId.RMC)

    /**
     * Creates a ZDA parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.RMC, 12)

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#getCorrectedCourse()
	 */
    override val correctedCourse: Double
        get() = course + variation

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#getCourse()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#setCourse(double)
	 */
    override var course: Double
        get() = getDoubleValue(COURSE)
        set(cog) {
            setDegreesValue(COURSE, cog)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DateSentence#getDate()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.DateSentence#setDate(net.sf.marineapi.
	 * nmea.util.Date)
	 */
    override var date: Date
        get() = Date(getStringValue(UTC_DATE))
        set(date) {
            setStringValue(UTC_DATE, date.toString())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#getDirectionOfVariation()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMCSentence#setDirectionOfVariation(net
	 * .sf.marineapi.nmea.util.Direction)
	 */
    override var directionOfVariation: CompassPoint
        get() = CompassPoint.Companion.valueOf(getCharValue(VAR_HEMISPHERE))
        set(dir) {
            require(!(dir != CompassPoint.EAST && dir != CompassPoint.WEST)) { "Invalid variation direction, expected EAST or WEST." }
            setCharValue(VAR_HEMISPHERE, dir.toChar())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#getFaaMode()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMCSentence#setFaaMode(net.sf.marineapi
	 * .nmea.util.FaaMode)
	 */
    override var mode: FaaMode
        get() = FaaMode.Companion.valueOf(getCharValue(MODE))
        set(mode) {
            fieldCount = 12
            setCharValue(MODE, mode.toChar())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.PositionSentence#getPosition()
	 */
    override fun getPosition(): Position {
        return parsePosition(LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#getSpeed()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#setSpeed(double)
	 */
    override var speed: Double
        get() = getDoubleValue(SPEED)
        set(sog) {
            setDoubleValue(SPEED, sog, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#getDataStatus()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.RMCSentence#setDataStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override var status: DataStatus
        get() = DataStatus.Companion.valueOf(getCharValue(DATA_STATUS))
        set(status) {
            setCharValue(DATA_STATUS, status.toChar())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.TimeSentence#getTime()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.TimeSentence#setTime(net.sf.marineapi.
	 * nmea.util.Time)
	 */
    override var time: Time
        get() {
            val str = getStringValue(UTC_TIME)
            return Time(str)
        }
        set(t) {
            setStringValue(UTC_TIME, t.toString())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#getVariation()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.RMCSentence#setVariation(double)
	 */
    override var variation: Double
        get() {
            var variation = getDoubleValue(MAG_VARIATION)
            if (CompassPoint.EAST == directionOfVariation && variation > 0) {
                variation = -variation
            }
            return variation
        }
        set(var) {
            setDegreesValue(MAG_VARIATION, `var`)
        }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.PositionSentence#setPosition(net.sf.marineapi
	 * .nmea.util.Position)
	 */
    override fun setPosition(pos: Position) {
        setPositionValues(pos, LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE)
    }

    companion object {
        private const val UTC_TIME = 0
        private const val DATA_STATUS = 1
        private const val LATITUDE = 2
        private const val LAT_HEMISPHERE = 3
        private const val LONGITUDE = 4
        private const val LON_HEMISPHERE = 5
        private const val SPEED = 6
        private const val COURSE = 7
        private const val UTC_DATE = 8
        private const val MAG_VARIATION = 9
        private const val VAR_HEMISPHERE = 10
        private const val MODE = 11
    }
}