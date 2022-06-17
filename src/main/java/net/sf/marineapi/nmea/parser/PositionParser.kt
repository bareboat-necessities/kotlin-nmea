/*
 * PositionParser.java
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

import net.sf.marineapi.nmea.parser.SentenceParser
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 *
 *
 * Abstract base class for sentence parsers that handle geographic position or
 * waypoint data and parse NMEA 0183 lat/lon values.
 *
 *
 * Notice that [net.sf.marineapi.nmea.sentence.PositionSentence] interface
 * is not implemented by this parser because the extending parser may not always
 * provide current location.
 *
 * @author Kimmo Tuukkanen
 */
abstract class PositionParser : SentenceParser {
    /**
     * Constructor.
     *
     * @param nmea Sentence string to parse.
     * @param type Expected Sentence ID
     * @see SentenceParser.SentenceParser
     */
    protected constructor(nmea: String, type: SentenceId) : super(nmea, type) {}

    /**
     * Constructor for empty sentence.
     *
     * @param talker Talker ID to set
     * @param type Sentence ID to set
     * @param size Number of empty data fields to set.
     * @see SentenceParser.SentenceParser
     */
    protected constructor(talker: TalkerId?, type: SentenceId, size: Int) : super(talker, type, size) {}

    /**
     * Parses the hemisphere of latitude from specified field.
     *
     * @param index Index of field that contains the latitude hemisphere value.
     * @return Hemisphere of latitude
     */
    fun parseHemisphereLat(index: Int): CompassPoint {
        val ch = getCharValue(index)
        return parseHemisphereLat(ch)
    }

    /**
     * Parses the hemisphere of longitude from the specified field.
     *
     * @param index Field index for longitude hemisphere indicator
     * @return Hemisphere of longitude
     */
    fun parseHemisphereLon(index: Int): CompassPoint {
        val ch = getCharValue(index)
        return parseHemisphereLon(ch)
    }

    /**
     * Parse latitude or longitude degrees and minutes from the specified field.
     * Assumes the `dddmm.mmmm` formatting where the two digits to the
     * left of dot denote full minutes and any remaining digits to the left
     * denote full degrees (usually padded with leading zeros). Digits to the
     * right of the dot denote the minute decimals.
     *
     * @see PositionParser
     *
     * @param index Index of the lat/lon field.
     * @return Degrees decimal value
     */
    fun parseDegrees(index: Int): Double {
        val field = getStringValue(index)
        return parseDegrees(field)
    }

    /**
     * Parses a `Position` from specified fields.
     *
     * @see PositionParser
     *
     * @param latIndex Latitude field index
     * @param latHemIndex Latitude hemisphere field index
     * @param lonIndex Longitude field index
     * @param lonHemIndex Longitude hemisphere field index
     * @return Position object
     */
    fun parsePosition(
        latIndex: Int, latHemIndex: Int,
        lonIndex: Int, lonHemIndex: Int
    ): Position {
        val latitudeValue = getStringValue(latIndex)
        val latitudeHemisphereIndicator = getCharValue(latHemIndex)
        val longitudeValue = getStringValue(lonIndex)
        val longitudeHemisphereIndicator = getCharValue(lonHemIndex)
        return parsePosition(
            latitudeValue, latitudeHemisphereIndicator,
            longitudeValue, longitudeHemisphereIndicator
        )
    }

    /**
     * Set the hemisphere of latitude in specified field.
     *
     * @param field Field index
     * @param hem Direction.NORTH or Direction.SOUTH
     * @throws IllegalArgumentException If specified Direction is other than
     * NORTH or SOUTH.
     */
    fun setLatHemisphere(field: Int, hem: CompassPoint) {
        require(!(hem != CompassPoint.NORTH && hem != CompassPoint.SOUTH)) {
            ("Invalid latitude hemisphere: "
                    + hem)
        }
        setCharValue(field, hem.toChar())
    }

    /**
     * Sets the latitude value in specified field, formatted in "ddmm.mmm".
     *
     * @param index Field index
     * @param lat Latitude value in degrees
     */
    fun setLatitude(index: Int, lat: Double) {
        val deg = Math.floor(lat).toInt()
        val min = (lat - deg) * 60
        val df = DecimalFormat("00.000")
        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'
        df.decimalFormatSymbols = dfs
        val result = String.format("%02d%s", deg, df.format(min))
        setStringValue(index, result)
    }

    /**
     * Sets the longitude value in specified field, formatted in "dddmm.mmm".
     * Does not check if the given value is logically correct to current
     * longitude hemisphere.
     *
     * @param index Field index
     * @param lon Longitude value in degrees
     */
    fun setLongitude(index: Int, lon: Double) {
        val deg = Math.floor(lon).toInt()
        val min = (lon - deg) * 60
        val nf = DecimalFormat("00.000")
        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'
        nf.decimalFormatSymbols = dfs
        val result = String.format("%03d%s", deg, nf.format(min))
        setStringValue(index, result)
    }

