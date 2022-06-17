/* 
 * GSVSentence.java
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
package net.sf.marineapi.nmea.sentence

import net.sf.marineapi.nmea.util.SatelliteInfo


/**
 * Detailed GPS satellite data; satellites in view, satellite elevation, azimuth
 * and signal noise ratio (SNR). GSV sentences are transmitted typically in
 * groups of two or three sentences, depending on the number of satellites in
 * view. Each GSV sentence may contain information about up to four satellites.
 * The last sentence in sequence may contain empty satellite information fields.
 * The empty fields may also be omitted, depending on the device model and
 * manufacturer.
 *
 *
 * Example: <br></br>
 * `$GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,63,058,50,21,53,329,47*73`
 *
 * @author Kimmo Tuukkanen
 */
interface GSVSentence : Sentence {
    /**
     * Get the number of satellites in view.
     *
     * @return Satellite count
     */
    fun getSatelliteCount(): Int

    /**
     * Get the satellites information.
     *
     * @return List of SatelliteInfo objects.
     */
    fun getSatelliteInfo(): List<SatelliteInfo?>?

    /**
     * Get the total number of sentences in GSV sequence.
     *
     * @return Number of sentences
     */
    fun getSentenceCount(): Int

    /**
     * Get the index of this sentence in GSV sequence.
     *
     * @return Sentence index
     */
    fun getSentenceIndex(): Int

    /**
     * Tells if this is the first sentence in GSV sequence.
     *
     * @return true if first, otherwise false.
     * @see .getSentenceCount
     * @see .getSentenceIndex
     */
    fun isFirst(): Boolean

    /**
     * Tells if this is the last sentence in GSV sequence. This is a convenience
     * method for comparison of
     * `({ #getSentenceCount()} == { #getSentenceIndex()})`
     * .
     *
     * @return `true` if first, otherwise `false`.
     */
    fun isLast(): Boolean

    /**
     * Set the number of satellites in view.
     *
     * @param count Satellite count
     * @throws IllegalArgumentException If specified number is negative
     */
    fun setSatelliteCount(count: Int)

    /**
     * Set the satellite information.
     *
     * @param info List of SatelliteInfo objects, size from 0 to 4.
     * @throws IllegalArgumentException If specified list size is greater that
     * maximum allowed number of satellites per sentence (4).
     */
    fun setSatelliteInfo(info: List<SatelliteInfo?>?)

    /**
     * Set the total number of sentences in GSV sequence.
     *
     * @param count Number of sentences
     * @throws IllegalArgumentException If specified count is negative
     */
    fun setSentenceCount(count: Int)

    /**
     * Set the index of this sentence in GSV sequence.
     *
     * @param index Sentence index to set
     * @throws IllegalArgumentException If specified index is negative
     */
    fun setSentenceIndex(index: Int)
}