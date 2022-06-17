/* 
 * HDTParser.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.*

/**
 * HDT sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class HDTParser : SentenceParser, HDTSentence {
    /**
     * Creates a new HDT parser.
     *
     * @param nmea HDT sentence String to parse.
     */
    constructor(nmea: String) : super(nmea, SentenceId.HDT)

    /**
     * Creates a new empty HDT sentence.
     *
     * @param talker Talker id to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.HDT, 2) {
        setCharValue(TRUE_INDICATOR, 'T')
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.parser.HeadingSentence#getHeading()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.parser.HeadingSentence#setHeading(double)
	 */
    override var heading: Double
        get() = getDoubleValue(HEADING)
        set(hdt) {
            setDegreesValue(HEADING, hdt)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HeadingSentence#isTrue()
	 */
    override val isTrue: Boolean
        get() = true

    companion object {
        private const val HEADING = 0
        private const val TRUE_INDICATOR = 1
    }
}