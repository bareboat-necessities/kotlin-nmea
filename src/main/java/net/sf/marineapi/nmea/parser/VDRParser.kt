/*
 * VDRParser.java
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

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.VDRSentence
import net.sf.marineapi.nmea.util.Units

/**
 * VDR sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class VDRParser : SentenceParser, VDRSentence {
    /**
     * Creates a new instance of VDRParser.
     *
     * @param nmea VDR sentence String
     */
    constructor(nmea: String) : super(nmea)

    /**
     * Creates a new empty instance of VDRParser.
     *
     * @param tid TalkerId to set
     */
    constructor(tid: TalkerId?) : super(tid, SentenceId.VDR, 6) {
        setCharValue(TRUE_INDICATOR, 'T')
        setCharValue(MAGN_INDICATOR, 'M')
        setCharValue(SPEED_UNITS, Units.NAUTICAL_MILES.toChar())
    }

    override fun getMagneticDirection(): Double {
        return getDoubleValue(MAGN_DIRECTION)
    }

    override fun getSpeed(): Double {
        return getDoubleValue(SPEED)
    }

    override fun getTrueDirection(): Double {
        return getDoubleValue(TRUE_DIRECTION)
    }

    override fun setMagneticDirection(direction: Double) {
        setDegreesValue(MAGN_DIRECTION, direction)
    }

    override fun setSpeed(speed: Double) {
        setDoubleValue(SPEED, speed, 0, 1)
    }

    override fun setTrueDirection(direction: Double) {
        setDegreesValue(TRUE_DIRECTION, direction)
    }

    companion object {
        private const val TRUE_DIRECTION = 0
        private const val TRUE_INDICATOR = 1
        private const val MAGN_DIRECTION = 2
        private const val MAGN_INDICATOR = 3
        private const val SPEED = 4
        private const val SPEED_UNITS = 5
    }
}