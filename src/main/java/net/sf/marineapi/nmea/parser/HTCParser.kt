/*
 * HTCParser.java
 * Copyright (C) 2015 Paweł Kozioł, Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.HTCSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction
import net.sf.marineapi.nmea.util.SteeringMode
import net.sf.marineapi.nmea.util.TurnMode

/**
 * HTC parser.
 *
 * @author Paweł Kozioł
 */
internal open class HTCParser : SentenceParser, HTCSentence {
    constructor(nmea: String) : super(nmea, SentenceId.HTC)
    constructor(talker: TalkerId?) : super(talker, SentenceId.HTC, 13)
    constructor(nmea: String, type: SentenceId) : super(nmea, type)
    constructor(tid: TalkerId?, sid: SentenceId, size: Int) : super(tid, sid, size)

    override val override: DataStatus?
        get() = if (hasValue(OVERRIDE)) {
            DataStatus.Companion.valueOf(getCharValue(OVERRIDE))
        } else {
            null
        }
    override val rudderAngle: Double
        get() = if (hasValue(COMMANDED_RUDDER_ANGLE)) {
            getDoubleValue(COMMANDED_RUDDER_ANGLE)
        } else {
            Double.NaN
        }
    override val rudderDirection: Direction?
        get() = if (hasValue(COMMANDED_RUDDER_DIRECTION)) {
            Direction.Companion.valueOf(getCharValue(COMMANDED_RUDDER_DIRECTION))
        } else {
            null
        }
    override val steeringMode: SteeringMode?
        get() = if (hasValue(SELECTED_STEERING_MODE)) {
            SteeringMode.Companion.valueOf(getCharValue(SELECTED_STEERING_MODE))
        } else {
            null
        }
    override val turnMode: TurnMode?
        get() = if (hasValue(TURN_MODE)) {
            TurnMode.Companion.valueOf(getCharValue(TURN_MODE))
        } else {
            null
        }
    override val rudderLimit: Double
        get() = if (hasValue(COMMANDED_RUDDER_LIMIT)) {
            getDoubleValue(COMMANDED_RUDDER_LIMIT)
        } else {
            Double.NaN
        }
    override val offHeadingLimit: Double
        get() = if (hasValue(COMMANDED_OFF_HEADING_LIMIT)) {
            getDoubleValue(COMMANDED_OFF_HEADING_LIMIT)
        } else {
            Double.NaN
        }
    override val radiusOfTurn: Double
        get() = if (hasValue(COMMANDED_RADIUS_OF_TURN_FOR_HEADING_CHANGES)) {
            getDoubleValue(COMMANDED_RADIUS_OF_TURN_FOR_HEADING_CHANGES)
        } else {
            Double.NaN
        }
    override val rateOfTurn: Double
        get() = if (hasValue(COMMANDED_RATE_OF_TURN_FOR_HEADING_CHANGES)) {
            getDoubleValue(COMMANDED_RATE_OF_TURN_FOR_HEADING_CHANGES)
        } else {
            Double.NaN
        }
    override val headingToSteer: Double
        get() = if (hasValue(COMMANDED_HEADING_TO_STEER)) {
            getDoubleValue(COMMANDED_HEADING_TO_STEER)
        } else {
            Double.NaN
        }
    override val offTrackLimit: Double
        get() = if (hasValue(COMMANDED_OFF_TRACK_LIMIT)) {
            getDoubleValue(COMMANDED_OFF_TRACK_LIMIT)
        } else {
            Double.NaN
        }
    override val track: Double
        get() = if (hasValue(COMMANDED_TRACK)) {
            getDoubleValue(COMMANDED_TRACK)
        } else {
            Double.NaN
        }
    override val isHeadingTrue: Boolean
        get() = (hasValue(HEADING_REFERENCE_IN_USE)
                && getCharValue(HEADING_REFERENCE_IN_USE) == 'T')

    companion object {
        private const val OVERRIDE = 0
        private const val COMMANDED_RUDDER_ANGLE = 1
        private const val COMMANDED_RUDDER_DIRECTION = 2
        private const val SELECTED_STEERING_MODE = 3
        private const val TURN_MODE = 4
        private const val COMMANDED_RUDDER_LIMIT = 5
        private const val COMMANDED_OFF_HEADING_LIMIT = 6
        private const val COMMANDED_RADIUS_OF_TURN_FOR_HEADING_CHANGES = 7
        private const val COMMANDED_RATE_OF_TURN_FOR_HEADING_CHANGES = 8
        private const val COMMANDED_HEADING_TO_STEER = 9
        private const val COMMANDED_OFF_TRACK_LIMIT = 10
        private const val COMMANDED_TRACK = 11
        private const val HEADING_REFERENCE_IN_USE = 12
    }
}