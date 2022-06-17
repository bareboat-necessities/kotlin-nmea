/* 
 * DTAParser.java
 * Copyright (C) 2019 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.DTASentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerIdimport java.text.DateFormatimport java.text.SimpleDateFormatimport java.util.*
/**
 * DTA sentence parser.
 *
 * @author Bob Schwarz
 * @see [marine-api fork](https://github.com/LoadBalanced/marine-api)
 */
internal open class DTAParser : SentenceParser, DTASentence {
    private val offset = if (fieldCount >= 8) 0 else -1

    /**
     * Creates a new instance of DTAParser with 8 data fields.
     *
     * @param talker DTA talkerId
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.DTA, 8)

    /**
     * Creates a new instance of DTAParser.
     *
     * @param nmea DTA sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.DTA)

    /**
     * Creates a new instance of DTAParser.
     *
     * @param nmea DTA sentence String
     */
    protected constructor(nmea: String, type: SentenceId) : super(nmea, type)

    /**
     * Creates a new instance of DTAParser with specified type and data fields.
     *
     * @param talker DTA talkerId
     * @param type SentenceId enum
     * @param size number of data fields in DTASentence (not counting header and checksum).
     */
    protected constructor(talker: TalkerId?, type: SentenceId, size: Int) : super(talker, type, size)

    /**
     * Returns the field index fixed with possible offset.
     */
    private fun getFieldIndex(field: Int): Int {
        return offset + field
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getChannelNumber()
	 */
    override val channelNumber: Int
        get() = if (offset == -1) {
            1
        } else getIntValue(CHANNEL_NUMBER)

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getGasConcentration()
	 */
    override val gasConcentration: Double
        get() = getDoubleValue(getFieldIndex(GAS_CONCENTRATION))

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getConfidenceFactorR2()
	 */
    override val confidenceFactorR2: Int
        get() = getIntValue(getFieldIndex(CONFIDENCE_FACTOR_R2))

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getDistance()
	 */
    override val distance: Double
        get() = getDoubleValue(getFieldIndex(DISTANCE))

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getLightLevel()
	 */
    override val lightLevel: Int
        get() = getIntValue(getFieldIndex(LIGHT_LEVEL))

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getDateTime()
	 */
    override val dateTime: Date
        get() {
            val value: Date
            value = try {
                DATE_PARSER.parse(getStringValue(getFieldIndex(DATE_TIME)))
            } catch (ex: java.text.ParseException) {
                throw ParseException("Field does not contain date value", ex)
            }
            return value
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getSerialNumber()
	 */
    override val serialNumber: String?
        get() = getStringValue(getFieldIndex(SER_NUMBER))

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getStatusCode()
	 */
    override val statusCode: Int
        get() = getIntValue(getFieldIndex(STATUS_CODE))

    companion object {
        private const val CHANNEL_NUMBER = 0
        private const val GAS_CONCENTRATION = 1
        private const val CONFIDENCE_FACTOR_R2 = 2
        private const val DISTANCE = 3
        private const val LIGHT_LEVEL = 4
        private const val DATE_TIME = 5
        private const val SER_NUMBER = 6
        private const val STATUS_CODE = 7
        private val DATE_PARSER: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    }
}