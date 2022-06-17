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

import net.sf.marineapi.nmea.sentence.RSDSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
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
     * @see RSDSentence.getOriginOneRange
     */
    override fun getOriginOneRange(): Double {
        return getDoubleValue(ORIGIN_ONE_RANGE)
    }

    /**
     * @see RSDSentence.getOriginOneBearing
     */
    override fun getOriginOneBearing(): Double {
        return getDoubleValue(ORIGIN_ONE_BEARING)
    }

    /**
     * @see RSDSentence.getVRMOneRange
     */
    override fun getVRMOneRange(): Double {
        return getDoubleValue(VRM_ONE_RANGE)
    }

    /**
     * @see RSDSentence.getEBLOneBearing
     */
    override fun getEBLOneBearing(): Double {
        return getDoubleValue(EBL_ONE_BEARING)
    }

    /**
     * @see RSDSentence.getOriginTwoRange
     */
    override fun getOriginTwoRange(): Double {
        return getDoubleValue(ORIGIN_TWO_RANGE)
    }

    /**
     * @see RSDSentence.getOriginTwoBearing
     */
    override fun getOriginTwoBearing(): Double {
        return getDoubleValue(ORIGIN_TWO_BEARING)
    }

    /**
     * @see RSDSentence.getVRMTwoRange
     */
    override fun getVRMTwoRange(): Double {
        return getDoubleValue(VRM_TWO_RANGE)
    }

    /**
     * @see RSDSentence.getEBLTwoBearing
     */
    override fun getEBLTwoBearing(): Double {
        return getDoubleValue(EBL_TWO_BEARING)
    }

    /**
     * @see RSDSentence.getCursorRange
     */
    override fun getCursorRange(): Double {
        return getDoubleValue(CURSOR_RANGE)
    }

    /**
     * @see RSDSentence.getCursorBearing
     */
    override fun getCursorBearing(): Double {
        return getDoubleValue(CURSOR_BEARING)
    }

    /**
     * @see RSDSentence.getRangeScale
     */
    override fun getRangeScale(): Double {
        return getDoubleValue(RANGE_SCALE)
    }

    /**
     * @see RSDSentence.getRangeUnits
     */
    override fun getRangeUnits(): Units {
        return Units.valueOf(getCharValue(RANGE_UNITS))
    }

    /**
     * @see RSDSentence.getDisplayRotation
     */
    override fun getDisplayRotation(): DisplayRotation {
        return DisplayRotation.valueOf(getCharValue(DISPLAY_ROTATION))
    }

    /**
     * @see RSDSentence.setOriginOneRange
     */
    override fun setOriginOneRange(range: Double) {
        setDoubleValue(ORIGIN_ONE_RANGE, range)
    }

    /**
     * @see RSDSentence.setOriginOneBearing
     */
    override fun setOriginOneBearing(bearing: Double) {
        setDoubleValue(ORIGIN_ONE_BEARING, bearing)
    }

    /**
     * @see RSDSentence.setVRMOneRange
     */
    override fun setVRMOneRange(range: Double) {
        setDoubleValue(VRM_ONE_RANGE, range)
    }

    /**
     * @see RSDSentence.setEBLOneBearing
     */
    override fun setEBLOneBearing(bearing: Double) {
        setDoubleValue(EBL_ONE_BEARING, bearing)
    }

    /**
     * @see RSDSentence.setOriginTwoRange
     */
    override fun setOriginTwoRange(range: Double) {
        setDoubleValue(ORIGIN_TWO_RANGE, range)
    }

    /**
     * @see RSDSentence.setOriginTwoBearing
     */
    override fun setOriginTwoBearing(bearing: Double) {
        setDoubleValue(ORIGIN_TWO_BEARING, bearing)
    }

    /**
     * @see RSDSentence.setVRMTwoRange
     */
    override fun setVRMTwoRange(range: Double) {
        setDoubleValue(VRM_TWO_RANGE, range)
    }

    /**
     * @see RSDSentence.setEBLTwoBearing
     */
    override fun setEBLTwoBearing(bearing: Double) {
        setDoubleValue(EBL_TWO_BEARING, bearing)
    }

    /**
     * @see RSDSentence.setCursorRange
     */
    override fun setCursorRange(range: Double) {
        setDoubleValue(CURSOR_RANGE, range)
    }

    /**
     * @see RSDSentence.setCursorBearing
     */
    override fun setCursorBearing(bearing: Double) {
        setDoubleValue(CURSOR_BEARING, bearing)
    }

    /**
     * @see RSDSentence.setRangeScale
     */
    override fun setRangeScale(scale: Double) {
        setDoubleValue(RANGE_SCALE, scale)
    }

    /**
     * @see RSDSentence.setRangeUnits
     */
    override fun setRangeUnits(units: Units?) {
        if (listOf(*VALID_RANGE_UNITS).contains(units)) {
            setCharValue(RANGE_UNITS, units!!.toChar())
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
     * @see RSDSentence.setDisplayRotation
     */
    override fun setDisplayRotation(rotation: DisplayRotation?) {
        setCharValue(DISPLAY_ROTATION, rotation!!.toChar())
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