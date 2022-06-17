/* 
 * WaypointTest.java
 * Copyright 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.util

import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * WaypointTest
 *
 * @author Kimmo Tuukkanen
 */
class WaypointTest {
    private val id1 = "FOO"
    private val id2 = "BAR"
    private val desc = "Description text"
    var point: Waypoint? = null
    @Before
    fun setUp() {
        point = Waypoint(id1, 60.0, 25.0, Datum.WGS84)
    }

    /**
     * Test method for
     * [Waypoint.setDescription]
     * .
     */
    @Test
    fun testDescription() {
        Assert.assertEquals("", point!!.description)
        point!!.description = desc
        Assert.assertEquals(desc, point!!.description)
    }

    /**
     * Test method for
     * [Waypoint.setId].
     */
    @Test
    fun testId() {
        Assert.assertEquals(id1, point!!.id)
        point!!.id = id2
        Assert.assertEquals(id2, point!!.id)
    }
}