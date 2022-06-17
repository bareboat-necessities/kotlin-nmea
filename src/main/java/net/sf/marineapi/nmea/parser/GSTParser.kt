/*
 * GSTParser.java
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

import net.sf.marineapi.nmea.sentence.GSTSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.Time

/**
 * GST sentence parser.
 *
 * @author Tero Laitinen
 */
internal class GSTParser : SentenceParser, GSTSentence {
    /**
     * Creates a new instance of GST parser.
     *
     * @param nmea GST sentence String.
     * @throws IllegalArgumentException If the specified sentence is invalid or
     * not a GST sentence.
     */
    constructor(nmea: String) : super(nmea, SentenceId.GST) {}

    /**
     * Creates GSA parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.GST, 8) {}

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.TimeSentence#getTime()
	 */
    override fun getTime(): Time {
        val str = getStringValue(UTC_TIME)
        return Time(str)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getPseudoRangeResidualsRMS
	 */
    override fun getPseudoRangeResidualsRMS(): Double {
        return getDoubleValue(PSEUDORANGE_RESIDUALS_RMS)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getSemiMajorError
	 */
    override fun getSemiMajorError(): Double {
        return getDoubleValue(ERROR_ELLIPSE_SEMI_MAJOR)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getSemiMinorError
	 */
    override fun getSemiMinorError(): Double {
        return getDoubleValue(ERROR_ELLIPSE_SEMI_MINOR)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getErrorEllipseOrientation
	 */
    override fun getErrorEllipseOrientation(): Double {
        return getDoubleValue(ERROR_ELLIPSE_ORIENTATION)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getLatitudeError
	 */
    override fun getLatitudeError(): Double {
        return getDoubleValue(LATITUDE_ERROR)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getLongitudeError
	 */
    override fun getLongitudeError(): Double {
        return getDoubleValue(LONGITUDE_ERROR)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getAltitudeError
	 */
    override fun getAltitudeError(): Double {
        return getDoubleValue(ALTITUDE_ERROR)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.TimeSentence#setTime(net.sf.marineapi.nmea.util.Time)
	 */
    override fun setTime(t: Time) {
        setStringValue(UTC_TIME, t.toString())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setPseudoRangeResidualsRMS
	 */
    override fun setPseudoRangeResidualsRMS(rms: Double) {
        setDoubleValue(PSEUDORANGE_RESIDUALS_RMS, rms)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setSemiMajorError
	 */
    override fun setSemiMajorError(error: Double) {
        setDoubleValue(ERROR_ELLIPSE_SEMI_MAJOR, error)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setSemiMinorError
	 */
    override fun setSemiMinorError(error: Double) {
        setDoubleValue(ERROR_ELLIPSE_SEMI_MINOR, error)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setErrorEllipseOrientation
	 */
    override fun setErrorEllipseOrientation(orientation: Double) {
        setDoubleValue(ERROR_ELLIPSE_ORIENTATION, orientation)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setLatitudeError
	 */
    override fun setLatitudeError(error: Double) {
        setDoubleValue(LATITUDE_ERROR, error)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setLongitudeError
	 */
    override fun setLongitudeError(error: Double) {
        setDoubleValue(LONGITUDE_ERROR, error)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setAltitudeError
	 */
    override fun setAltitudeError(error: Double) {
        setDoubleValue(ALTITUDE_ERROR, error)
    }

    companion object {
        // GST field indices
        private const val UTC_TIME = 0
        private const val PSEUDORANGE_RESIDUALS_RMS = 1
        private const val ERROR_ELLIPSE_SEMI_MAJOR = 2
        private const val ERROR_ELLIPSE_SEMI_MINOR = 3
        private const val ERROR_ELLIPSE_ORIENTATION = 4
        private const val LATITUDE_ERROR = 5
        private const val LONGITUDE_ERROR = 6
        private const val ALTITUDE_ERROR = 7
    }
}