/* 
 * DPTParser.java
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

import net.sf.marineapi.nmea.sentence.DPTSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId

/**
 * DPT sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
internal class DPTParser : SentenceParser, DPTSentence {
    /**
     * Creates a new instance of DPTParser.
     *
     * @param nmea DPT sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.DPT)

    /**
     * Creates a new instance of DPTParser with empty data fields.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.DPT, 3)

    override fun getDepth(): Double {
        return getDoubleValue(DEPTH)
    }

    override fun getOffset(): Double {
        return getDoubleValue(OFFSET)
    }

    override fun setDepth(depth: Double) {
        setDoubleValue(DEPTH, depth, 1, 1)
    }

    override fun setOffset(offset: Double) {
        setDoubleValue(OFFSET, offset, 1, 1)
    }

    override fun getMaximum(): Double {
        return getDoubleValue(MAXIMUM)
    }

    override fun setMaximum(max: Double) {
        setDoubleValue(MAXIMUM, max)
    }

    companion object {
        private const val DEPTH = 0
        private const val OFFSET = 1
        private const val MAXIMUM = 2
    }
}