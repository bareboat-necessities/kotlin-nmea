/* 
 * RTESentence.java
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

import net.sf.marineapi.nmea.util.RouteType


/**
 * GPS route data and list of waypoints.
 *
 *
 * Example:<br></br>
 * `$GPRTE,1,1,c,0,MELIN,RUSKI,KNUDAN*25`
 *
 * @author Kimmo Tuukkanen
 */
interface RTESentence : Sentence {

    /**
     * Add a waypoint ID at the end of waypoint list. The number of waypoint id
     * fields is increased by one on each addition.
     *
     * @param id Waypoint ID to add.
     * @return The total number of waypoint IDs after addition.
     */
    fun addWaypointId(id: String?): Int

    /**
     * Get the number or name of the route.
     *
     * @return Route ID or name as String
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getRouteId(): String?

    /**
     * Get the number of sentences in RTE sequence.
     *
     * @return integer
     * @see .getSentenceIndex
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getSentenceCount(): Int

    /**
     * Get the index of sentence in RTE sequence.
     *
     * @return integer
     * @see .getSentenceCount
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getSentenceIndex(): Int

    /**
     * Get the number of waypoints IDs in this sentence.
     *
     * @return Waypoint count
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getWaypointCount(): Int

    /**
     * Get the list of route waypoints.
     *
     * @return Waypoint IDs as String array
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun getWaypointIds(): Array<String?>?

    /**
     * Tells if the sentence holds a current active route data.
     *
     * @return true if active route, otherwise false.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
     * unexpected or illegal value.
     */
    fun isActiveRoute(): Boolean

    /**
     * Tells if this is the first sentence in RTE sequence.
     *
     * @return true if there's no sentences left, otherwise false.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the sentence
     * index or sentence count is not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If sentence index or count
     * fields contain unexpected or illegal value.
     */
    fun isFirst(): Boolean

    /**
     * Tells if this is the last sentence in RTE sequence.
     *
     * @return true if there's no sentences left, otherwise false.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the sentence
     * index or sentence count is not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If sentence index or count
     * fields contain unexpected or illegal value.
     */
    fun isLast(): Boolean

    /**
     * Tells if the sentence holds a current working route data.
     *
     * @return true if working route, otherwise false.
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
     * not available.
     * @throws net.sf.marineapi.nmea.parser.ParseException If status field contains
     * illegal value.
     */
    fun isWorkingRoute(): Boolean

    /**
     * Set the route name or number.
     *
     * @param id Route ID or name as String
     */
    fun setRouteId(id: String?)

    /**
     * Set the type of route.
     *
     * @param type RouteType to set
     */
    fun setRouteType(type: RouteType?)

    /**
     * Set the number of sentences in RTE sequence.
     *
     * @param count Sentence count in sequence
     * @throws IllegalArgumentException If the specified count is negative.
     */
    fun setSentenceCount(count: Int)

    /**
     * Set the index of sentence in RTE sequence.
     *
     * @param index Sentence index in sequence
     * @throws IllegalArgumentException If specified index is negative.
     */
    fun setSentenceIndex(index: Int)

    /**
     * Set the list of route waypoints.
     *
     * @param ids String array of waypoint IDs
     */
    fun setWaypointIds(ids: Array<String?>?)
}