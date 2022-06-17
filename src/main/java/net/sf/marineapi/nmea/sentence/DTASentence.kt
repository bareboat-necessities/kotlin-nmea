/* 
 * DTASentence.java
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
package net.sf.marineapi.nmea.sentence

import java.util.*

/**
 *
 * Boreal GasFinder data.
 *
 *
 * Example from GasFinder2:<br></br>
 * `$GFDTA,7.7,98,600,5527,2011/01/27 13:29:28,HFH2O-1xxx,1*2B`
 *
 *
 * Example from GasFinderMC with additional channel number:<br></br>
 * `$GFDTA,1,1.5,99,600,11067,2002/03/01 00:30:28,HF-1xxx,1*3C`
 *
 * @author Bob Schwarz
 * @see DTBSentence
 */
interface DTASentence : Sentence {
    /**
     * Gets the optional channel number (GasFinderMC only).
     * @return Channel number 1 - 8.
     */
    val channelNumber: Int

    /**
     * Gets the max eight-digit Gas Concentration.
     * @return Gas Concentration in ppmm (parts per million meter)
     */
    val gasConcentration: Double

    /**
     * Gets the two-digit Confidence Factor.
     * @return Confidence Factor as number between 0 - 99.
     */
    val confidenceFactorR2: Int

    /**
     * Gets the max four-digit distance to retro.
     * @return distance in meters (GasFinder2) or meters x 10 (GasFinderMC).
     */
    val distance: Double

    /**
     * Gets the max five-digit Light Level.
     * @return Light Level as integer.
     */
    val lightLevel: Int

    /**
     * Gets the max nineteen-digit current date/time.
     * @return date/time as YYYY/MM/DD hh:mm:ss.
     */
    val dateTime: Date

    /**
     * Gets the max ten-char. device serial number.
     * @return Serial Number id
     */
    val serialNumber: String?

    /**
     * Gets the max 4-digit status code.
     * @return Status Code as number in range: 1 - 0xFFFF
     */
    val statusCode: Int
}