/* 
 * MTAParser.java
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

import net.sf.marineapi.nmea.sentence.MTASentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.Units

/**
 * MTA sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class MTAParser : SentenceParser, MTASentence {
    /**
     * Constructor.
     *
     * @param mta MTA sentence String to parse.
     */
    constructor(mta: String) : super(mta, SentenceId.MTA)

    /**
     * Constructor for empty MTA sentence.
     *
     * @param talker Talker ID to set.
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.MTA, 2) {
        setCharValue(UNIT_INDICATOR, Units.CELSIUS.toChar())
    }

    override fun getTemperature(): Double {
        return getDoubleValue(TEMPERATURE)
    }

    override fun setTemperature(temp: Double) {
        setDoubleValue(TEMPERATURE, temp, 1, 2)
    }

    companion object {
        private const val TEMPERATURE = 0
        private const val UNIT_INDICATOR = 1
    }
}