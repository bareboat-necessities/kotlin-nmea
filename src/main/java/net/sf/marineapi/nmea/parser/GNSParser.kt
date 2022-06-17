/*
 * GNSParser.java
 * Copyright (C) 2016 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.GNSSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.Position
import net.sf.marineapi.nmea.util.Time

/**
 * GNS sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class GNSParser : PositionParser, GNSSentence {
    /**
     * Constructor for parsing GNS.
     *
     * @param nmea GNS sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.GNS)

    /**
     * Constructor for empty GNS sentence.
     *
     * @param tid Talker ID to set
     */
    constructor(tid: TalkerId?) : super(tid, SentenceId.GNS, 12) {
        setTime(Time())
        setStringValue(MODE, "NN")
    }

    override fun getTime(): Time {
        return Time(getStringValue(UTC_TIME))
    }

    override fun setTime(t: Time?) {
        setStringValue(UTC_TIME, t.toString())
    }

    override fun getPosition(): Position {
        return parsePosition(LATITUDE, LAT_DIRECTION, LONGITUDE, LON_DIRECTION)
    }

    override fun setPosition(pos: Position) {
        setPositionValues(pos, LATITUDE, LAT_DIRECTION, LONGITUDE, LON_DIRECTION)
    }

    override fun getGpsMode(): GNSSentence.Mode {
        val modes = getStringValue(MODE)
        return GNSSentence.Mode.valueOf(modes[GPS_MODE])
    }

    override fun setGpsMode(gps: GNSSentence.Mode?) {
        val modes = getStringValue(MODE)
        setStringValue(MODE, gps!!.toChar().toString() + modes.substring(GNS_MODE))
    }

    override fun getGlonassMode(): GNSSentence.Mode {
        val modes = getStringValue(MODE)
        return GNSSentence.Mode.valueOf(modes[GNS_MODE])
    }

    override fun setGlonassMode(gns: GNSSentence.Mode?) {
        val modes = getStringValue(MODE)
        val sb = StringBuffer(modes.length)
        sb.append(modes[GPS_MODE])
        sb.append(gns!!.toChar())
        if (modes.length > 2) {
            sb.append(modes.substring(VAR_MODE))
        }
        setStringValue(MODE, sb.toString())
    }

    override fun getAdditionalModes(): Array<GNSSentence.Mode?> {
        val mode = getStringValue(MODE)
        if (mode.length == 2) {
            return arrayOfNulls(0)
        }
        val additional = mode.substring(VAR_MODE)
        val modes = arrayOfNulls<GNSSentence.Mode>(additional.length)
        for (i in 0 until additional.length) {
            modes[i] = GNSSentence.Mode.valueOf(additional[i])
        }
        return modes
    }

    override fun setAdditionalModes(vararg modes: GNSSentence.Mode?) {
        val current = getStringValue(MODE)
        val sb = StringBuffer(modes.size + 2)
        sb.append(current.substring(0, VAR_MODE))
        for (m in modes) {
            sb.append(m!!.toChar())
        }
        setStringValue(MODE, sb.toString())
    }

    override fun getSatelliteCount(): Int {
        return getIntValue(SATELLITE_COUNT)
    }

    override fun setSatelliteCount(count: Int) {
        setIntValue(SATELLITE_COUNT, count, 2)
    }

    override fun getHorizontalDOP(): Double {
        return getDoubleValue(HDOP)
    }

    override fun setHorizontalDOP(hdop: Double) {
        setDoubleValue(HDOP, hdop, 1, 2)
    }

    override fun getOrthometricHeight(): Double {
        return getDoubleValue(ORTHOMETRIC_HEIGHT)
    }

    override fun setOrthometricHeight(height: Double) {
        setDoubleValue(ORTHOMETRIC_HEIGHT, height, 1, 2)
    }

    override fun getGeoidalSeparation(): Double {
        return getDoubleValue(GEOIDAL_SEPARATION)
    }

    override fun setGeoidalSeparation(separation: Double) {
        setDoubleValue(GEOIDAL_SEPARATION, separation, 1, 2)
    }

    override fun getDgpsAge(): Double {
        return getDoubleValue(DGPS_AGE)
    }

    override fun setDgpsAge(age: Double) {
        setDoubleValue(DGPS_AGE, age, 1, 1)
    }

    override fun getDgpsStationId(): String {
        return getStringValue(DGPS_STATION)
    }

    override fun setDgpsStationId(stationId: String?) {
        setStringValue(DGPS_STATION, stationId)
    }

    companion object {
        // NMEA field indices
        private const val UTC_TIME = 0
        private const val LATITUDE = 1
        private const val LAT_DIRECTION = 2
        private const val LONGITUDE = 3
        private const val LON_DIRECTION = 4
        private const val MODE = 5
        private const val SATELLITE_COUNT = 6
        private const val HDOP = 7
        private const val ORTHOMETRIC_HEIGHT = 8
        private const val GEOIDAL_SEPARATION = 9
        private const val DGPS_AGE = 10
        private const val DGPS_STATION = 11

        // MODE string character indices
        private const val GPS_MODE = 0
        private const val GNS_MODE = 1
        private const val VAR_MODE = 2
    }
}