/*
 * ZDASentence.java
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
package net.sf.marineapi.nmea.sentence

import net.sf.marineapi.nmea.util.Time
import java.util.*

/**
 * UTC time and date with local time zone offset.
 *
 *
 * Example: <br></br>
 * `$GPZDA,032915,07,08,2004,00,00*4D`
 *
 * @author Kimmo Tuukkanen
 */
interface ZDASentence : TimeSentence, DateSentence {
    /**
     * Get offset to local time zone in hours, from 0 to +/- 13 hours.
     *
     * @return Time zone offset
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getLocalZoneHours(): Int

    /**
     * Get offset to local time zone in minutes, from 0 to +/- 59.
     *
     * @return Time zone offset
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getLocalZoneMinutes(): Int

    /**
     * Set offset to local time zone in hours.
     *
     * @param hours Offset, from 0 to +/- 13 hours.
     */
    fun setLocalZoneHours(hours: Int)

    /**
     * Set offset to local time zone in minutes.
     *
     * @param minutes Offset, from 0 to +/- 59 minutes.
     */
    fun setLocalZoneMinutes(minutes: Int)

    /**
     * Set time and local time zone hours and minutes.
     *
     * @param t Time to be inserted in sentence.
     */
    fun setTimeAndLocalZone(t: Time)

    /**
     * Get date and time as [java.util.Date].
     *
     * @return [java.util.Date]
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If any of the
     * date/time values is not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the any of the
     * date/time fields contains invalid value.
     */
    fun toDate(): Date?
}