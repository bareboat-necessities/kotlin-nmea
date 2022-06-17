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

import net.sf.marineapi.nmea.sentence.GGASentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.*
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
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setAltitude(double)
	 */
    override var altitude: Double
        get() = getDoubleValue(ALTITUDE)
        set(alt) {
            setDoubleValue(ALTITUDE, alt, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getAltitudeUnits()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setAltitudeUnits(net.sf.marineapi
	 * .nmea.util.Units)
	 */
    override var altitudeUnits: Units
        get() {
            val ch = getCharValue(ALTITUDE_UNITS)
            if (ch != GGASentence.Companion.ALT_UNIT_METERS && ch != GGASentence.Companion.ALT_UNIT_FEET) {
                val msg = "Invalid altitude unit indicator: %s"
                throw ParseException(String.format(msg, ch))
            }
            return Units.Companion.valueOf(ch)
        }
        set(unit) {
            setCharValue(ALTITUDE_UNITS, unit.toChar())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getDgpsAge()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setDgpsAge(int)
	 */
    override var dgpsAge: Double
        get() = getDoubleValue(DGPS_AGE)
        set(age) {
            setDoubleValue(DGPS_AGE, age, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getDgpsStationId()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setDgpsStationId(java.lang
	 * .String)
	 */
    override var dgpsStationId: String?
        get() = getStringValue(DGPS_STATION_ID)
        set(id) {
            setStringValue(DGPS_STATION_ID, id)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getFixQuality()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setFixQuality(net.sf.marineapi
	 * .nmea.util.GpsFixQuality)
	 */
    override var fixQuality: GpsFixQuality
        get() = GpsFixQuality.Companion.valueOf(getIntValue(FIX_QUALITY))
        set(quality) {
            setIntValue(FIX_QUALITY, quality.toInt())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getGeoidalHeight()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setGeoidalHeight(double)
	 */
    override var geoidalHeight: Double
        get() = getDoubleValue(GEOIDAL_HEIGHT)
        set(height) {
            setDoubleValue(GEOIDAL_HEIGHT, height, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getGeoidalHeightUnits()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setGeoidalHeightUnits(net.
	 * sf.marineapi.nmea.util.Units)
	 */
    override var geoidalHeightUnits: Units
        get() = Units.Companion.valueOf(getCharValue(HEIGHT_UNITS))
        set(unit) {
            setCharValue(HEIGHT_UNITS, unit.toChar())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getHorizontalDOP()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setHorizontalDOP(double)
	 */
    override var horizontalDOP: Double
        get() = getDoubleValue(HORIZONTAL_DILUTION)
        set(hdop) {
            setDoubleValue(HORIZONTAL_DILUTION, hdop, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.PositionSentence#getPosition()
	 */
    override fun getPosition(): Position? {
        val pos = parsePosition(
            LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE
        )
        if (hasValue(ALTITUDE) && hasValue(ALTITUDE_UNITS)) {
            var alt = altitude
            if (altitudeUnits == Units.FEET) {
                alt = alt / 0.3048
            }
            pos.altitude = alt
        }
        return pos
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getSatelliteCount()
	 *//*
 	 * (non-Javadoc)
 	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setSatelliteCount(int)
 	 */
    override var satelliteCount: Int
        get() = getIntValue(SATELLITES_IN_USE)
        set(count) {
            require(count >= 0) { "Satelite count cannot be negative" }
            setIntValue(SATELLITES_IN_USE, count, 2)
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
	 * @see
	 * net.sf.marineapi.nmea.sentence.PositionSentence#setPosition(net.sf.marineapi
	 * .nmea.util.Position)
	 */
    override fun setPosition(pos: Position) {
        setPositionValues(
            pos, LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE
        )
        altitude = pos.altitude
        altitudeUnits = Units.METER
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