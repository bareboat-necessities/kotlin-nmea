/*
 * RSAParser.java
 * Copyright (C) 2014 Lázár József, Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.RSASentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Side

/**
 * RSA sentence parser.
 *
 * @author Lázár József, Kimmo Tuukkanen
 */
internal class RSAParser : SentenceParser, RSASentence {
    /**
     * Creates a new instance of RSAParser.
     *
     * @param nmea RSA sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.RSA) {}

    /**
     * Creates a new instance of RSAParser with empty data fields.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.RSA, 4) {
        setStatus(Side.STARBOARD, DataStatus.VOID)
        setStatus(Side.PORT, DataStatus.VOID)
    }

    override fun getRudderAngle(side: Side): Double {
        return if (Side.STARBOARD == side) {
            getDoubleValue(STARBOARD_SENSOR)
        } else getDoubleValue(PORT_SENSOR)
    }

    override fun setRudderAngle(side: Side, angle: Double) {
        if (Side.STARBOARD == side) {
            setDoubleValue(STARBOARD_SENSOR, angle)
        } else {
            setDoubleValue(PORT_SENSOR, angle)
        }
    }

    override fun getStatus(side: Side): DataStatus {
        return if (Side.STARBOARD == side) {
            DataStatus.valueOf(getCharValue(STARBOARD_STATUS))
        } else DataStatus.valueOf(getCharValue(PORT_STATUS))
    }

    override fun setStatus(side: Side, status: DataStatus) {
        if (Side.STARBOARD == side) {
            setCharValue(STARBOARD_STATUS, status.toChar())
        } else {
            setCharValue(PORT_STATUS, status.toChar())
        }
    }

    companion object {
        private const val STARBOARD_SENSOR = 0
        private const val STARBOARD_STATUS = 1
        private const val PORT_SENSOR = 2
        private const val PORT_STATUS = 3
    }
}