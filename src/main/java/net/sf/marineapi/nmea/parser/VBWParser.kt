/*
 * VBWParser.java
 * Copyright (C) 2015 ESRG LLC.
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
import net.sf.marineapi.nmea.sentence.VBWSentence
import net.sf.marineapi.nmea.util.DataStatus

/**
 * VBW sentence parser.
 *
 * @author Jeremy Wilson
 */
internal class VBWParser : SentenceParser, VBWSentence {
    /**
     * Create a new instance of VBWParser.
     *
     * @param nmea VBW sentence String.
     * @throws IllegalArgumentException If specified sentence is invalid.
     */
    constructor(nmea: String) : super(nmea, SentenceId.VBW) {}

    /**
     * Create a VBW parser with an empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.VBW, 10) {}

    override fun getLongWaterSpeed(): Double {
        return getDoubleValue(LONG_WATERSPEED)
    }

    override fun getWaterSpeedStatus(): DataStatus {
        return DataStatus.Companion.valueOf(getCharValue(WATER_SPEED_STATUS))
    }

    override fun getGroundSpeedStatus(): DataStatus {
        return DataStatus.Companion.valueOf(getCharValue(GROUND_SPEED_STATUS))
    }

    override fun getLongGroundSpeed(): Double {
        return getDoubleValue(LONG_GROUNDSPEED)
    }

    override fun getTravWaterSpeed(): Double {
        return getDoubleValue(TRAV_WATERSPEED)
    }

    override fun getTravGroundSpeed(): Double {
        return getDoubleValue(TRAV_GROUNDSPEED)
    }

    override fun getSternWaterSpeed(): Double {
        return getDoubleValue(STERN_WATERSPEED)
    }

    override fun getSternWaterSpeedStatus(): DataStatus {
        return DataStatus.Companion.valueOf(getCharValue(STERN_SPEED_STATUS))
    }

    override fun getSternGroundSpeed(): Double {
        return getDoubleValue(STERN_GROUNDSPEED)
    }

    override fun getSternGroundSpeedStatus(): DataStatus {
        return DataStatus.Companion.valueOf(getCharValue(STERN_GROUNDSPEED_STATUS))
    }

    override fun setLongWaterSpeed(speed: Double) {
        setDoubleValue(LONG_WATERSPEED, speed, 2, 1)
    }

    override fun setLongGroundSpeed(speed: Double) {
        setDoubleValue(LONG_GROUNDSPEED, speed, 2, 1)
    }

    override fun setTravWaterSpeed(speed: Double) {
        setDoubleValue(TRAV_WATERSPEED, speed, 2, 1)
    }

    override fun setTravGroundSpeed(speed: Double) {
        setDoubleValue(TRAV_GROUNDSPEED, speed, 2, 1)
    }

    override fun setWaterSpeedStatus(status: DataStatus) {
        setCharValue(WATER_SPEED_STATUS, status.toChar())
    }

    override fun setGroundSpeedStatus(status: DataStatus) {
        setCharValue(GROUND_SPEED_STATUS, status.toChar())
    }

    override fun setSternWaterSpeed(speed: Double) {
        setDoubleValue(STERN_WATERSPEED, speed, 2, 1)
    }

    override fun setSternWaterSpeedStatus(status: DataStatus) {
        setCharValue(STERN_SPEED_STATUS, status.toChar())
    }

    override fun setSternGroundSpeed(speed: Double) {
        setDoubleValue(STERN_GROUNDSPEED, speed, 2, 1)
    }

    override fun setSternGroundSpeedStatus(status: DataStatus) {
        setCharValue(STERN_GROUNDSPEED_STATUS, status.toChar())
    }

    companion object {
        const val LONG_WATERSPEED = 0
        const val TRAV_WATERSPEED = 1
        const val WATER_SPEED_STATUS = 2
        const val LONG_GROUNDSPEED = 3
        const val TRAV_GROUNDSPEED = 4
        const val GROUND_SPEED_STATUS = 5
        const val STERN_WATERSPEED = 6
        const val STERN_SPEED_STATUS = 7
        const val STERN_GROUNDSPEED = 8
        const val STERN_GROUNDSPEED_STATUS = 9
    }
}