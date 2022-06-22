/*
 * ZDAParser.java
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

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.ZDASentence
import net.sf.marineapi.nmea.util.Time

import java.util.Date

/**
 * ZDA sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class ZDAParser : SentenceParser, ZDASentence {
    /**
     * Creates a new instance of ZDAParser.
     *
     * @param nmea ZDA sentence String
     * @throws IllegalArgumentException If specified sentence is invalid.
     */
    constructor(nmea: String) : super(nmea, SentenceId.ZDA)

    /**
     * Creates WPL parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.ZDA, 6)

    override fun getDate(): net.sf.marineapi.nmea.util.Date {
        val y = getIntValue(YEAR)
        val m = getIntValue(MONTH)
        val d = getIntValue(DAY)
        return net.sf.marineapi.nmea.util.Date(y, m, d)
    }

    override fun getLocalZoneHours(): Int {
        return getIntValue(LOCAL_ZONE_HOURS)
    }

    override fun getLocalZoneMinutes(): Int {
        return getIntValue(LOCAL_ZONE_MINUTES)
    }

    override fun getTime(): Time {
        val str = getStringValue(UTC_TIME)
        val tzHrs = getLocalZoneHours()
        val tzMin = getLocalZoneMinutes()
        val t = Time(str)
        t.offsetHours = tzHrs
        t.offsetMinutes = tzMin
        return t
    }

    override fun setDate(date: net.sf.marineapi.nmea.util.Date?) {
        setIntValue(YEAR, date!!.getYear())
        setIntValue(MONTH, date.getMonth(), 2)
        setIntValue(DAY, date.getDay(), 2)
    }

    override fun setLocalZoneHours(hours: Int) {
        require(!(hours < -13 || hours > 13)) { "Value must be within range -13..13" }
        setIntValue(LOCAL_ZONE_HOURS, hours, 2)
    }

    override fun setLocalZoneMinutes(minutes: Int) {
        require(!(minutes < -59 || minutes > 59)) { "Value must be within range -59..59" }
        setIntValue(LOCAL_ZONE_MINUTES, minutes, 2)
    }

    override fun setTime(t: Time?) {
        setStringValue(UTC_TIME, t.toString())
    }

    override fun setTimeAndLocalZone(t: Time?) {
        setTime(t)
        setLocalZoneHours(t!!.offsetHours)
        setLocalZoneMinutes(t.offsetMinutes)
    }

    override fun toDate(): Date {
        val d = getDate()
        val t = getTime()
        return t.toDate(d.toDate())
    }

    companion object {
        // field indices
        private const val UTC_TIME = 0
        private const val DAY = 1
        private const val MONTH = 2
        private const val YEAR = 3
        private const val LOCAL_ZONE_HOURS = 4
        private const val LOCAL_ZONE_MINUTES = 5
    }
}