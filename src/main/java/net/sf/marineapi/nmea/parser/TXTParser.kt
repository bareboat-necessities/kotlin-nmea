/*
 * TXTParser.java
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

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TXTSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import java.util.regex.Pattern

/**
 * TXT sentence parser.
 *
 * $GPTXT,01,01,TARG1,Message*35
 *
 * TXT - TXT protocol header
 * 01 - Total number of messages in this transmission
 * 01 - Message number in this transmission
 * TARG1 - Target name associated with this message
 * Message - Text of message for Terrain Navigator Pro to display.
 *
 * @author Kimmo Tuukkanen
 */
internal class TXTParser : SentenceParser, TXTSentence {
    /**
     * Constructor with sentence String.
     *
     * @param nmea TXT sentence String
     */
    constructor(nmea: String) : super(nmea, SentenceId.TXT) {}

    /**
     * Constructs an empty TXT sentence.
     *
     * @param tid TalkerId to set
     */
    constructor(tid: TalkerId?) : super(tid, SentenceId.TXT, 4) {}

    override fun getMessageCount(): Int {
        return getIntValue(MESSAGE_COUNT)
    }

    override fun setMessageCount(count: Int) {
        require(count >= 1) { "Message count cannot be zero or negative" }
        setIntValue(MESSAGE_COUNT, count)
    }

    override fun getMessageIndex(): Int {
        return getIntValue(MESSAGE_INDEX)
    }

    override fun setMessageIndex(index: Int) {
        require(index >= 0) { "Message index cannot be negative" }
        setIntValue(MESSAGE_INDEX, index)
    }

    override fun getIdentifier(): String {
        return getStringValue(IDENTIFIER)
    }

    override fun setIdentifier(id: String?) {
        setStringValue(IDENTIFIER, id)
    }

    override fun getMessage(): String {
        return getStringValue(MESSAGE)
    }

    override fun setMessage(msg: String?) {
        require(ASCII.matcher(msg.toString()).matches()) { "Message must be in ASCII character set" }
        setStringValue(MESSAGE, msg)
    }

    companion object {
        private val ASCII = Pattern.compile("^[\\x20-\\x7F]*$")
        private const val MESSAGE_COUNT = 0
        private const val MESSAGE_INDEX = 1
        private const val IDENTIFIER = 2
        private const val MESSAGE = 3
    }
}