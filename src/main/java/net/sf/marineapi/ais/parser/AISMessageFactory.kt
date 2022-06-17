/* 
 * AISMessageFactory.java
 * Copyright (C) 2015 Kimmo Tuukkanen
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
package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage
import net.sf.marineapi.ais.util.Sixbit
import net.sf.marineapi.nmea.sentence.AISSentence

/**
 * Factory for creating AIS message parsers.
 *
 * @author Kimmo Tuukkanen
 */
class AISMessageFactory private constructor() {
    private val parsers: MutableMap<Int, Class<out AISMessage>>

    /**
     * Hidden constructor.
     */
    init {
        parsers = HashMap(7)
        parsers[1] = AISMessage01Parser::class.java
        parsers[2] = AISMessage02Parser::class.java
        parsers[3] = AISMessage03Parser::class.java
        parsers[4] = AISMessage04Parser::class.java
        parsers[5] = AISMessage05Parser::class.java
        parsers[9] = AISMessage09Parser::class.java
        parsers[18] = AISMessage18Parser::class.java
        parsers[19] = AISMessage19Parser::class.java
        parsers[21] = AISMessage21Parser::class.java
        parsers[24] = AISMessage24Parser::class.java
        parsers[27] = AisMessage27Parser::class.java
    }

    /**
     * Creates a new AIS message parser based on given sentences.
     *
     * @param sentences One or more AIS sentences in correct sequence order.
     * @throws IllegalArgumentException If given message type is not supported
     * or sequence order is incorrect.
     * @throws IllegalStateException If message parser cannot be constructed
     * due to illegal state, e.g. invalid or empty message.
     * @return AISMessage instance
     */
    fun create(vararg sentences: AISSentence?): AISMessage {
        val parser = AISMessageParser(*sentences)
        if (!parsers.containsKey(parser.messageType)) {
            val msg = String.format("no parser for message type %d", parser.messageType)
            throw IllegalArgumentException(msg)
        }
        val result: AISMessage
        val c = parsers[parser.messageType]!!
        result = try {
            val co = c.getConstructor(Sixbit::class.java)
            co.newInstance(parser.sixbit)
        } catch (e: Exception) {
            throw IllegalStateException(e.cause)
        }
        return result
    }

    companion object {
        /**
         * Returns the factory singleton.
         *
         * @return AISMessageFactory
         */
        var instance: AISMessageFactory? = null
            get() {
                if (field == null) {
                    field = AISMessageFactory()
                }
                return field
            }
            private set
    }
}