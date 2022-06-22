/*
 * TXTSentence.java
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
package net.sf.marineapi.nmea.sentence

/**
 *
 *
 * Text message sentence. Transmits various information on the device, such as
 * power-up screen, software version etc.
 *
 *
 * Example:<br></br>
 * `$GPTXT,01,01,TARG1,Message*35`
 *
 *
 * @author Kimmo Tuukkanen
 */
interface TXTSentence : Sentence {
    /**
     * Get total number of sentences in message sequence.
     *
     * @return Number of transmission messages.
     */
    fun getMessageCount(): Int

    /**
     * Set total number of sentences in message sequence.
     *
     * @param count Number of transmission messages.
     * @throws IllegalArgumentException If given count is zero or negative
     */
    fun setMessageCount(count: Int)

    /**
     * Returns the sentence index in message sequence.
     *
     * @return Message number of this sentence.
     * @see .getMessageCount
     */
    fun getMessageIndex(): Int

    /**
     * Sets the sentence index in message sequence.
     *
     * @param index Message index to set
     * @throws IllegalArgumentException If given index is negative
     */
    fun setMessageIndex(index: Int)

    /**
     * Returns the message identifier. This field may be used for various
     * purposes depending on the device. Originally a numeric field but may
     * also contain String values. For example, should match target name in
     * `TLL` or waypoint name in `WPL`.
     *
     * @return Message identifier
     */
    fun getIdentifier(): String?

    /**
     * Sets the message identifier.
     *
     * @param id Identifier to be set
     * @see .getIdentifier
     */
    fun setIdentifier(id: String?)

    /**
     * Returns the message content.
     *
     * @return ASCII text content
     */
    fun getMessage(): String?

    /**
     * Sets the message content.
     *
     * @param msg ASCII text content to set.
     */
    fun setMessage(msg: String?)
}