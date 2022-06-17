/* 
 * GSAParser.java
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

import net.sf.marineapi.nmea.sentence.GSASentenceimport

net.sf.marineapi.nmea.sentence.SentenceIdimport net.sf.marineapi.nmea.sentence.TalkerIdimport net.sf.marineapi.nmea.util.FaaModeimport net.sf.marineapi.nmea.util.GpsFixStatus
/**
 * GSA sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class GSAParser : SentenceParser, GSASentence {
    /**
     * Creates a new instance of GSA parser.
     *
     * @param nmea GSA sentence String
     * @throws IllegalArgumentException If specified sentence is invalid.
     */
    constructor(nmea: String) : super(nmea, SentenceId.GSA)

    /**
     * Creates GSA parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.GSA, 17)

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSASentence#getFixStatus()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GSASentence#setFixStatus(net.sf.marineapi
	 * .nmea.util.GpsFixStatus)
	 */
    override var fixStatus: GpsFixStatus
        get() = GpsFixStatus.Companion.valueOf(getIntValue(FIX_MODE))
        set(status) {
            setIntValue(FIX_MODE, status.toInt())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSASentence#getHorizontalDOP()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GSASentence#setHorizontalPrecision(double)
	 */
    override var horizontalDOP: Double
        get() = getDoubleValue(HORIZONTAL_DOP)
        set(hdop) {
            setDoubleValue(HORIZONTAL_DOP, hdop, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSASentence#getMode()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GSASentence#setFaaMode(net.sf.marineapi
	 * .nmea.util.FaaMode)
	 */
    override var mode: FaaMode
        get() = FaaMode.Companion.valueOf(getCharValue(GPS_MODE))
        set(mode) {
            setCharValue(GPS_MODE, mode.toChar())
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSASentence#getPositionDOP()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSASentence#setPositionDOP(double)
	 */
    override var positionDOP: Double
        get() = getDoubleValue(POSITION_DOP)
        set(pdop) {
            setDoubleValue(POSITION_DOP, pdop, 1, 1)
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSASentence#getSatelliteIds()
	 *//*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GSASentence#setSatelliteIds(java.lang.
	 * String[])
	 */
    override var satelliteIds: Array<String?>
        get() {
            val result: MutableList<String?> = ArrayList()
            for (i in FIRST_SV..LAST_SV) {
                if (hasValue(i)) {
                    result.add(getStringValue(i))
                }
            }
            return result.toTypedArray()
        }
        set(ids) {
            require(!(ids.size > LAST_SV - FIRST_SV + 1)) { "List length exceeded (12)" }
            var j = 0
            for (i in FIRST_SV..LAST_SV) {
                val id = if (j < ids.size) ids[j++] else ""
                setStringValue(i, id)
            }
        }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSASentence#getVerticalDOP()
	 *//*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSASentence#setVerticalDOP(double)
	 */
    override var verticalDOP: Double
        get() = getDoubleValue(VERTICAL_DOP)
        set(vdop) {
            setDoubleValue(VERTICAL_DOP, vdop, 1, 1)
        }

    companion object {
        // field indices
        private const val GPS_MODE = 0
        private const val FIX_MODE = 1
        private const val FIRST_SV = 2
        private const val LAST_SV = 13
        private const val POSITION_DOP = 14
        private const val HORIZONTAL_DOP = 15
        private const val VERTICAL_DOP = 16
    }
}