/*
 * RPMParser.java
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

import net.sf.marineapi.nmea.sentence.RPMSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus

/**
 * RPM parser
 *
 * @author Kimmo Tuukkanen
 */
internal class RPMParser : SentenceParser, RPMSentence {
    /**
     * Creates a new instance of RPMParser.
     *
     * @param nmea NMEA sentence String.
     */
    constructor(nmea: String) : super(nmea)

    /**
     * Creates a new empty parser.
     *
     * @param talker TalkerId to set.
     */
    constructor(talker: TalkerId?) : super(talker, "RPM", 5)

    /*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getId()
	 */
    override fun getId(): Int {
        return getIntValue(SOURCE_NUMBER)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getPitch()
	 */
    override fun getPitch(): Double {
        return getDoubleValue(PITCH)
    }

    override fun getRPM(): Double {
        return getDoubleValue(REVOLUTIONS)
    }

    override fun getSource(): Char {
        return getCharValue(SOURCE)
    }

    override fun getStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(STATUS))
    }

    override fun isEngine(): Boolean {
        return getCharValue(SOURCE) == RPMSentence.ENGINE
    }

    override fun isShaft(): Boolean {
        return getCharValue(SOURCE) == RPMSentence.SHAFT
    }

    override fun setId(id: Int) {
        setIntValue(SOURCE_NUMBER, id)
    }

    override fun setPitch(pitch: Double) {
        setDoubleValue(PITCH, pitch, 1, 1)
    }

    override fun setRPM(rpm: Double) {
        setDoubleValue(REVOLUTIONS, rpm)
    }

    override fun setSource(source: Char) {
        require(!(source != RPMSentence.ENGINE && source != RPMSentence.SHAFT)) { "Invalid source indicator, expected 'E' or 'S'" }
        setCharValue(SOURCE, source)
    }

    override fun setStatus(status: DataStatus?) {
        setCharValue(STATUS, status!!.toChar())
    }

    companion object {
        private const val SOURCE = 0
        private const val SOURCE_NUMBER = 1
        private const val REVOLUTIONS = 2
        private const val PITCH = 3
        private const val STATUS = 4
    }
}