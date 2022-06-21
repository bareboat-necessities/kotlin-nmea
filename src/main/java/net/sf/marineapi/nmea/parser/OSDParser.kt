/*
 * OSDParser.java
 * Copyright (C) 2020 Joshua Sweaney
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

import net.sf.marineapi.nmea.sentence.OSDSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.ReferenceSystem
import net.sf.marineapi.nmea.util.Units


/**
 * OSD sentence parser
 *
 * @author Joshua Sweaney
 */
internal class OSDParser : SentenceParser, OSDSentence {
    /**
     * Creates a new instance of OSD parser
     *
     * @param nmea OSD sentence string.
     */
    constructor(nmea: String) : super(nmea, SentenceId.OSD)

    /**
     * Creates OSD parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.OSD, 9)

    /**
     * @see OSDSentence.getHeading
     */
    override fun getHeading(): Double {
        return getDoubleValue(HEADING)
    }

    /**
     * @see OSDSentence.getHeadingStatus
     */
    override fun getHeadingStatus(): DataStatus {
        return DataStatus.valueOf(getCharValue(HEADING_STATUS))
    }

    /**
     * @see OSDSentence.getCourse
     */
    override fun getCourse(): Double {
        return getDoubleValue(COURSE)
    }

    /**
     * @see OSDSentence.getCourseReference
     */
    override fun getCourseReference(): ReferenceSystem {
        return ReferenceSystem.valueOf(getCharValue(COURSE_REFERENCE))
    }

    /**
     * @see OSDSentence.getSpeed
     */
    override fun getSpeed(): Double {
        return getDoubleValue(SPEED)
    }

    /**
     * @see OSDSentence.getSpeedReference
     */
    override fun getSpeedReference(): ReferenceSystem {
        return ReferenceSystem.valueOf(getCharValue(SPEED_REFERENCE))
    }

    /**
     * @see OSDSentence.getVesselSet
     */
    override fun getVesselSet(): Double {
        return getDoubleValue(VESSEL_SET)
    }

    /**
     * @see OSDSentence.getVesselDrift
     */
    override fun getVesselDrift(): Double {
        return getDoubleValue(VESSEL_DRIFT)
    }

    /**
     * @see OSDSentence.getSpeedUnits
     */
    override fun getSpeedUnits(): Units {
        return Units.valueOf(getCharValue(SPEED_UNITS))
    }

    /**
     * @see OSDSentence.setHeading
     */
    override fun setHeading(heading: Double) {
        setDoubleValue(HEADING, heading)
    }

    /**
     * @see OSDSentence.setHeadingStatus
     */
    override fun setHeadingStatus(status: DataStatus?) {
        setCharValue(HEADING_STATUS, status!!.toChar())
    }

    /**
     * @see OSDSentence.setCourse
     */
    override fun setCourse(course: Double) {
        setDoubleValue(COURSE, course)
    }

    /**
     * @see OSDSentence.setCourseReference
     */
    override fun setCourseReference(reference: ReferenceSystem?) {
        setCharValue(COURSE_REFERENCE, reference!!.toChar())
    }

    /**
     * @see OSDSentence.setSpeed
     */
    override fun setSpeed(speed: Double) {
        setDoubleValue(SPEED, speed)
    }

    /**
     * @see OSDSentence.setSpeedReference
     */
    override fun setSpeedReference(reference: ReferenceSystem?) {
        setCharValue(SPEED_REFERENCE, reference!!.toChar())
    }

    /**
     * @see OSDSentence.setVesselSet
     */
    override fun setVesselSet(set: Double) {
        setDoubleValue(VESSEL_SET, set)
    }

    /**
     * @see OSDSentence.setVesselDrift
     */
    override fun setVesselDrift(drift: Double) {
        setDoubleValue(VESSEL_DRIFT, drift)
    }

    /**
     * @see OSDSentence.setSpeedUnits
     */
    override fun setSpeedUnits(units: Units?) {
        if (listOf(*VALID_SPEED_UNITS).contains(units)) {
            setCharValue(SPEED_UNITS, units!!.toChar())
        } else {
            var err = "Speed units must be "
            for (i in VALID_SPEED_UNITS.indices) {
                val u = VALID_SPEED_UNITS[i]
                err += u.name + "(" + u.toChar() + ")"
                if (i != VALID_SPEED_UNITS.size - 1) {
                    err += ", "
                }
            }
            throw IllegalArgumentException(err)
        }
    }

    companion object {
        private const val HEADING = 0
        private const val HEADING_STATUS = 1
        private const val COURSE = 2
        private const val COURSE_REFERENCE = 3
        private const val SPEED = 4
        private const val SPEED_REFERENCE = 5
        private const val VESSEL_SET = 6
        private const val VESSEL_DRIFT = 7
        private const val SPEED_UNITS = 8
        private val VALID_SPEED_UNITS = arrayOf(Units.KILOMETERS, Units.NAUTICAL_MILES, Units.STATUTE_MILES)
    }
}