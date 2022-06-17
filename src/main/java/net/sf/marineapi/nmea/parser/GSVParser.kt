/*
 * GSVParser.java
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

import net.sf.marineapi.nmea.sentence.GSVSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.SatelliteInfo

/**
 * GSV sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class GSVParser : SentenceParser, GSVSentence {
    /**
     * Constructor.
     *
     * @param nmea GSV Sentence
     */
    constructor(nmea: String) : super(nmea, SentenceId.GSV)

    /**
     * Creates an GSV parser with empty sentence.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.GSV, 19)

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#getSatelliteCount()
     */
    override fun getSatelliteCount(): Int {
        return getIntValue(SATELLITES_IN_VIEW)
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#getSatelliteInfo()
     */
    override fun getSatelliteInfo(): List<SatelliteInfo> {
        val satellites: MutableList<SatelliteInfo> = ArrayList(4)
        for (idf in ID_FIELDS) {
            try {
                val id = getStringValue(idf)
                val elev = getIntValue(idf + ELEVATION)
                val azm = getIntValue(idf + AZIMUTH)
                var snr = 0

//              In some case, the SNR field will be null
//              Example: $GLGSV,3,1,09,67,10,065,26,68,36,015,21,69,27,315,31,77,11,035,*6C
                try {
                    snr = getIntValue(idf + NOISE)
                } catch (e: Exception) {
                    //ignore
                }
                satellites.add(SatelliteInfo(id, elev, azm, snr))
            } catch (e: DataNotAvailableException) {
                // nevermind missing satellite info
            } catch (e: IndexOutOfBoundsException) {
                // less than four satellites, give up
                break
            }
        }
        return satellites
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#getSentenceCount()
     */
    override fun getSentenceCount(): Int {
        return getIntValue(NUMBER_OF_SENTENCES)
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#getSentenceIndex()
     */
    override fun getSentenceIndex(): Int {
        return getIntValue(SENTENCE_NUMBER)
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#isFirst()
     */
    override fun isFirst(): Boolean {
        return getSentenceIndex() == 1
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#isLast()
     */
    override fun isLast(): Boolean {
        return getSentenceIndex() == getSentenceCount()
    }

    override fun setSatelliteCount(count: Int) {
        require(count >= 0) { "Satellite count cannot be negative" }
        setIntValue(SATELLITES_IN_VIEW, count)
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.GSVSentence#setSatelliteInfo(java.util
     * .List)
     */
    override fun setSatelliteInfo(info: List<SatelliteInfo?>?) {
        require(info!!.size <= 4) { "Maximum list size is 4" }
        var i = 0
        for (id in ID_FIELDS) {
            if (i < info.size) {
                val si = info[i++]
                setStringValue(id, si!!.id)
                setIntValue(id + ELEVATION, si.getElevation())
                setIntValue(id + AZIMUTH, si.getAzimuth(), 3)
                setIntValue(id + NOISE, si.getNoise())
            } else {
                setStringValue(id, "")
                setStringValue(id + ELEVATION, "")
                setStringValue(id + AZIMUTH, "")
                setStringValue(id + NOISE, "")
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#setSentenceCount(int)
     */
    override fun setSentenceCount(count: Int) {
        require(count >= 1) { "Number of sentences cannot be negative" }
        setIntValue(NUMBER_OF_SENTENCES, count)
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#setSentenceIndex(int)
     */
    override fun setSentenceIndex(index: Int) {
        require(index >= 0) { "Sentence index cannot be negative" }
        setIntValue(SENTENCE_NUMBER, index)
    }

    companion object {
        // field indices
        private const val NUMBER_OF_SENTENCES = 0
        private const val SENTENCE_NUMBER = 1
        private const val SATELLITES_IN_VIEW = 2

        // satellite id fields
        private val ID_FIELDS = intArrayOf(3, 7, 11, 15)

        // satellite data fields, relative to each id field
        private const val ELEVATION = 1
        private const val AZIMUTH = 2
        private const val NOISE = 3
    }
}