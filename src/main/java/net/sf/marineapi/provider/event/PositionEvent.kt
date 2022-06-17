/* 
 * PositionEvent.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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
package net.sf.marineapi.provider.event

import java.util.Date
import net.sf.marineapi.nmea.util.*

/**
 * GPS time/position/velocity report with current position, altitude, speed,
 * course and a time stamp. Notice that altitude may be missing, depending on
 * the source sentence of position (only
 * [net.sf.marineapi.nmea.sentence.GGASentence] contains altitude).
 *
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.provider.PositionProvider
 *
 * @see net.sf.marineapi.provider.event.PositionListener
 */
class PositionEvent
/**
 * Creates a new instance of PositionEvent.
 *
 * @param source Source object of event
 * @param pos Position of the event
 * @param sog Speed over ground
 * @param cog Course over ground, in degrees.
 * @param date Date when position was recorded
 * @param time Time when position was recorded
 * @param mode FAA mode
 * @param fq Position fixQuality quality
 */(
    source: Any?, private val position: Position?, private val speed: Double, private val course: Double?,
    private val date: Date, private val time: Time?, private val mode: FaaMode?, private val fixQuality: GpsFixQuality?
) : ProviderEvent(source), Cloneable {
    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
    public override fun clone(): PositionEvent {
        return PositionEvent(
            getSource(), position, speed, course, date, time,
            mode, fixQuality
        )
    }

    /**
     * Returns the current (true) course over ground.
     *
     * @return the course
     */
    fun getCourse(): Double? {
        return course
    }

    /**
     * Returns the date.
     *
     * @return Date
     */
    fun getDate(): Date {
        return date
    }

    /**
     * Returns the current GPS fixQuality quality.
     *
     * @return GpsFixQuality
     */
    fun getFixQuality(): GpsFixQuality? {
        return fixQuality
    }

    /**
     *
     * Returns the current FAA operating mode of GPS receiver.
     *
     * Notice: may be always `null`, depending on the NMEA version
     * in use.
     *
     * @return FaaMode
     */
    fun getMode(): FaaMode? {
        return mode
    }

    /**
     * Returns the current position.
     *
     * @return Position
     */
    fun getPosition(): Position? {
        return position
    }

    /**
     * Returns the current speed over ground, in km/h.
     *
     * @return the speed
     */
    fun getSpeed(): Double {
        return speed * 1.852
    }

    /**
     * Returns the time.
     *
     * @return Time
     */
    fun getTime(): Time? {
        return time
    }

    /*
	 * (non-Javadoc)
	 * @see java.util.EventObject#toString()
	 */
    override fun toString(): String {
        val ptr = "t[%s %s] p%s v[%.01f, %.01f]"
        return String.format(ptr, date, time, position, speed, course)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}