/* 
 * DBTParser.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.DBTSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.Units
/**
 * DBT sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class DBTParser : SentenceParser, DBTSentence {
    /**
     * Creates a new instance of DBTParser.
     *
     * @param nmea DBT sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.DBT) {}

    /**
     * Creates a new instance of DBTParser with empty data fields.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.DBT, 6) {
        setCharValue(FEET, Units.FEET.toChar())
        setCharValue(METERS, Units.METER.toChar())
        setCharValue(FATHOMS, Units.FATHOMS.toChar())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DBTSentence#getMeters()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DBTSentence#setMeters(double)
	 */
    override var depth: Double
        get() = getDoubleValue(DEPTH_METERS)
        set(depth) {
            setDoubleValue(DEPTH_METERS, depth, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DBTSentence#getFathoms()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DBTSentence#setFathoms(double)
	 */
    override var fathoms: Double
        get() = getDoubleValue(DEPTH_FATHOMS)
        set(depth) {
            setDoubleValue(DEPTH_FATHOMS, depth, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DBTSentence#getFeet()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DBTSentence#setFeet(double)
	 */
    override var feet: Double
        get() = getDoubleValue(DEPTH_FEET)
        set(depth) {
            setDoubleValue(DEPTH_FEET, depth, 1, 1)
        }

    companion object {
        // TODO calculate value for all units when setting a value
        private const val DEPTH_FEET = 0
        private const val FEET = 1
        private const val DEPTH_METERS = 2
        private const val METERS = 3
        private const val DEPTH_FATHOMS = 4
        private const val FATHOMS = 5
    }
}