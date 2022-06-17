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

import net.sf.marineapi.nmea.sentence.BODSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId

/**
 * BOD sentence parser.
 *
 * @author Kimmo Tuukkanen
 * @see BODSentence
 */
internal class BODParser : SentenceParser, BODSentence {
    /**
     * Creates a new instance of BOD parser.
     *
     * @param nmea BOD sentence String
     * @throws IllegalArgumentException If specified String is invalid or does
     * not contain a BOD sentence.
     */
    constructor(nmea: String) : super(nmea, SentenceId.BOD) {}

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
	 */
    override fun getDestinationWaypointId(): String {
        return getStringValue(DESTINATION)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getMagneticBearing()
	 */
    override fun getMagneticBearing(): Double {
        return getDoubleValue(BEARING_MAGN)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getOriginWaypointId()
	 */
    override fun getOriginWaypointId(): String {
        return getStringValue(ORIGIN)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getTrueBearing()
	 */
    override fun getTrueBearing(): Double {
        return getDoubleValue(BEARING_TRUE)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#setDestinationWaypointId(java
	 * .lang.String)
	 */
    override fun setDestinationWaypointId(id: String) {
        setStringValue(DESTINATION, id)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#setMagneticBearing(double)
	 */
    override fun setMagneticBearing(bearing: Double) {
        setDegreesValue(BEARING_MAGN, bearing)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#setOriginWaypointId(java.lang
	 * .String)
	 */
    override fun setOriginWaypointId(id: String) {
        setStringValue(ORIGIN, id)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#setTrueBearing(double)
	 */
    override fun setTrueBearing(bearing: Double) {
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