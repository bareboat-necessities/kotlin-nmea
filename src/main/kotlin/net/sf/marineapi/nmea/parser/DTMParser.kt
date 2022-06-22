/*
 * DTMParser.java
 * Copyright (C) 2014 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.DTMSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId

/**
 * DTM parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class DTMParser : SentenceParser, DTMSentence {
    /**
     * Creates a new instance of DTMParser.
     *
     * @param nmea DTM sentence String to parse.
     */
    constructor(nmea: String) : super(nmea, SentenceId.DTM)

    /**
     * Creates a new empty instance of DTMParser.
     *
     * @param talker Talker ID to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.DTM, 8)

    override fun getAltitudeOffset(): Double {
        return getDoubleValue(ALTITUDE_OFFSET)
    }

    override fun getDatumCode(): String {
        return getStringValue(DATUM_CODE)
    }

    override fun getDatumSubCode(): String {
        return getStringValue(DATUM_SUBCODE)
    }

    override fun getLatitudeOffset(): Double {
        return getDoubleValue(LATITUDE_OFFSET)
    }

    override fun getLongitudeOffset(): Double {
        return getDoubleValue(LONGITUDE_OFFSET)
    }

    override fun getName(): String {
        return getStringValue(DATUM_NAME)
    }

    override fun setDatumCode(code: String?) {
        setStringValue(DATUM_CODE, code)
    }

    override fun setDatumSubCode(code: String?) {
        setStringValue(DATUM_SUBCODE, code)
    }

    override fun setLatitudeOffset(offset: Double) {
        setDoubleValue(LATITUDE_OFFSET, offset, 1, 4)
        setCharValue(LAT_OFFSET_HEMISPHERE, if (offset < 0) 'S' else 'N')
    }

    override fun setLongitudeOffset(offset: Double) {
        setDoubleValue(LONGITUDE_OFFSET, offset, 1, 4)
        setCharValue(LON_OFFSET_HEMISPHERE, if (offset < 0) 'W' else 'E')
    }

    override fun setName(name: String?) {
        setStringValue(DATUM_NAME, name)
    }

    companion object {
        private const val DATUM_CODE = 0
        private const val DATUM_SUBCODE = 1
        private const val LATITUDE_OFFSET = 2
        private const val LAT_OFFSET_HEMISPHERE = 3
        private const val LONGITUDE_OFFSET = 4
        private const val LON_OFFSET_HEMISPHERE = 5
        private const val ALTITUDE_OFFSET = 6
        private const val DATUM_NAME = 7
    }
}