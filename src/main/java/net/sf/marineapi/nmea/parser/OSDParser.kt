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

import net.sf.marineapi.nmea.sentence.OSDSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.*import java.util.*

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
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.getHeading
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.setHeading
     */
    override var heading: Double
        get() = getDoubleValue(HEADING)
        set(heading) {
            setDoubleValue(HEADING, heading)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.getHeadingStatus
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.setHeadingStatus
     */
    override var headingStatus: DataStatus
        get() = DataStatus.Companion.valueOf(getCharValue(HEADING_STATUS))
        set(status) {
            setCharValue(HEADING_STATUS, status.toChar())
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.getCourse
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.setCourse
     */
    override var course: Double
        get() = getDoubleValue(COURSE)
        set(course) {
            setDoubleValue(COURSE, course)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.getCourseReference
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.setCourseReference
     */
    override var courseReference: ReferenceSystem
        get() = ReferenceSystem.Companion.valueOf(getCharValue(COURSE_REFERENCE))
        set(reference) {
            setCharValue(COURSE_REFERENCE, reference.toChar())
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.getSpeed
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.setSpeed
     */
    override var speed: Double
        get() = getDoubleValue(SPEED)
        set(speed) {
            setDoubleValue(SPEED, speed)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.getSpeedReference
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.setSpeedReference
     */
    override var speedReference: ReferenceSystem
        get() = ReferenceSystem.Companion.valueOf(getCharValue(SPEED_REFERENCE))
        set(reference) {
            setCharValue(SPEED_REFERENCE, reference.toChar())
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.getVesselSet
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.setVesselSet
     */
    override var vesselSet: Double
        get() = getDoubleValue(VESSEL_SET)
        set(set) {
            setDoubleValue(VESSEL_SET, set)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.getVesselDrift
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.setVesselDrift
     */
    override var vesselDrift: Double
        get() = getDoubleValue(VESSEL_DRIFT)
        set(drift) {
            setDoubleValue(VESSEL_DRIFT, drift)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.getSpeedUnits
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.OSDSentence.setSpeedUnits
     */
    override var speedUnits: Units
        get() = Units.Companion.valueOf(getCharValue(SPEED_UNITS))
        set(units) {
            if (Arrays.asList(*VALID_SPEED_UNITS).contains(units)) {
                setCharValue(SPEED_UNITS, units.toChar())
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