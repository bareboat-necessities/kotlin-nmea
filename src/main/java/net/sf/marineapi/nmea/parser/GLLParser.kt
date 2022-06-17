/*
 * GLLParser.java
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

import net.sf.marineapi.nmea.sentence.GLLSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.FaaMode
import net.sf.marineapi.nmea.util.Position
import net.sf.marineapi.nmea.util.Time

/**
 * GLL Sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class GLLParser : PositionParser, GLLSentence {
    /**
     * Creates a new instance of GLLParser.
     *
     * @param nmea GLL sentence String.
     * @throws IllegalArgumentException If the given sentence is invalid or does
     * not contain GLL sentence.
     */
    constructor(nmea: String) : super(nmea, SentenceId.GLL)

    /**
     * Creates GSA parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.GLL, 7)

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.PositionSentence#getPosition()
	 */
    override fun getPosition(): Position {
        return parsePosition(LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GLLSentence#getDataStatus()
	 */
    override fun getStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(DATA_STATUS))
    }

    override fun getMode(): FaaMode? {
        return if (getFieldCount() > MODE) {
            FaaMode.valueOf(getCharValue(MODE))
        } else {
            null
        }
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
	 * @see
	 * net.sf.marineapi.nmea.sentence.PositionSentence#setPosition(net.sf.marineapi
	 * .nmea.util.Position)
	 */
    override fun setPosition(pos: Position) {
        setPositionValues(
            pos, LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE
        )
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GLLSentence#setDataStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override fun setStatus(status: DataStatus?) {
        setCharValue(DATA_STATUS, status!!.toChar())
    }

    override fun setMode(mode: FaaMode?) {
        if (this.getFieldCount() <= MODE) {
            this.setFieldCount(7)
        }
        setCharValue(MODE, mode!!.toChar())
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
        // field indices
        private const val LATITUDE = 0
        private const val LAT_HEMISPHERE = 1
        private const val LONGITUDE = 2
        private const val LON_HEMISPHERE = 3
        private const val UTC_TIME = 4
        private const val DATA_STATUS = 5
        private const val MODE = 6
    }
}