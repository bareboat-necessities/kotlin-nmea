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

import net.sf.marineapi.nmea.sentence.RPMSentenceimport

net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.DataStatus
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
	 *//*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#setId(int)
	 */
    override var id: Int
        get() = getIntValue(SOURCE_NUMBER)
        set(id) {
            setIntValue(SOURCE_NUMBER, id)
        }

    /*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getPitch()
	 *//*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#setPitch(double)
	 */
    override var pitch: Double
        get() = getDoubleValue(PITCH)
        set(pitch) {
            setDoubleValue(PITCH, pitch, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getRPM()
	 *//*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#setRPM()
	 */
    override var rPM: Double
        get() = getDoubleValue(REVOLUTIONS)
        set(rpm) {
            setDoubleValue(REVOLUTIONS, rpm)
        }

    /*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getSource()
	 *//*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#setSource(char)
	 */
    override var source: Char
        get() = getCharValue(SOURCE)
        set(source) {
            require(!(source != RPMSentence.Companion.ENGINE && source != RPMSentence.Companion.SHAFT)) { "Invalid source indicator, expected 'E' or 'S'" }
            setCharValue(SOURCE, source)
        }

    /*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getStatus()
	 *//*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.sf.marineapi.nmea.sentence.RPMSentence#setStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override var status: DataStatus
        get() = DataStatus.Companion.valueOf(getCharValue(STATUS))
        set(status) {
            setCharValue(STATUS, status.toChar())
        }

    /*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#isEngine()
	 */
    override val isEngine: Boolean
        get() = getCharValue(SOURCE) == RPMSentence.Companion.ENGINE

    /*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#isShaft()
	 */
    override val isShaft: Boolean
        get() = getCharValue(SOURCE) == RPMSentence.Companion.SHAFT

    companion object {
        private const val SOURCE = 0
        private const val SOURCE_NUMBER = 1
        private const val REVOLUTIONS = 2
        private const val PITCH = 3
        private const val STATUS = 4
    }
}