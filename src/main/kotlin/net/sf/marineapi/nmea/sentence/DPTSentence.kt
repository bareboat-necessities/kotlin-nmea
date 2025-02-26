/* 
 * DPTSentence.java
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
package net.sf.marineapi.nmea.sentence

/**
 *
 * Depth of water, measured in meters. Includes offset to transducer, positive
 * values for distance from transducer to water line and negative values for
 * distance from transducer to keel. The maximum value is included since NMEA
 * v3.0 and may thus be missing.
 *
 *
 * Example:<br></br>`$SDDPT,2.4,,*7F`
 *
 * @author Kimmo Tuukkanen
 */
interface DPTSentence : DepthSentence {
    /**
     * Get offset to transducer.
     *
     * @return Offset in meters.
     */
    fun getOffset(): Double

    /**
     * Set offset to transducer.
     *
     * @param offset Offset in meters
     */
    fun setOffset(offset: Double)

    /**
     * Get maximum depth value the sounder can detect.
     *
     * @return Maximum depth, in meters.
     */
    fun getMaximum(): Double

    /**
     * Set maximum depth value the sounder can detect.
     *
     * @param max Maximum depth, in meters.
     */
    fun setMaximum(max: Double)
}