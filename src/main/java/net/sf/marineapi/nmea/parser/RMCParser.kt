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

import net.sf.marineapi.nmea.sentence.RMCSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.*


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

    override fun getCorrectedCourse(): Double {
        return getCourse() + getVariation()
    }

    override fun getCourse(): Double {
        return getDoubleValue(COURSE)
    }

    override fun getDate(): Date {
        return Date(getStringValue(UTC_DATE))
    }

    override fun getDirectionOfVariation(): CompassPoint {
        return CompassPoint.valueOf(getCharValue(VAR_HEMISPHERE))
    }

    override fun getMode(): FaaMode {
        return FaaMode.valueOf(getCharValue(MODE))
    }

    override fun getPosition(): Position {
        return parsePosition(LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE)
    }

    override fun getSpeed(): Double {
        return getDoubleValue(SPEED)
    }

    override fun getStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(DATA_STATUS))
    }

    override fun getTime(): Time {
        val str = getStringValue(UTC_TIME)
        return Time(str)
    }

    override fun getVariation(): Double {
        var variation = getDoubleValue(MAG_VARIATION)
        if (CompassPoint.EAST == getDirectionOfVariation() && variation > 0) {
            variation = -variation
        }
        return variation
    }

    override fun setCourse(cog: Double) {
        setDegreesValue(COURSE, cog)
    }

    override fun setDate(date: Date?) {
        setStringValue(UTC_DATE, date.toString())
    }

    override fun setDirectionOfVariation(dir: CompassPoint?) {
        require(!(dir != CompassPoint.EAST && dir != CompassPoint.WEST)) { "Invalid variation direction, expected EAST or WEST." }
        setCharValue(VAR_HEMISPHERE, dir!!.toChar())
    }

    override fun setMode(mode: FaaMode?) {
        setFieldCount(12)
        setCharValue(MODE, mode!!.toChar())
    }

    override fun setPosition(pos: Position) {
        setPositionValues(pos, LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE)
    }

    override fun setSpeed(sog: Double) {
        setDoubleValue(SPEED, sog, 1, 1)
    }

    override fun setStatus(status: DataStatus?) {
        setCharValue(DATA_STATUS, status!!.toChar())
    }

    override fun setTime(t: Time?) {
        setStringValue(UTC_TIME, t.toString())
    }

    override fun setVariation(`var`: Double) {
        setDegreesValue(MAG_VARIATION, `var`)
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