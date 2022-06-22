/* 
 * VWTParser.java
 * Copyright (C) 2016 Henri Laurent
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.VWTSentence
import net.sf.marineapi.nmea.util.Direction

/**
 * VWT sentence parser.
 *
 * @author Henri Laurent
 */
internal class VWTParser : SentenceParser, VWTSentence {
    /**
     * Creates a new instance of VWTParser.
     *
     * @param nmea VWT sentence String
     * @throws IllegalArgumentException If specified sentence is invalid
     */
    constructor(nmea: String) : super(nmea, SentenceId.VWT)

    /**
     * Creates VWT parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.VWT, 9) {
        setCharValue(KNOTS_INDICATOR, VWTSentence.KNOT)
        setCharValue(MPS_INDICATOR, VWTSentence.MPS)
        setCharValue(KMPH_INDICATOR, VWTSentence.KMPH)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.VWTSentence#getWindAngle()
	 */
    override fun getWindAngle(): Double {
        return getDoubleValue(WIND_ANGLE_DEGREES)
    }

    override fun getDirectionLeftRight(): Direction {
        return Direction.valueOf(getCharValue(WIND_DIRECTION_LEFT_RIGHT_OF_BOW))
    }

    override fun getSpeedKmh(): Double {
        return getDoubleValue(SPEED_KMPH)
    }

    override fun getSpeedKnots(): Double {
        return getDoubleValue(SPEED_KNOTS)
    }

    val trueCourse: Double
        get() = getDoubleValue(SPEED_MPS)

    override fun setSpeedKmh(kmh: Double) {
        require(kmh >= 0) { "Speed cannot be negative" }
        setDoubleValue(SPEED_KMPH, kmh, 1, 2)
    }

    override fun setSpeedKnots(knots: Double) {
        require(knots >= 0) { "Speed cannot be negative" }
        setDoubleValue(SPEED_KNOTS, knots, 1, 2)
    }

    override fun setWindAngle(mWindAngle: Double) {
        setDegreesValue(WIND_ANGLE_DEGREES, mWindAngle)
    }
    
    override fun setDirectionLeftRight(direction: Direction) {
        setCharValue(WIND_DIRECTION_LEFT_RIGHT_OF_BOW, direction.toChar())
    }

    companion object {
        private const val WIND_ANGLE_DEGREES = 0
        private const val WIND_DIRECTION_LEFT_RIGHT_OF_BOW = 1
        private const val SPEED_KNOTS = 2
        private const val KNOTS_INDICATOR = 3
        private const val SPEED_MPS = 4
        private const val MPS_INDICATOR = 5
        private const val SPEED_KMPH = 6
        private const val KMPH_INDICATOR = 7
    }
}