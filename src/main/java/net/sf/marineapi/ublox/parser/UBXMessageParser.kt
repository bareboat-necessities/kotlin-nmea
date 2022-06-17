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
package net.sf.marineapi.ublox.parser

import net.sf.marineapi.nmea.parser.SentenceParser
import net.sf.marineapi.nmea.sentence.UBXSentence
import net.sf.marineapi.ublox.message.UBXMessage

/**
 * This is the base class for all UBX message parser implementations and contains
 * common logic. In a sense it wraps the given [UBXSentence]. The [UBXMessageParser]
 * extends [SentenceParser] to access the fields from the NMEA message (UBXSentence).
 *
 * @author Gunnar Hillert
 *
 * @see UBXMessage00Parser
 *
 * @see UBXMessage03Parser
 */
open class UBXMessageParser
/**
 * Construct a parser with the given [UBXSentence].
 *
 * @param sentence UBXSentence
 */(protected val sentence: UBXSentence) : SentenceParser(sentence.toString()), UBXMessage {
    /**
     * @see UBXMessage.getMessageType
     */
    override fun getMessageType(): Int {
        return sentence.messageId
    }
}