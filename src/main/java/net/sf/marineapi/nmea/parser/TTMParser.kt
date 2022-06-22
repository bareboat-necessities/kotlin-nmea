/*
 * TTMParser.java
 * Copyright (C) 2014-2020 Johan Bergkvist, Joshua Sweaney
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
import net.sf.marineapi.nmea.sentence.TTMSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.AcquisitionType
import net.sf.marineapi.nmea.util.TargetStatus
import net.sf.marineapi.nmea.util.Time
import net.sf.marineapi.nmea.util.Units
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * TTM sentence parser.
 *
 * @author Johan Bergkvist, Joshua Sweaney
 */
internal class TTMParser : SentenceParser, TTMSentence {
    /**
     * Create a new instance of TTMParser.
     *
     * @param nmea
     * TTM sentence String.
     * @throws IllegalArgumentException
     * If specified sentence is invalid.
     */
    constructor(nmea: String) : super(nmea, SentenceId.TTM)

    /**
     * Create a TTM parser with an empty sentence.
     *
     * @param talker
     * TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.TTM, 15)

    override fun getTime(): Time {
        val str = getStringValue(UTC_TIME)
        return Time(str)
    }

    override fun setTime(t: Time?) {
        /*
		 * The TTM specification calls for seconds with TWO decimals, not the
		 * usual three implemented by the Time.toString(). So we create our own
		 * string.
		 */
        var str = String.format("%02d%02d", t!!.getHour(), t.getMinutes())
        val nf = DecimalFormat("00.00")
        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'
        nf.decimalFormatSymbols = dfs
        str += nf.format(t.getSeconds())
        setStringValue(UTC_TIME, str)
    }

    override fun getNumber(): Int {
        return getIntValue(NUMBER)
    }

    override fun getDistance(): Double {
        return getDoubleValue(DISTANCE)
    }

    override fun getBearing(): Double {
        return getDoubleValue(BEARING)
    }

    override fun getBearingTrueRel(): Char {
        return getCharValue(BEARING_TRUE_REL)
    }

    override fun getSpeed(): Double {
        return getDoubleValue(SPEED)
    }

    override fun getCourse(): Double {
        return getDoubleValue(COURSE)
    }

    override fun getCourseTrueRel(): Char {
        return getCharValue(COURSE_TRUE_REL)
    }

    override fun getDistanceOfCPA(): Double {
        return getDoubleValue(DISTANCE_CPA)
    }

    override fun getTimeToCPA(): Double {
        return getDoubleValue(TIME_CPA)
    }

    override fun getUnits(): Units {
        return Units.valueOf(getCharValue(UNITS))
    }

    override fun getName(): String {
        return getStringValue(NAME)
    }

    override fun getStatus(): TargetStatus {
        return TargetStatus.valueOf(getCharValue(STATUS))
    }

    override fun getAcquisitionType(): AcquisitionType {
        return AcquisitionType.valueOf(getCharValue(ACQUISITON_TYPE))
    }

    override fun getReference(): Boolean {
        return getCharValue(REFERENCE) == 'R'
    }

    override fun setNumber(number: Int) {
        setIntValue(NUMBER, number, 2)
    }

    override fun setDistance(distance: Double) {
        setDoubleValue(DISTANCE, distance, 1, 1)
        setCharValue(UNITS, 'N')
    }

    override fun setTrueBearing(bearing: Double) {
        setDoubleValue(BEARING, bearing, 1, 1)
        setCharValue(BEARING_TRUE_REL, 'T')
    }

    override fun setRelativeBearing(bearing: Double) {
        setDoubleValue(BEARING, bearing, 1, 1)
        setCharValue(BEARING_TRUE_REL, 'R')
    }

    override fun isTrueBearing(): Boolean {
        return getCharValue(BEARING_TRUE_REL) == 'T'
    }

    override fun setSpeed(speed: Double) {
        setDoubleValue(SPEED, speed, 1, 1)
        setCharValue(UNITS, 'N')
    }

    override fun setTrueCourse(course: Double) {
        setDoubleValue(COURSE, course, 1, 1)
        setCharValue(COURSE_TRUE_REL, 'T')
    }

    override fun setRelativeCourse(course: Double) {
        setDoubleValue(COURSE, course, 1, 1)
        setCharValue(COURSE_TRUE_REL, 'R')
    }

    override fun isTrueCourse(): Boolean {
        return getCharValue(COURSE_TRUE_REL) == 'T'
    }

    override fun setDistanceOfCPA(distance: Double) {
        setDoubleValue(DISTANCE_CPA, distance, 1, 1)
        setCharValue(UNITS, 'N')
    }

    override fun setTimeToCPA(minutes: Double) {
        setDoubleValue(TIME_CPA, minutes, 1, 1)
    }

    /**
     * @see TTMSentence.setUnits
     */
    override fun setUnits(units: Units) {
        setCharValue(UNITS, units.toChar())
    }

    override fun setName(name: String?) {
        setStringValue(NAME, name)
    }

    override fun setStatus(status: TargetStatus) {
        setCharValue(STATUS, status.toChar())
    }

    override fun setReference(isReference: Boolean) {
        if (isReference) {
            setCharValue(REFERENCE, 'R')
        }
    }

    override fun setAcquisitionType(acquisitionType: AcquisitionType) {
        setCharValue(ACQUISITON_TYPE, acquisitionType.toChar())
    }

    companion object {
        private const val NUMBER = 0
        private const val DISTANCE = 1
        private const val BEARING = 2
        private const val BEARING_TRUE_REL = 3
        private const val SPEED = 4
        private const val COURSE = 5
        private const val COURSE_TRUE_REL = 6
        private const val DISTANCE_CPA = 7
        private const val TIME_CPA = 8
        private const val UNITS = 9
        private const val NAME = 10
        private const val STATUS = 11
        private const val REFERENCE = 12
        private const val UTC_TIME = 13
        private const val ACQUISITON_TYPE = 14
    }
}