/*
 * HTDSentence.java
 * Copyright (C) 2015 Paweł Kozioł, Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.util.DataStatus

/**
 * Heading/Track control systems data and commands. HTD provides output from a
 * heading controller with information about values, modes and references in
 * use, while HTC provides input to a heading controller to set values, modes
 * and references
 * [[nuovamarea.com](http://www.nuovamarea.com/files/product%20manuals/nm%20manuals/NM-2C_v1.00.pdf)].
 *
 * @author Paweł Kozioł
 * @see HTCSentence
 */
interface HTDSentence : HTCSentence, HeadingSentence {
    /**
     * Returns the rudder status.
     *
     * @return `DataStatus.ACTIVE` when within limits or
     * `DataStatus.VOID` when limit reached or exceeded.
     */
    val rudderStatus: DataStatus?

    /**
     * Returns the off-heading status.
     *
     * @return `DataStatus.ACTIVE` when within limits or
     * `DataStatus.VOID` when limit reached or exceeded.
     */
    val offHeadingStatus: DataStatus?

    /**
     * Returns the off-track status.
     *
     * @return `DataStatus.ACTIVE` when within limits or
     * `DataStatus.VOID` when limit reached or exceeded.
     */
    val offTrackStatus: DataStatus?
}