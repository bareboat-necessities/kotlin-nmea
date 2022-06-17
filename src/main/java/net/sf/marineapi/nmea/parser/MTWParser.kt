/* 
 * MTWParser.java
 * Copyright (C) 2011 Warren Zahra
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

import net.sf.marineapi.nmea.sentence.MTWSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.Units

/**
 * MTW Sentence parser.
 *
 * @author Warren Zahra, Kimmo Tuukkanen
 */
internal class MTWParser : SentenceParser, MTWSentence {
    /**
     * Creates new instance of MTWParser with specified sentence.
     *
     * @param nmea MTW sentence string
     */
    constructor(nmea: String) : super(nmea)

    /**
     * Creates new MTW parse without data.
     *
     * @param tid TalkerId to set
     */
    constructor(tid: TalkerId?) : super(tid, SentenceId.MTW, 2) {
        setCharValue(UNIT_INDICATOR, Units.CELSIUS.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MTWSentence#getTemperature()
	 */
    override fun getTemperature(): Double {
        return getDoubleValue(TEMPERATURE)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MTWSentence#setTemperature(double)
	 */
    override fun setTemperature(temp: Double) {
        setDoubleValue(TEMPERATURE, temp, 1, 2)
    }

    companion object {
        private const val TEMPERATURE = 0
        private const val UNIT_INDICATOR = 1
    }
}