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

import net.sf.marineapi.nmea.sentence.DTMSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerId
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

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.DTMSentence#getAltitudeOffset()
	 */
    override val altitudeOffset: Double
        get() = getDoubleValue(ALTITUDE_OFFSET)

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.DTMSentence#getDatumCode()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.DTMSentence#setDatumCode(java.lang.String)
	 */
    override var datumCode: String?
        get() = getStringValue(DATUM_CODE)
        set(code) {
            setStringValue(DATUM_CODE, code)
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.DTMSentence#getDatumSubCode()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.DTMSentence#setDatumSubCode(java.lang.
	 * String)
	 */
    override var datumSubCode: String?
        get() = getStringValue(DATUM_SUBCODE)
        set(code) {
            setStringValue(DATUM_SUBCODE, code)
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.DTMSentence#getLatitudeOffset()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.DTMSentence#setLatitudeOffset(double)
	 */
    override var latitudeOffset: Double
        get() = getDoubleValue(LATITUDE_OFFSET)
        set(offset) {
            setDoubleValue(LATITUDE_OFFSET, offset, 1, 4)
            if (offset < 0) {
                setCharValue(LAT_OFFSET_HEMISPHERE, 'S')
            } else {
                setCharValue(LAT_OFFSET_HEMISPHERE, 'N')
            }
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.DTMSentence#getLongitudeOffset()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.DTMSentence#setLongitudeOffset(double)
	 */
    override var longitudeOffset: Double
        get() = getDoubleValue(LONGITUDE_OFFSET)
        set(offset) {
            setDoubleValue(LONGITUDE_OFFSET, offset, 1, 4)
            if (offset < 0) {
                setCharValue(LON_OFFSET_HEMISPHERE, 'W')
            } else {
                setCharValue(LON_OFFSET_HEMISPHERE, 'E')
            }
        }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.DTMSentence#getName()
	 *//*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.DTMSentence#setName(java.lang.String)
	 */
    override var name: String?
        get() = getStringValue(DATUM_NAME)
        set(name) {
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