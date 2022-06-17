/*
 * Copyright (C) 2020 Gunnar Hillert
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

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.UBXSentence
import net.sf.marineapi.ublox.parser.UBXMessageParser

/**
 * Common UBX sentence parser. These messages are often referred to as PUBX messages,
 * consisting of [TalkerId.P] + [SentenceId.UBX].
 *
 * This parser only handles the NMEA layer. The actual payload message is parsed
 * by the [UBXMessageParser] and its sub-classes.
 *
 * @author Gunnar Hillert
 *
 * @see UBXSentence
 *
 * @see UBXMessageParser
 */
internal class UBXParser : SentenceParser, UBXSentence {
    constructor(nmea: String) : super(nmea, SentenceId.UBX) {}

    /**
     * Creates a new empty UBX Parser.
     *
     * @param talker TalkerId to set
     */
    constructor(talker: TalkerId?) : super(talker, SentenceId.UBX, 6) {}
    constructor(nmea: String, type: String?) : super(nmea, type) {}

    override fun getMessageId(): Int {
        return super.getIntValue(0)
    }

    override fun getUBXFieldIntValue(index: Int): Int {
        return super.getIntValue(index)
    }

    override fun getUBXFieldStringValue(index: Int): String? {
        return super.getStringValue(index)
    }

    override fun getUBXFieldCharValue(index: Int): Char {
        return super.getCharValue(index)
    }

    override fun getUBXFieldDoubleValue(index: Int): Double {
        return super.getDoubleValue(index)
    }

    override fun getUBXFieldCount(): Int {
        return super.getFieldCount()
    }
}