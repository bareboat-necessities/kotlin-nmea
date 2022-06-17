/*
 * RSDParser.java
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

import net.sf.marineapi.nmea.sentence.*
import net.sf.marineapi.nmea.util.DisplayRotation
import net.sf.marineapi.nmea.util.Units
import java.util.*


/**
 * RSD sentence parser
 *
 * @author Joshua Sweaney
 */
internal class RSDParser : SentenceParser, RSDSentence {
    /**
     * Creates a new instance of RSD parser
     *
     * @param nmea RSD sentence string.
     */
    constructor(nmea: String) : super(nmea, SentenceId.RSD)

    /**
     * Creates RSD parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.RSD, 13)
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getOriginOneRange
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setOriginOneRange
     */
    override var originOneRange: Double
        get() = getDoubleValue(ORIGIN_ONE_RANGE)
        set(range) {
            setDoubleValue(ORIGIN_ONE_RANGE, range)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getOriginOneBearing
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setOriginOneBearing
     */
    override var originOneBearing: Double
        get() = getDoubleValue(ORIGIN_ONE_BEARING)
        set(bearing) {
            setDoubleValue(ORIGIN_ONE_BEARING, bearing)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getVRMOneRange
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setVRMOneRange
     */
    override var vRMOneRange: Double
        get() = getDoubleValue(VRM_ONE_RANGE)
        set(range) {
            setDoubleValue(VRM_ONE_RANGE, range)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getEBLOneBearing
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setEBLOneBearing
     */
    override var eBLOneBearing: Double
        get() = getDoubleValue(EBL_ONE_BEARING)
        set(bearing) {
            setDoubleValue(EBL_ONE_BEARING, bearing)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getOriginTwoRange
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setOriginTwoRange
     */
    override var originTwoRange: Double
        get() = getDoubleValue(ORIGIN_TWO_RANGE)
        set(range) {
            setDoubleValue(ORIGIN_TWO_RANGE, range)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getOriginTwoBearing
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setOriginTwoBearing
     */
    override var originTwoBearing: Double
        get() = getDoubleValue(ORIGIN_TWO_BEARING)
        set(bearing) {
            setDoubleValue(ORIGIN_TWO_BEARING, bearing)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getVRMTwoRange
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setVRMTwoRange
     */
    override var vRMTwoRange: Double
        get() = getDoubleValue(VRM_TWO_RANGE)
        set(range) {
            setDoubleValue(VRM_TWO_RANGE, range)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getEBLTwoBearing
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setEBLTwoBearing
     */
    override var eBLTwoBearing: Double
        get() = getDoubleValue(EBL_TWO_BEARING)
        set(bearing) {
            setDoubleValue(EBL_TWO_BEARING, bearing)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getCursorRange
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setCursorRange
     */
    override var cursorRange: Double
        get() = getDoubleValue(CURSOR_RANGE)
        set(range) {
            setDoubleValue(CURSOR_RANGE, range)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getCursorBearing
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setCursorBearing
     */
    override var cursorBearing: Double
        get() = getDoubleValue(CURSOR_BEARING)
        set(bearing) {
            setDoubleValue(CURSOR_BEARING, bearing)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getRangeScale
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setRangeScale
     */
    override var rangeScale: Double
        get() = getDoubleValue(RANGE_SCALE)
        set(scale) {
            setDoubleValue(RANGE_SCALE, scale)
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getRangeUnits
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setRangeUnits
     */
    override var rangeUnits: Units
        get() = Units.Companion.valueOf(getCharValue(RANGE_UNITS))
        set(units) {
            if (Arrays.asList(*VALID_RANGE_UNITS).contains(units)) {
                setCharValue(RANGE_UNITS, units.toChar())
            } else {
                var err = "Range units must be "
                for (i in VALID_RANGE_UNITS.indices) {
                    val u = VALID_RANGE_UNITS[i]
                    err += u.name + "(" + u.toChar() + ")"
                    if (i != VALID_RANGE_UNITS.size - 1) {
                        err += ", "
                    }
                }
                throw IllegalArgumentException(err)
            }
        }
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.getDisplayRotation
     */
    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence.setDisplayRotation
     */
    override var displayRotation: DisplayRotation
        get() = DisplayRotation.Companion.valueOf(getCharValue(DISPLAY_ROTATION))
        set(rotation) {
            setCharValue(DISPLAY_ROTATION, rotation.toChar())
        }

    companion object {
        private const val ORIGIN_ONE_RANGE = 0
        private const val ORIGIN_ONE_BEARING = 1
        private const val VRM_ONE_RANGE = 2
        private const val EBL_ONE_BEARING = 3
        private const val ORIGIN_TWO_RANGE = 4
        private const val ORIGIN_TWO_BEARING = 5
        private const val VRM_TWO_RANGE = 6
        private const val EBL_TWO_BEARING = 7
        private const val CURSOR_RANGE = 8
        private const val CURSOR_BEARING = 9
        private const val RANGE_SCALE = 10
        private const val RANGE_UNITS = 11
        private const val DISPLAY_ROTATION = 12
        private val VALID_RANGE_UNITS = arrayOf(Units.KILOMETERS, Units.NAUTICAL_MILES, Units.STATUTE_MILES)
    }
}