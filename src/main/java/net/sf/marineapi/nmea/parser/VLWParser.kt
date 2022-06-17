/*
 * VLWParser.java
 * Copyright (C) 2014 ktuu
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

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.VLWSentence

/**
 * VLW sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class VLWParser : SentenceParser, VLWSentence {
    /**
     * Creates a new instance of VLWParser.
     *
     * @param nmea NMEA sentence STring.
     */
    constructor(nmea: String) : super(nmea)

    /**
     * Creates a new empty instance of VLWParser.
     *
     * @param talker TalkerId to set.
     */
    constructor(talker: TalkerId?) : super(talker, "VLW", 4)

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VLWSentence#getTotal()
	 */
    override fun getTotal(): Double {
        return getDoubleValue(TOTAL)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VLWSentence#getTotalUnits()
	 */
    override fun getTotalUnits(): Char {
        return getCharValue(TOTAL_UNITS)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VLWSentence#getTrip()
	 */
    override fun getTrip(): Double {
        return getDoubleValue(TRIP)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VLWSentence#getTripUnits()
	 */
    override fun getTripUnits(): Char {
        return getCharValue(TRIP_UNITS)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VLWSentence#setTotal(double)
	 */
    override fun setTotal(distance: Double) {
        setDoubleValue(TOTAL, distance, 1, 1)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VLWSentence#setTotalUnits(char)
	 */
    override fun setTotalUnits(unit: Char) {
        setUnit(TOTAL_UNITS, unit)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VLWSentence#setTrip(double)
	 */
    override fun setTrip(distance: Double) {
        setDoubleValue(TRIP, distance, 1, 1)
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VLWSentence#setTripUnits(char)
	 */
    override fun setTripUnits(unit: Char) {
        setUnit(TRIP_UNITS, unit)
    }

    /**
     * Set and validate unit char.
     *
     * @param index Field index
     * @param unit Unit char
     */
    private fun setUnit(index: Int, unit: Char) {
        require(!(unit != VLWSentence.KM && unit != VLWSentence.NM)) { "Invalid distance unit, expected 'N' or 'K'" }
        setCharValue(index, unit)
    }

    companion object {
        private const val TOTAL = 0
        private const val TOTAL_UNITS = 1
        private const val TRIP = 2
        private const val TRIP_UNITS = 3
    }
}