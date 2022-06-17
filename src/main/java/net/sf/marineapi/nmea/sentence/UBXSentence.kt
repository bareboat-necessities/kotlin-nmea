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
package net.sf.marineapi.nmea.sentence

import net.sf.marineapi.nmea.parser.SentenceParser
import net.sf.marineapi.ublox.message.UBXMessage
import net.sf.marineapi.ublox.message.UBXMessage00
import net.sf.marineapi.ublox.message.UBXMessage03
import net.sf.marineapi.ublox.parser.UBXMessageFactory
import net.sf.marineapi.ublox.parser.UBXMessageParser

/**
 * Base interface (Outer layer) for all UBX [Sentence]s. UBX sentences are
 * parsed in two phases and they share the same NMEA sentence layout.
 *
 *
 * In the second phase the actual message contents is retrieved. Users should use
 * a [UBXMessageParser] implementation to retrieve specific [UBXMessage]s.
 *
 * The following [UBXMessageParser]s
 * are available:
 *
 *
 *  *  [UBXMessage00]
 *  *  [UBXMessage03]
 *
 *
 *
 * UBX messages are proprietary NMEA messages (Vendor extensions) for u-blox
 * positioning receivers. They are also often referred to as `PUBX00`,
 * `PUBX03` etc. `Pxxx` identifies this message as proprietary
 * followed by `UBX`. To be consistent, the Java Marine API uses the term
 * `UBX`.
 *
 *
 * Important: u-blox also support a binary messaging format called UBX which is not
 * supported by the Java Marine API.
 *
 * @author Gunnar Hillert
 *
 * @see UBXMessageParser
 *
 * @see UBXMessageFactory
 */
interface UBXSentence : Sentence {
    /**
     * @return The numeric u-blox proprietary message identifier
     */
    fun getMessageId(): Int

    /**
     * Parse integer value from the specified sentence field.
     *
     * @param index Field index in sentence
     * @return Field parsed by [SentenceParser]
     */
    fun getUBXFieldIntValue(index: Int): Int

    /**
     * Parse [String] value from the specified sentence field.
     *
     * @param index Field index in sentence
     * @return Field parsed by [SentenceParser]
     */
    fun getUBXFieldStringValue(index: Int): String?

    /**
     * Parse char value from the specified sentence field.
     *
     * @param index Field index in sentence
     * @return Field parsed by [SentenceParser]
     */
    fun getUBXFieldCharValue(index: Int): Char

    /**
     * Parse double value from the specified sentence field.
     *
     * @param index Field index in sentence
     * @return Field parsed by [SentenceParser]
     */
    fun getUBXFieldDoubleValue(index: Int): Double

    /**
     * @return the number of data fields in the sentence, excluding ID field
     * and checksum.
     *
     * @see SentenceParser
     */
    fun getUBXFieldCount(): Int
}