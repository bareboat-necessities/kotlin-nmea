/* 
 * HDMParser.java
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

import net.sf.marineapi.nmea.sentence.HDMSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerId
/**
 * HDM sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class HDMParser : SentenceParser, HDMSentence {
    /**
     * Creates a new HDM parser.
     *
     * @param nmea HDM sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.HDM)

    /**
     * Creates a new empty HDM sentence.
     *
     * @param talker Talker id to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.HDM, 2) {
        setCharValue(MAGN_INDICATOR, 'M')
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HDMSentence#getHeading()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HDMSentence#setHeading(double)
	 */
    override var heading: Double
        get() = getDoubleValue(HEADING)
        set(hdm) {
            setDegreesValue(HEADING, hdm)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HeadingSentence#isTrue()
	 */
    override val isTrue: Boolean
        get() = false

    companion object {
        private const val HEADING = 0
        private const val MAGN_INDICATOR = 1
    }
}