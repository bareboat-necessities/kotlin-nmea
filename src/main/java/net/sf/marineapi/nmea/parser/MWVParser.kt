/* 
 * MWVParser.java
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

import net.sf.marineapi.nmea.sentence.MWVSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Units

/**
 * MWV sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class MWVParser : SentenceParser, MWVSentence {
    /**
     * Creates a new instance of MWVParser.
     *
     * @param nmea MWV sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.MWV) {}

    /**
     * Creates a new empty instance of MWVParser.
     *
     * @param talker Talker id to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.MWV, 5) {
        setCharValue(DATA_STATUS, DataStatus.VOID.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MWVSentence#getAngle()
	 */
    override fun getAngle(): Double {
        return getDoubleValue(WIND_ANGLE)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MWVSentence#getSpeed()
	 */
    override fun getSpeed(): Double {
        return getDoubleValue(WIND_SPEED)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MWVSentence#getSpeedUnit()
	 */
    override fun getSpeedUnit(): Units {
        return Units.valueOf(getCharValue(SPEED_UNITS))
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MWVSentence#getStatus()
	 */
    override fun getStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(DATA_STATUS))
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MWVSentence#isTrue()
	 */
    override fun isTrue(): Boolean {
        val ch = getCharValue(REFERENCE)
        return ch == 'T'
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MWVSentence#setAngle(double)
	 */
    override fun setAngle(angle: Double) {
        setDegreesValue(WIND_ANGLE, angle)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MWVSentence#setSpeed(double)
	 */
    override fun setSpeed(speed: Double) {
        require(speed >= 0) { "Speed must be positive" }
        setDoubleValue(WIND_SPEED, speed, 1, 1)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.MWVSentence#setSpeedUnit(net.sf.marineapi
	 * .nmea.util.Units)
	 */
    override fun setSpeedUnit(unit: Units?) {
        if (unit == Units.METER || unit == Units.KILOMETERS || unit == Units.NAUTICAL_MILES) {
            setCharValue(SPEED_UNITS, unit.toChar())
            return
        }
        throw IllegalArgumentException("Invalid unit for speed")
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.MWVSentence#setStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override fun setStatus(status: DataStatus?) {
        setCharValue(DATA_STATUS, status!!.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MWVSentence#setTrue(boolean)
	 */
    override fun setTrue(isTrue: Boolean) {
        if (isTrue) {
            setCharValue(REFERENCE, 'T')
        } else {
            setCharValue(REFERENCE, 'R')
        }
    }

    companion object {
        private const val WIND_ANGLE = 0
        private const val REFERENCE = 1
        private const val WIND_SPEED = 2
        private const val SPEED_UNITS = 3
        private const val DATA_STATUS = 4
    }
}