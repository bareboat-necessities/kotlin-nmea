/* 
 * DTBParser.java
 * Copyright (C) 2019 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.DTBSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId

/**
 * DTB sentence parser.
 *
 * @author Bob Schwarz
 * @see [marine-api fork](https://github.com/LoadBalanced/marine-api)
 */
internal class DTBParser : DTAParser, DTBSentence {
    /**
     * Creates a new instance of DTBParser with 8 data fields.
     *
     * @param talker DTB talkerId
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.DTB, 8)

    /**
     * Creates a new instance of DTBParser.
     *
     * @param nmea DTB sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.DTB)

    /**
     * Gets the hard-coded channel for GasFinder2. Since only GasFinder2
     * will send a DTB sentence, and since only GasFinderMC has channels,
     * there will normally be no channel in DTB sentences.
     *
     * @return Channel number, always 2.
     */
    override fun getChannelNumber(): Int {
        return 2
    }

    companion object {
        const val DTB_SENTENCE_ID = "DTB"
    }
}