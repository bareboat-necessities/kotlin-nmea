/* 
 * HDGParser.java
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

import net.sf.marineapi.nmea.sentence.HDGSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.CompassPoint
/**
 * HDG sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class HDGParser : SentenceParser, HDGSentence {
    /**
     * Creates a new HDG parser.
     *
     * @param nmea HDG sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.HDG)

    /**
     * Creates a new empty HDG parser.
     *
     * @param talker Talker id to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.HDG, 5)

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HDGSentence#getDeviation()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HDGSentence#setDeviation(double)
	 */
    override var deviation: Double
        get() {
            val dev = getDoubleValue(DEVIATION)
            if (dev == 0.0) {
                return dev
            }
            val dir: CompassPoint = CompassPoint.Companion.valueOf(getCharValue(DEV_DIRECTION))
            return if (dir == CompassPoint.WEST) -dev else dev
        }
        set(deviation) {
            require(!(deviation < -180 || deviation > 180)) { "Value out of range [-180..180]" }
            if (deviation > 0) {
                setCharValue(DEV_DIRECTION, CompassPoint.EAST.toChar())
            } else if (deviation < 0) {
                setCharValue(DEV_DIRECTION, CompassPoint.WEST.toChar())
            } else {
                setStringValue(DEV_DIRECTION, "")
            }
            setDoubleValue(DEVIATION, Math.abs(deviation), 3, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HDGSentence#getHeading()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HDGSentence#setHeading(double)
	 */
    override var heading: Double
        get() = getDoubleValue(HEADING)
        set(heading) {
            setDegreesValue(HEADING, heading)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HDGSentence#getVariation()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HDGSentence#setVariation(double)
	 */
    override var variation: Double
        get() {
            val `var` = getDoubleValue(VARIATION)
            if (`var` == 0.0) {
                return `var`
            }
            val dir: CompassPoint = CompassPoint.Companion.valueOf(getCharValue(VAR_DIRECTION))
            return if (dir == CompassPoint.WEST) -`var` else `var`
        }
        set(variation) {
            require(!(variation < -180 || variation > 180)) { "Value out of range [-180..180]" }
            if (variation > 0) {
                setCharValue(VAR_DIRECTION, CompassPoint.EAST.toChar())
            } else if (variation < 0) {
                setCharValue(VAR_DIRECTION, CompassPoint.WEST.toChar())
            } else {
                setStringValue(VAR_DIRECTION, "")
            }
            setDoubleValue(VARIATION, Math.abs(variation), 3, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HeadingSentence#isTrue()
	 */
    override val isTrue: Boolean
        get() = false

    companion object {
        private const val HEADING = 0
        private const val DEVIATION = 1
        private const val DEV_DIRECTION = 2
        private const val VARIATION = 3
        private const val VAR_DIRECTION = 4
    }
}