/* 
 * BODParser.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.BODSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerId
/**
 * BOD sentence parser.
 *
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.nmea.sentence.BODSentence
 */
internal class BODParser : SentenceParser, BODSentence {
    /**
     * Creates a new instance of BOD parser.
     *
     * @param nmea BOD sentence String
     * @throws IllegalArgumentException If specified String is invalid or does
     * not contain a BOD sentence.
     */
    constructor(nmea: String) : super(nmea, SentenceId.BOD)

    /**
     * Creates GSA parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.BOD, 6) {
        setCharValue(TRUE_INDICATOR, 'T')
        setCharValue(MAGN_INDICATOR, 'M')
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#getDestinationWaypointId()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#setDestinationWaypointId(java
	 * .lang.String)
	 */
    override var destinationWaypointId: String?
        get() = getStringValue(DESTINATION)
        set(id) {
            setStringValue(DESTINATION, id)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getMagneticBearing()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#setMagneticBearing(double)
	 */
    override var magneticBearing: Double
        get() = getDoubleValue(BEARING_MAGN)
        set(bearing) {
            setDegreesValue(BEARING_MAGN, bearing)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getOriginWaypointId()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#setOriginWaypointId(java.lang
	 * .String)
	 */
    override var originWaypointId: String?
        get() = getStringValue(ORIGIN)
        set(id) {
            setStringValue(ORIGIN, id)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getTrueBearing()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#setTrueBearing(double)
	 */
    override var trueBearing: Double
        get() = getDoubleValue(BEARING_TRUE)
        set(bearing) {
            setDegreesValue(BEARING_TRUE, bearing)
        }

    companion object {
        // field indices
        private const val BEARING_TRUE = 0
        private const val TRUE_INDICATOR = 1
        private const val BEARING_MAGN = 2
        private const val MAGN_INDICATOR = 3
        private const val DESTINATION = 4
        private const val ORIGIN = 5
    }
}