    /**
     * Set the hemisphere of longitude in specified field. Does not check if the
     * given value is logically correct to current longitude value.
     *
     * @param field Field index
     * @param hem Direction.EAST or Direction.WEST
     * @throws IllegalArgumentException If specified Direction is other than
     * EAST or WEST.
     */
    fun setLonHemisphere(field: Int, hem: CompassPoint) {
        require(!(hem != CompassPoint.EAST && hem != CompassPoint.WEST)) {
            ("Invalid longitude hemisphere: "
                    + hem)
        }
        setCharValue(field, hem.toChar())
    }

    /**
     * Sets the values from specified `Position` according to given
     * field indices. Sets the absolute values of latitude and longitude, and
     * hemisphere indicators as given by `Position`. Does not set
     * altitude.
     *
     * @param p Position to set
     * @param latIndex Index of latitude field
     * @param latHemIndex Index of latitude hemisphere field
     * @param lonIndex Index of longitude field
     * @param lonHemIndex Index of longitude hemisphere field
     */
    fun setPositionValues(
        p: Position, latIndex: Int, latHemIndex: Int,
        lonIndex: Int, lonHemIndex: Int
    ) {
        setLatitude(latIndex, Math.abs(p.latitude))
        setLongitude(lonIndex, Math.abs(p.longitude))
        setLatHemisphere(latHemIndex, p.latitudeHemisphere)
        setLonHemisphere(lonHemIndex, p.longitudeHemisphere)
    }

    companion object {
        /**
         * Parse latitude or longitude degrees and minutes from the specified string.
         * Assumes the `dddmm.mmmm` formatting where the two digits to the
         * left of dot denote full minutes and any remaining digits to the left
         * denote full degrees (usually padded with leading zeros). Digits to the
         * right of the dot denote the minute decimals.
         *
         * @param degreeStr NMEA 0183 degrees/minutes String
         * @return Degrees decimal value
         */
        fun parseDegrees(degreeStr: String?): Double {
            val dotIndex = degreeStr!!.indexOf(".")
            val degStr = if (dotIndex > 2) degreeStr.substring(0, dotIndex - 2) else "0"
            val minStr = if (dotIndex > 2) degreeStr.substring(dotIndex - 2) else degreeStr
            val deg = degStr.toInt()
            val min = minStr.toDouble()
            return deg + min / 60
        }

        /**
         * Parses the hemisphere of latitude from specified char.
         *
         * @param ch Hemisphere char indicator.
         * @return Corresponding [CompassPoint] enum value.
         * @throws ParseException If specified char is not 'N' or 'S'.
         */
        fun parseHemisphereLat(ch: Char): CompassPoint {
            val d = CompassPoint.valueOf(ch)
            if (d != CompassPoint.NORTH && d != CompassPoint.SOUTH) {
                throw ParseException("Invalid latitude hemisphere '$ch'")
            }
            return d
        }

        /**
         * Parses the hemisphere of longitude from the specified char.
         *
         * @param ch Hemisphere char indicator.
         * @return Corresponding [CompassPoint] enum value.
         * @throws ParseException If specified char is not 'E' or 'W'.
         */
        fun parseHemisphereLon(ch: Char): CompassPoint {
            val d = CompassPoint.valueOf(ch)
            if (d != CompassPoint.EAST && d != CompassPoint.WEST) {
                throw ParseException("Invalid longitude hemisphere $ch'")
            }
            return d
        }

        /**
         * Parses a `Position` from specified fields. The parameters are the raw
         * values from the NMEA Sentence.
         *
         * @param latitudeValue String value from the NMEA Sentence
         * @param latitudeHemisphereIndicator Character value from the NMEA Sentence
         * @param longitudeValue String value from the NMEA Sentence
         * @param longitudeHemisphereIndicator Character value from the NMEA Sentence
         * @return Position object
         */
        @JvmStatic
        fun parsePosition(
            latitudeValue: String?, latitudeHemisphereIndicator: Char,
            longitudeValue: String?, longitudeHemisphereIndicator: Char
        ): Position {
            var lat = parseDegrees(latitudeValue)
            var lon = parseDegrees(longitudeValue)
            val lath = parseHemisphereLat(latitudeHemisphereIndicator)
            val lonh = parseHemisphereLon(longitudeHemisphereIndicator)
            if (lath == CompassPoint.SOUTH) {
                lat = -lat
            }
            if (lonh == CompassPoint.WEST) {
                lon = -lon
            }
            return Position(lat, lon)
        }
    }
}