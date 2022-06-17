/* 
 * CURParser.java
 * Copyright (C) 2016 Henri Laurent
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

import net.sf.marineapi.nmea.sentence.CURSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerId
/**
 * CUR sentence parser.
 *
 * @author Henri Laurent
 * @see CURSentence
 */
internal class CURParser : SentenceParser, CURSentence {
    /**
     * Creates a new instance of CUR parser.
     *
     * @param nmea CUR sentence String
     * @throws IllegalArgumentException If specified String is invalid or does
     * not contain a CUR sentence.
     */
    constructor(nmea: String) : super(nmea, SentenceId.CUR)

    /**
     * Creates CUR parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.CUR, 11) {
        setCharValue(DIRECTION_REFERENCE, 'T')
        setCharValue(HEADING_REFERENCE, 'T')
        setCharValue(SPEED_REFERENCE, 'B')
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#getCurrentDirection()
	 */
    override val currentDirection: Double
        get() = getDoubleValue(CURRENT_DIRECTION)

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#getCurrentDirectionReference()
	 */
    override val currentDirectionReference: String
        get() = getStringValue(DIRECTION_REFERENCE)

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#getCurrentHeadingReference()
	 */
    override val currentHeadingReference: String
        get() = getStringValue(HEADING_REFERENCE)

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getCurrentSpeed()
	 */
    override val currentSpeed: Double
        get() = getDoubleValue(CURRENT_SPEED)

    companion object {
        // field indices
        private const val DATA_STATUS = 0
        private const val DATA_SET = 1
        private const val LAYER = 2
        private const val CURRENT_DEPTH = 3 // in meters
        private const val CURRENT_DIRECTION = 4 // in degrees
        private const val DIRECTION_REFERENCE = 5 // True/Relative T/R
        private const val CURRENT_SPEED = 6 // in knots
        private const val REFERENCE_LAYER_DEPTH = 7 // in meters
        private const val CURRENT_HEADING = 8
        private const val HEADING_REFERENCE = 9 // True/Magentic T/M
        private const val SPEED_REFERENCE = 10 // Bottom/Water/Positioning system B/W/P
    }
}