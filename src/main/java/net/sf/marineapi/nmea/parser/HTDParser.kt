/*
 * HTDParser.java
 * Copyright (C) 2015 Paweł Kozioł, Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.HTDSentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.DataStatus
/**
 * HTD parser.
 *
 * @author Paweł Kozioł
 */
internal class HTDParser : HTCParser, HTDSentence {
    /**
     * Constructor.
     *
     * @param nmea HTD sentence String to parse.
     */
    constructor(nmea: String) : super(nmea, SentenceId.HTD) {}

    /**
     * Constructor for empty HTD sentence.
     *
     * @param talker Talker ID to set.
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.HTD, 17) {}

    override val rudderStatus: DataStatus?
        get() = if (hasValue(RUDDER_STATUS)) {
            DataStatus.Companion.valueOf(getCharValue(RUDDER_STATUS))
        } else {
            null
        }
    override val offHeadingStatus: DataStatus?
        get() = if (hasValue(OFF_HEADING_STATUS)) {
            DataStatus.Companion.valueOf(getCharValue(OFF_HEADING_STATUS))
        } else {
            null
        }
    override val offTrackStatus: DataStatus?
        get() = if (hasValue(OFF_TRACK_STATUS)) {
            DataStatus.Companion.valueOf(getCharValue(OFF_TRACK_STATUS))
        } else {
            null
        }
    override var heading: Double
        get() = if (hasValue(HEADING)) {
            getDoubleValue(HEADING)
        } else {
            Double.NaN
        }
        set(hdt) {
            setDoubleValue(HEADING, hdt)
        }
    override val isTrue: Boolean
        get() = isHeadingTrue

    companion object {
        private const val RUDDER_STATUS = 13
        private const val OFF_HEADING_STATUS = 14
        private const val OFF_TRACK_STATUS = 15
        private const val HEADING = 16
    }
}