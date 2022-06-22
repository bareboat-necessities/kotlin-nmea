/* 
 * VTGParser.java
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

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.VTGSentence
import net.sf.marineapi.nmea.util.FaaMode

/**
 * VTG sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class VTGParser : SentenceParser, VTGSentence {
    /**
     * Creates a new instance of VTGParser.
     *
     * @param nmea VTG sentence String
     * @throws IllegalArgumentException If specified sentence is invalid
     */
    constructor(nmea: String) : super(nmea, SentenceId.VTG)

    /**
     * Creates VTG parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.VTG, 9) {
        setCharValue(TRUE_INDICATOR, VTGSentence.TRUE)
        setCharValue(MAGNETIC_INDICATOR, VTGSentence.MAGNETIC)
        setCharValue(KNOTS_INDICATOR, VTGSentence.KNOT)
        setCharValue(KMPH_INDICATOR, VTGSentence.KMPH)
    }

    override fun getMagneticCourse(): Double {
        return getDoubleValue(MAGNETIC_COURSE)
    }

    override fun getMode(): FaaMode {
        return FaaMode.valueOf(getCharValue(MODE))
    }

    override fun getSpeedKmh(): Double {
        return getDoubleValue(SPEED_KMPH)
    }

    override fun getSpeedKnots(): Double {
        return getDoubleValue(SPEED_KNOTS)
    }

    override fun getTrueCourse(): Double {
        return getDoubleValue(TRUE_COURSE)
    }

    override fun setMagneticCourse(mcog: Double) {
        setDegreesValue(MAGNETIC_COURSE, mcog)
    }

    override fun setMode(mode: FaaMode) {
        setFieldCount(9)
        setCharValue(MODE, mode.toChar())
    }

    override fun setSpeedKmh(kmh: Double) {
        require(kmh >= 0) { "Speed cannot be negative" }
        setDoubleValue(SPEED_KMPH, kmh, 1, 2)
    }

    override fun setSpeedKnots(knots: Double) {
        require(knots >= 0) { "Speed cannot be negative" }
        setDoubleValue(SPEED_KNOTS, knots, 1, 2)
    }

    override fun setTrueCourse(tcog: Double) {
        setDegreesValue(TRUE_COURSE, tcog)
    }

    companion object {
        private const val TRUE_COURSE = 0
        private const val TRUE_INDICATOR = 1
        private const val MAGNETIC_COURSE = 2
        private const val MAGNETIC_INDICATOR = 3
        private const val SPEED_KNOTS = 4
        private const val KNOTS_INDICATOR = 5
        private const val SPEED_KMPH = 6
        private const val KMPH_INDICATOR = 7
        private const val MODE = 8
    }
}