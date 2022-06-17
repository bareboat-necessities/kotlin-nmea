/* 
 * OSDSentence.java
 * Copyright (C) 2020 Joshua Sweaney
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

import net.sf.marineapi.nmea.util.*

/**
 * Own ship data.<br></br>
 * Gives the movement vector of the ship.<br></br>
 *
 * Includes (in this order): Heading (degrees true), Heading status [net.sf.marineapi.nmea.util.DataStatus] <br></br>
 * Vessel course (degrees true), Course reference [net.sf.marineapi.nmea.util.ReferenceSystem],<br></br>
 * Vessel speed, Speed reference (ReferenceSystem), Vessel set (degrees true), vessel drift,<br></br>
 * Speed units [net.sf.marineapi.nmea.util.Units]<br></br>
 *
 * Example:<br></br>
 * `$RAOSD,35.1,A,36.0,P,10.2,P,15.3,0.1,N*41<CR><LF> `
 *
 * @author Joshua Sweaney
 */
interface OSDSentence {
    /**
     * Get ownship heading
     *
     * @return Double ownship heading
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set ownship heading
     * @param heading the heading
     */
    var heading: Double
    /**
     * Get the status of heading data
     * @return DataStatus the status
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set the heading data status
     * @param status the status
     */
    var headingStatus: DataStatus
    /**
     * Get the course of ownship
     * @return Double the course
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set ownship course
     * @param course the course
     */
    var course: Double
    /**
     * Get the reference system used to calculate course
     * @return ReferenceSystem the reference
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set the reference system for the course
     * @param reference the reference
     */
    var courseReference: ReferenceSystem
    /**
     * Get ownship speed
     * @return Double the speed
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set ownship speed
     * @param speed the speed
     */
    var speed: Double
    /**
     * Get the reference system used to calculate speed
     * @return ReferenceSystem the reference
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set the reference system for the speed
     * @param reference the reference
     */
    var speedReference: ReferenceSystem
    /**
     * Get the vessel set (water current direction)
     * @return Double the set
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set the vessel set
     * @param set the vessel set
     */
    var vesselSet: Double
    /**
     * Get the vessel drift
     * @return double the drift
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set the vessel drift
     * @param drift the vessel drift
     */
    var vesselDrift: Double
    /**
     * Get the units of speed measurements
     * @return Units the speed units (K, N, S)
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    /**
     * Set the speed units
     * @param units the units
     */
    var speedUnits: Units
}