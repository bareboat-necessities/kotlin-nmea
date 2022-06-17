/* 
 * PositionProvider.java
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
package net.sf.marineapi.provider

import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.parser.DataNotAvailableException
import net.sf.marineapi.nmea.sentence.*
import net.sf.marineapi.nmea.util.*

import net.sf.marineapi.provider.event.PositionEvent
import java.util.*
import java.util.Date

/**
 *
 *
 * Provides Time, Position and Velocity reports from GPS. Data is captured from
 * RMC, GGA and GLL sentences. RMC is used for date/time, speed and course. GGA
 * is used as primary source for position as it contains also the altitude. When
 * GGA is not available, position may be taken from GLL or RMC. If this is the
 * case, there is no altitude included in the
 * [net.sf.marineapi.nmea.util.Position]. GPS data statuses are also
 * captured and events are dispatched only when sentences report
 * [net.sf.marineapi.nmea.util.DataStatus.ACTIVE]. FAA mode transmitted in
 * RMC is also checked and captured when available, but may be `null`
 * depending on used NMEA version.
 *
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.provider.event.PositionListener
 *
 * @see net.sf.marineapi.provider.event.PositionEvent
 *
 * @see net.sf.marineapi.nmea.io.SentenceReader
 */
class PositionProvider
/**
 * Creates a new instance of PositionProvider.
 *
 * @param reader SentenceReader that provides the required sentences.
 */
    (reader: SentenceReader) :
    AbstractProvider<PositionEvent>(reader, SentenceId.RMC, SentenceId.GGA, SentenceId.GLL, SentenceId.VTG) {
    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#createProviderEvent()
	 */
    override fun createProviderEvent(): PositionEvent {
        var p: Position? = null
        var sog: Double? = null
        var cog: Double? = null
        var d: Date? = null
        var t: Time? = null
        var mode: FaaMode? = null
        var fix: GpsFixQuality? = null
        for (s in getSentences()) {
            if (s is RMCSentence) {
                sog = s.getSpeed()
                try {
                    cog = s.getCourse()
                } catch (e: DataNotAvailableException) {
                    // If we are not moving, cource can be undefined. Leave null in that case.
                }
                d = s.getDate()
                t = s.getTime()
                if (p == null) {
                    p = s.getPosition()
                    if (s.getFieldCount() > 11) {
                        mode = s.getMode()
                    }
                }
            } else if (s is VTGSentence) {
                sog = s.getSpeedKnots()
                try {
                    cog = s.getTrueCourse()
                } catch (e: DataNotAvailableException) {
                    // If we are not moving, cource can be undefined. Leave null in that case.
                }
            } else if (s is GGASentence) {
                // Using GGA as primary position source as it contains both
                // position and altitude
                val gga = s
                p = gga.getPosition()
                fix = gga.getFixQuality()

                // Some receivers do not provide RMC message
                if (t == null) {
                    t = gga.getTime()
                }
            } else if (s is GLLSentence && p == null) {
                val gll = s
                p = gll.getPosition()
            }
        }

        // Ag-Star reciever does not provide RMC sentence. So we have to guess what date it is
        if (d == null) {
            d = Date()
        }
        return PositionEvent(this, p, sog!!, cog, d, t, mode, fix)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#isReady()
	 */
    override fun isReady(): Boolean {
        return hasOne("RMC", "VTG") && hasOne("GGA", "GLL")
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#isValid()
	 */
    override fun isValid(): Boolean {
        for (s in getSentences()) {
            if (s is RMCSentence) {
                val rmc = s
                val ds = rmc.getStatus()
                if (DataStatus.VOID == ds || rmc.getFieldCount() > 11 && FaaMode.NONE == rmc.getMode()) {
                    return false
                }
            } else if (s is GGASentence) {
                val fq = s.getFixQuality()
                if (GpsFixQuality.INVALID == fq) {
                    return false
                }
            } else if (s is GLLSentence) {
                val ds = s.getStatus()
                if (DataStatus.VOID == ds) {
                    return false
                }
            }
        }
        return true
    }
}