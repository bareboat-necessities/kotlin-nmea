/* 
 * SatelliteInfoEvent.java
 * Copyright (C) 2012 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.GSASentence
import net.sf.marineapi.nmea.util.FaaMode
import net.sf.marineapi.nmea.util.GpsFixStatus
import net.sf.marineapi.nmea.util.SatelliteInfo

/**
 * SatelliteInfoEvent contains the satellite information collected by
 * [net.sf.marineapi.provider.SatelliteInfoProvider].
 *
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.nmea.sentence.GSASentence
 *
 * @see net.sf.marineapi.nmea.sentence.GSVSentence
 *
 * @see net.sf.marineapi.nmea.util.SatelliteInfo
 */
class SatelliteInfoEvent
/**
 * Creates a new satellite info event. GSA and GSV satellite info assumed
 * to be from same NMEA update sequence/cycle.
 *
 * @param source The object that sends the event.
 * @param gsa GSA sentence
 * @param info GSV satellite info
 */(
    source: Any?, private val gsa: GSASentence?,
    private val info: List<SatelliteInfo?>
) : ProviderEvent(source) {
    /**
     * Returns the list of GPS satellites used for GPS fix.
     *
     * @return Satellite ids list as reported by GSA sentence.
     */
    fun getSatelliteIds(): Array<String?>? {
        return gsa!!.getSatelliteIds()
    }

    /**
     * Returns the current detailed satellite information.
     *
     * @return List of SatelliteInfo objects from latest GSV sequence.
     */
    fun getSatelliteInfo(): List<SatelliteInfo?> {
        return info
    }

    /**
     * Returns the horizontal precision of GPS fix.
     *
     * @return HDOP value as reported by GSA sentence.
     */
    fun getHorizontalPrecision(): Double {
        return gsa!!.getHorizontalDOP()
    }

    /**
     * Returns the vertical precision of GPS fix.
     *
     * @return VDOP as reported by GSA sentence.
     */
    fun getVerticalPrecision(): Double {
        return gsa!!.getVerticalDOP()
    }

    /**
     * Returns the overall precision of GPS fix.
     *
     * @return PDOP as reported by GSA sentence.
     */
    fun getPositionPrecision(): Double {
        return gsa!!.getPositionDOP()
    }

    /**
     * Returns the GPS mode of operation as reported in GSA sentence.
     *
     * @return FaaMode enum value
     */
    fun getGpsMode(): FaaMode? {
        return gsa!!.getMode()
    }

    /**
     * Returns the GPS fix status as reported by GSA sentence.
     *
     * @return GpsFixStatus enum value
     */
    fun getGpsFixStatus(): GpsFixStatus? {
        return gsa!!.getFixStatus()
    }

    companion object {
        private const val serialVersionUID = -5243047395130051907L
    }
}