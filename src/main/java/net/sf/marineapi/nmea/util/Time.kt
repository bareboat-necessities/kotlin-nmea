/*
 * Time.java
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
package net.sf.marineapi.nmea.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

import java.util.GregorianCalendar
import java.util.Calendar
import java.util.Date

/**
 * Represents a time of day in 24-hour clock, i.e. the UTC time used as default
 * in NMEA 0183. Transmitted by
 * [net.sf.marineapi.nmea.sentence.TimeSentence].
 *
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.nmea.sentence.TimeSentence
 *
 * @see net.sf.marineapi.nmea.util.Date
 */
class Time {
    // hour of day
    private var hour = 0

    // minute of hour
    private var minutes = 0

    // seconds of a minute, may include decimal sub-second in some sentences
    private var seconds = 0.0
    /**
     * Get time zone offset hours. Defaults to 0 (UTC).
     *
     * @return Offset hours as int.
     */
    /**
     * Set time zone offset hours.
     *
     * @param hours Offset to set (-13..13)
     * @throws IllegalArgumentException If offset out of bounds.
     */
    // time zone offset hours
    var offsetHours = 0
        set(hours) {
            require(!(hours < -13 || hours > 13)) { "Offset out of bounds [-13..13]" }
            field = hours
        }
    /**
     * Get time zone offset minutes. Defaults to 0 (UTC).
     *
     * @return Offset minutes as int.
     */
    /**
     * Set time zone offset minutes.
     *
     * @param minutes Offset to set (-59..59)
     * @throws IllegalArgumentException If offset out of bounds.
     */
    // time zone offset minutes
    var offsetMinutes = 0
        set(minutes) {
            require(!(minutes < -59 || minutes > 59)) { "Offset out of bounds [-59..59]" }
            field = minutes
        }

    /**
     * Creates a new instance of `Time` using the current system
     * time.
     */
    constructor() {
        val c = GregorianCalendar()
        hour = c[Calendar.HOUR_OF_DAY]
        minutes = c[Calendar.MINUTE]
        seconds = c[Calendar.SECOND].toDouble()
    }

    /**
     * Creates a new instance of `Time` based on given String.
     * Assumes the `hhmmss.sss` formatting used in NMEA sentences.
     *
     * @param time Timestamp String
     */
    constructor(time: String?) {
        setHour(time!!.substring(0, 2).toInt())
        setMinutes(time.substring(2, 4).toInt())
        setSeconds(time.substring(4).toDouble())
    }

    /**
     * Creates a new instance of Time with hours, minutes and seconds.
     *
     * @param hour Hour of day
     * @param min Minute of hour
     * @param sec Second of minute
     */
    constructor(hour: Int, min: Int, sec: Double) {
        setHour(hour)
        setMinutes(min)
        setSeconds(sec)
    }

