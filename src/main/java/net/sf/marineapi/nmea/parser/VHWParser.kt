/* 
 * VHWParser.java
 * Copyright (C) 2011 Warren Zahra
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
import net.sf.marineapi.nmea.sentence.VHWSentence

/**
 * WHV sentence parser.
 *
 * @author Warren Zahra, Kimmo Tuukkanen
 */
internal class VHWParser : SentenceParser, VHWSentence {
    /**
     * Creates a new instance of VHW parser with given data.
     *
     * @param nmea VHW sentence String
     */
    constructor(nmea: String) : super(nmea)

    /**
     * Creates a new empty VHW parser instance.
     *
     * @param talker Talker ID to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.VHW, 8) {
        setCharValue(TRUE_INDICATOR, 'T')
        setCharValue(MAGNETIC_INDICATOR, 'M')
        setCharValue(KNOTS_INDICATOR, 'N')
        setCharValue(KMH_INDICATOR, 'K')
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HeadingSentence#getHeading()
	 */
    override fun getHeading(): Double {
        return getDoubleValue(TRUE_HEADING)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.VHWSentence#getMagneticHeading()
	 */
    override fun getMagneticHeading(): Double {
        return getDoubleValue(MAGNETIC_HEADING)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.VHWSentence#getSpeedKilometres()
	 */
    override fun getSpeedKmh(): Double {
        return getDoubleValue(SPEED_KMH)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.VHWSentence#getSpeedKnots()
	 */
    override fun getSpeedKnots(): Double {
        return getDoubleValue(SPEED_KNOTS)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HeadingSentence#isTrue()
	 */
    override fun isTrue(): Boolean {
        return true
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.HeadingSentence#setHeading(double)
	 */
    override fun setHeading(hdg: Double) {
        setDegreesValue(TRUE_HEADING, hdg)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.VHWSentence#setMagneticHeading(double)
	 */
    override fun setMagneticHeading(hdg: Double) {
        setDegreesValue(MAGNETIC_HEADING, hdg)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.VHWSentence#setSpeedKmh(double)
	 */
    override fun setSpeedKmh(kmh: Double) {
        setDoubleValue(SPEED_KMH, kmh, 1, 1)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.VHWSentence#setSpeedKnots(double)
	 */
    override fun setSpeedKnots(knots: Double) {
        setDoubleValue(SPEED_KNOTS, knots, 1, 1)
    }

    companion object {
        private const val TRUE_HEADING = 0
        private const val TRUE_INDICATOR = 1
        private const val MAGNETIC_HEADING = 2
        private const val MAGNETIC_INDICATOR = 3
        private const val SPEED_KNOTS = 4
        private const val KNOTS_INDICATOR = 5
        private const val SPEED_KMH = 6
        private const val KMH_INDICATOR = 7
    }
}