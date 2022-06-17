/*
 * XTEParser.java
 * Copyright (C) 2014 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://Kimmo Tuukkanenkkan.github.io/marine-api/>
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
import net.sf.marineapi.nmea.sentence.XTESentence
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction
import net.sf.marineapi.nmea.util.FaaMode

/**
 * XTE sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class XTEParser : SentenceParser, XTESentence {
    /**
     * Creates new instance of XTEParser.
     *
     * @param nmea XTE sentence String
     */
    constructor(nmea: String) : super(nmea) {
        setFieldCount(6)
    }

    constructor(talker: TalkerId?) : super(talker, SentenceId.XTE, 6) {
        setMode(FaaMode.NONE)
        setStatus(DataStatus.VOID)
        setCycleLockStatus(DataStatus.VOID)
        setCharValue(DISTANCE_UNIT, 'N')
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getCycleLockStatus()
	 */
    override fun getCycleLockStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(CYCLE_LOCK_STATUS))
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getMagnitude()
	 */
    override fun getMagnitude(): Double {
        return getDoubleValue(DISTANCE)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getMode()
	 */
    override fun getMode(): FaaMode {
        return FaaMode.valueOf(getCharValue(FAA_MODE))
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getStatus()
	 */
    override fun getStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(SIGNAL_STATUS))
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getSteerTo()
	 */
    override fun getSteerTo(): Direction {
        return Direction.valueOf(getCharValue(DIRECTION))
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.XTESentence#setCycleLockStatus(net.sf.
	 * marineapi.nmea.util.DataStatus)
	 */
    override fun setCycleLockStatus(status: DataStatus) {
        setCharValue(CYCLE_LOCK_STATUS, status.toChar())
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#setMagnitude(double)
	 */
    override fun setMagnitude(distance: Double) {
        setDoubleValue(DISTANCE, distance, 0, 2)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.XTESentence#setMode(net.sf.marineapi.nmea
	 * .util.FaaMode)
	 */
    override fun setMode(mode: FaaMode) {
        setCharValue(FAA_MODE, mode.toChar())
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.XTESentence#setStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
    override fun setStatus(status: DataStatus) {
        setCharValue(SIGNAL_STATUS, status.toChar())
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.XTESentence#setSteerTo(net.sf.marineapi
	 * .nmea.util.Direction)
	 */
    override fun setSteerTo(direction: Direction) {
        setCharValue(DIRECTION, direction.toChar())
    }

    companion object {
        private const val SIGNAL_STATUS = 0
        private const val CYCLE_LOCK_STATUS = 1
        private const val DISTANCE = 2
        private const val DIRECTION = 3
        private const val DISTANCE_UNIT = 4
        private const val FAA_MODE = 5
    }
}