    /**
     * Creates a new instance of Time with time zone offsets.
     *
     * @param hour Hour of day
     * @param min Minute of hour
     * @param sec Second of minute
     * @param offsetHrs Time zone offset hours
     * @param offsetMin Time zone offset minutes
     */
    constructor(hour: Int, min: Int, sec: Double, offsetHrs: Int, offsetMin: Int) {
        setHour(hour)
        setMinutes(min)
        setSeconds(sec)
        offsetHours = offsetHrs
        offsetMinutes = offsetMin
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
    override fun equals(obj: Any?): Boolean {
        if (obj === this) {
            return true
        }
        if (obj is Time) {
            return obj.getHour() == getHour() && obj.getMinutes() == getMinutes() && obj.getSeconds() == getSeconds() && obj.offsetHours == offsetHours && obj.offsetMinutes == offsetMinutes
        }
        return false
    }

    /**
     * Get the hour of day.
     *
     * @return the hour
     */
    fun getHour(): Int {
        return hour
    }

    /**
     * Get time as milliseconds since beginning of day.
     *
     * @return Milliseconds
     */
    val milliseconds: Long
        get() {
            var m = Math.round(getSeconds() * 1000)
            m += (getMinutes() * 60 * 1000).toLong()
            m += (getHour() * 3600 * 1000).toLong()
            return m
        }

    /**
     * Get the minute of hour.
     *
     * @return the minute
     */
    fun getMinutes(): Int {
        return minutes
    }

    /**
     * Get the second of minute.
     *
     * @return the second
     */
    fun getSeconds(): Double {
        return seconds
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
    override fun hashCode(): Int {
        val s = String.format("%2d%2d%2f", hour, minutes, seconds)
        return s.hashCode()
    }

    /**
     * Set the hour of day.
     *
     * @param hour the hour to set
     * @throws IllegalArgumentException If hour value out of bounds 0..23
     */
    fun setHour(hour: Int) {
        require(!(hour < 0 || hour > 23)) { "Valid hour value is between 0..23" }
        this.hour = hour
    }

    /**
     * Set the minute of hour.
     *
     * @param minutes the minute to set
     * @throws IllegalArgumentException If minutes value out of bounds 0..59
     */
    fun setMinutes(minutes: Int) {
        require(!(minutes < 0 || minutes > 59)) { "Valid minutes value is between 0..59" }
        this.minutes = minutes
    }

    /**
     * Set seconds of minute.
     *
     * @param seconds Seconds to set
     * @throws IllegalArgumentException If seconds out of bounds (
     * `0 &lt; seconds &lt; 60`)
     */
    fun setSeconds(seconds: Double) {
        require(!(seconds < 0 || seconds >= 60)) { "Invalid value for second (0 < seconds < 60)" }
        this.seconds = seconds
    }

    /**
     * Set the time by [java.util.Date]. The date information of is
     * ignored, only hours, minutes and seconds are relevant. Notice also that
     * time zone offset is not affected by this method because
     * [java.util.Date] does not contain zone offset.
     *
     * @param d Date
     */
    fun setTime(d: Date?) {
        val cal = GregorianCalendar()
        cal.time = d
        val seconds = cal[Calendar.SECOND] + cal[Calendar.MILLISECOND] / 1000.0
        setHour(cal[Calendar.HOUR_OF_DAY])
        setMinutes(cal[Calendar.MINUTE])
        setSeconds(seconds)
    }

    /**
     * Convert to [java.util.Date]. Notice that time zone information is
     * lost in conversion as [java.util.Date] does not contain time zone.
     *
     * @param d Date that defines year, month and day for time.
     * @return A Date that is combination of specified Date and Time
     */
    fun toDate(d: Date?): Date {
        val seconds = getSeconds()
        val fullSeconds = Math.floor(seconds).toInt()
        val milliseconds = Math.round((seconds - fullSeconds) * 1000).toInt()
        val cal = GregorianCalendar()
        cal.time = d
        cal[Calendar.HOUR_OF_DAY] = getHour()
        cal[Calendar.MINUTE] = getMinutes()
        cal[Calendar.SECOND] = fullSeconds
        cal[Calendar.MILLISECOND] = milliseconds
        return cal.time
    }

    /**
     * Returns the String representation of `Time`. Formats the time
     * in `hhmmss.sss` format used in NMEA 0183 sentences. Seconds
     * are presented with three decimals regardless of precision returned by
     * [.getSeconds].
     */
    override fun toString(): String {
        val str = String.format("%02d%02d", getHour(), getMinutes())
        val nf = DecimalFormat("00.000")
        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'
        nf.decimalFormatSymbols = dfs
        return str + nf.format(getSeconds())
    }

    /**
     * Returns the ISO 8601 representation of time (`hh:mm:ss+hh:mm`).
     *
     * @return ISO 8601 formatted time String.
     */
    fun toISO8601(): String {
        val hr = getHour()
        val min = getMinutes()
        val sec = Math.floor(getSeconds()).toInt()
        val tzHr = offsetHours
        val tzMin = offsetMinutes
        return String.format(TIME_PATTERN, hr, min, sec, tzHr, tzMin)
    }

    companion object {
        // ISO 8601 time format pattern with time zone
        private const val TIME_PATTERN = "%02d:%02d:%02d%+03d:%02d"
    }
}