/* 
 * GGAParser.java
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

import net.sf.marineapi.nmea.sentence.GGASentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.GpsFixQuality
import net.sf.marineapi.nmea.util.Position
import net.sf.marineapi.nmea.util.Time
import net.sf.marineapi.nmea.util.Units

/**
 * GGA sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class GGAParser : PositionParser, GGASentence {
    /**
     * Creates a new instance of GGA parser.
     *
     * @param nmea GGA sentence String.
     * @throws IllegalArgumentException If the specified sentence is invalid or
     * not a GGA sentence.
     */
    constructor(nmea: String) : super(nmea, SentenceId.GGA) {}

    /**
     * Creates GSA parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.GGA, 14) {}

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getAltitude()
	 */
    override fun getAltitude(): Double {
        return getDoubleValue(ALTITUDE)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getAltitudeUnits()
	 */
    override fun getAltitudeUnits(): Units {
        val ch = getCharValue(ALTITUDE_UNITS)
        if (ch != GGASentence.ALT_UNIT_METERS && ch != GGASentence.ALT_UNIT_FEET) {
            val msg = "Invalid altitude unit indicator: %s"
            throw ParseException(String.format(msg, ch))
        }
        return Units.valueOf(ch)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getDgpsAge()
	 */
    override fun getDgpsAge(): Double {
        return getDoubleValue(DGPS_AGE)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getDgpsStationId()
	 */
    override fun getDgpsStationId(): String {
        return getStringValue(DGPS_STATION_ID)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getFixQuality()
	 */
    override fun getFixQuality(): GpsFixQuality {
        return GpsFixQuality.valueOf(getIntValue(FIX_QUALITY))
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getGeoidalHeight()
	 */
    override fun getGeoidalHeight(): Double {
        return getDoubleValue(GEOIDAL_HEIGHT)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getGeoidalHeightUnits()
	 */
    override fun getGeoidalHeightUnits(): Units {
        return Units.valueOf(getCharValue(HEIGHT_UNITS))
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getHorizontalDOP()
	 */
    override fun getHorizontalDOP(): Double {
        return getDoubleValue(HORIZONTAL_DILUTION)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.PositionSentence#getPosition()
	 */
    override fun getPosition(): Position {
        val pos = parsePosition(
            LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE
        )
        if (hasValue(ALTITUDE) && hasValue(ALTITUDE_UNITS)) {
            var alt = getAltitude()
            if (getAltitudeUnits() == Units.FEET) {
                alt /= 0.3048
            }
            pos!!.altitude = alt
        }
        return pos!!
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getSatelliteCount()
	 */
    override fun getSatelliteCount(): Int {
        return getIntValue(SATELLITES_IN_USE)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.TimeSentence#getTime()
	 */
    override fun getTime(): Time {
        val str = getStringValue(UTC_TIME)
        return Time(str)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setAltitude(double)
	 */
    override fun setAltitude(alt: Double) {
        setDoubleValue(ALTITUDE, alt, 1, 1)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setAltitudeUnits(net.sf.marineapi
	 * .nmea.util.Units)
	 */
    override fun setAltitudeUnits(unit: Units?) {
        setCharValue(ALTITUDE_UNITS, unit!!.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setDgpsAge(int)
	 */
    override fun setDgpsAge(age: Double) {
        setDoubleValue(DGPS_AGE, age, 1, 1)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setDgpsStationId(java.lang
	 * .String)
	 */
    override fun setDgpsStationId(id: String?) {
        setStringValue(DGPS_STATION_ID, id)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setFixQuality(net.sf.marineapi
	 * .nmea.util.GpsFixQuality)
	 */
    override fun setFixQuality(quality: GpsFixQuality?) {
        setIntValue(FIX_QUALITY, quality!!.toInt())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setGeoidalHeight(double)
	 */
    override fun setGeoidalHeight(height: Double) {
        setDoubleValue(GEOIDAL_HEIGHT, height, 1, 1)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setGeoidalHeightUnits(net.
	 * sf.marineapi.nmea.util.Units)
	 */
    override fun setGeoidalHeightUnits(unit: Units?) {
        setCharValue(HEIGHT_UNITS, unit!!.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setHorizontalDOP(double)
	 */
    override fun setHorizontalDOP(hdop: Double) {
        setDoubleValue(HORIZONTAL_DILUTION, hdop, 1, 1)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.PositionSentence#setPosition(net.sf.marineapi
	 * .nmea.util.Position)
	 */
    override fun setPosition(pos: Position) {
        setPositionValues(
            pos, LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE
        )
        setAltitude(pos.altitude)
        setAltitudeUnits(Units.METER)
    }

    /*
 	 * (non-Javadoc)
 	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setSatelliteCount(int)
 	 */
    override fun setSatelliteCount(count: Int) {
        require(count >= 0) { "Satelite count cannot be negative" }
        setIntValue(SATELLITES_IN_USE, count, 2)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.TimeSentence#setTime(net.sf.marineapi.
	 * nmea.util.Time)
	 */
    override fun setTime(t: Time?) {
        setStringValue(UTC_TIME, t.toString())
    }

    companion object {
        // GGA field indices
        private const val UTC_TIME = 0
        private const val LATITUDE = 1
        private const val LAT_HEMISPHERE = 2
        private const val LONGITUDE = 3
        private const val LON_HEMISPHERE = 4
        private const val FIX_QUALITY = 5
        private const val SATELLITES_IN_USE = 6
        private const val HORIZONTAL_DILUTION = 7
        private const val ALTITUDE = 8
        private const val ALTITUDE_UNITS = 9
        private const val GEOIDAL_HEIGHT = 10
        private const val HEIGHT_UNITS = 11
        private const val DGPS_AGE = 12
        private const val DGPS_STATION_ID = 13
    }
}