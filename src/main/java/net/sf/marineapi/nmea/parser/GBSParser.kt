/*
 * GBSParser.java
 * Copyright (C) 2018 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.GBSSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.Time

/**
 * GBS sentence parser.
 *
 * $GPGBS,015509.00,-0.031,-0.186,0.219,19,0.000,-0.354,6.972*4D
 *
 * @author Kimmo Tuukkanen
 */
internal class GBSParser : SentenceParser, GBSSentence {
    /**
     * Creates an empty GBS parser.
     *
     * @param tid Talker ID to set.
     */
    constructor(tid: TalkerId?) : super(tid, SentenceId.GBS, 8) {}

    /**
     * Creates a parser for given GBS sentence.
     *
     * @param nmea GBS sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.GBS) {}

    override fun getLatitudeError(): Double {
        return getDoubleValue(LAT_ERROR)
    }

    override fun setLatitudeError(err: Double) {
        setDoubleValue(LAT_ERROR, err)
    }

    override fun getLongitudeError(): Double {
        return getDoubleValue(LON_ERROR)
    }

    override fun setLongitudeError(err: Double) {
        setDoubleValue(LON_ERROR, err)
    }

    override fun getAltitudeError(): Double {
        return getDoubleValue(ALT_ERROR)
    }

    override fun setAltitudeError(err: Double) {
        setDoubleValue(ALT_ERROR, err)
    }

    override fun getSatelliteId(): String {
        return getStringValue(SATELLITE_ID)
    }

    override fun setSatelliteId(id: String?) {
        setStringValue(SATELLITE_ID, id)
    }

    override fun getProbability(): Double {
        return getDoubleValue(PROBABILITY)
    }

    override fun setProbability(probability: Double) {
        setDoubleValue(PROBABILITY, probability)
    }

    override fun getEstimate(): Double {
        return getDoubleValue(ESTIMATE)
    }

    override fun setEstimate(estimate: Double) {
        setDoubleValue(ESTIMATE, estimate)
    }

    override fun getDeviation(): Double {
        return getDoubleValue(DEVIATION)
    }

    override fun setDeviation(deviation: Double) {
        setDoubleValue(DEVIATION, deviation)
    }

    override fun getTime(): Time {
        return Time(getStringValue(UTC))
    }

    override fun setTime(t: Time?) {
        setStringValue(UTC, t.toString())
    }

    companion object {
        private const val UTC = 0
        private const val LAT_ERROR = 1
        private const val LON_ERROR = 2
        private const val ALT_ERROR = 3
        private const val SATELLITE_ID = 4
        private const val PROBABILITY = 5
        private const val ESTIMATE = 6
        private const val DEVIATION = 7
    }
}