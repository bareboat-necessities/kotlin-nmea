/* 
 * OSDTest.java
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
package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Units
import org.junit.Assert.assertEquals

/**
 * OSDTest
 *
 * @author Joshua Sweaney
 */
class OSDTest {
    var example: OSDSentence? = null
    var empty: OSDSentence? = null
    @Before
    fun setUp() {
        example = OSDParser(EXAMPLE)
        empty = OSDParser(TalkerId.RA)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.getHeading].
     */
    @Test
    fun testGetHeading() {
        assertEquals(35.1, example.heading, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.getHeadingStatus].
     */
    @Test
    fun testGetHeadingStatus() {
        assertEquals(DataStatus.ACTIVE, example.headingStatus)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.getCourse].
     */
    @Test
    fun testGetCourse() {
        assertEquals(36.0, example.course, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.getCourseReference].
     */
    @Test
    fun testGetCourseReference() {
        assertEquals(
            ReferenceSystem.POSITIONING_SYSTEM_GROUND_REFERENCE,
            example.courseReference
        )
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.getSpeed].
     */
    @Test
    fun testGetSpeed() {
        assertEquals(10.2, example.speed, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.getSpeedReference].
     */
    @Test
    fun testGetSpeedReference() {
        assertEquals(
            ReferenceSystem.POSITIONING_SYSTEM_GROUND_REFERENCE,
            example.speedReference
        )
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.getVesselSet].
     */
    @Test
    fun testGetVesselSet() {
        assertEquals(15.3, example.vesselSet, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.getVesselDrift].
     */
    @Test
    fun testGetVesselDrift() {
        assertEquals(0.1, example.vesselDrift, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.getSpeedUnits].
     */
    @Test
    fun testGetSpeedUnits() {
        assertEquals(Units.NAUTICAL_MILES, example.speedUnits)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.setHeading].
     */
    @Test
    fun testSetHeading() {
        val newHeading = 275.2
        empty.heading = newHeading
        assertEquals(newHeading, empty.heading, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.setHeadingStatus].
     */
    @Test
    fun testSetHeadingStatus() {
        val newStatus: DataStatus = DataStatus.VOID
        empty.headingStatus = newStatus
        assertEquals(newStatus, empty.headingStatus)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.setCourse].
     */
    @Test
    fun testSetCourse() {
        val newCourse = 95.3
        empty.course = newCourse
        assertEquals(newCourse, empty.course, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.setCourseReference].
     */
    @Test
    fun testSetCourseReference() {
        val newReference: ReferenceSystem = ReferenceSystem.BOTTOM_TRACKING_LOG
        empty.courseReference = newReference
        assertEquals(newReference, empty.courseReference)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.setSpeed].
     */
    @Test
    fun testSetSpeed() {
        val newSpeed = 11.2
        empty.speed = newSpeed
        assertEquals(newSpeed, empty.speed, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.setSpeedReference].
     */
    @Test
    fun testSetSpeedReference() {
        val newReference: ReferenceSystem = ReferenceSystem.RADAR_TRACKING
        empty.speedReference = newReference
        assertEquals(newReference, empty.speedReference)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.setVesselSet].
     */
    @Test
    fun testSetVesselSet() {
        val newSet = 13.9
        empty.vesselSet = newSet
        assertEquals(newSet, empty.vesselSet, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.setVesselDrift].
     */
    @Test
    fun testSetVesselDrift() {
        val newDrift = 365.4
        empty.vesselDrift = newDrift
        assertEquals(newDrift, empty.vesselDrift, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.OSDParser.setSpeedUnits].
     */
    @Test(expected = IllegalArgumentException::class)
    fun testSetSpeedUnits() {
        val newUnits = Units.NAUTICAL_MILES
        empty.speedUnits = newUnits
        assertEquals(newUnits, empty.speedUnits)

        // An invalid speed unit. Should throw IllegalArgumentException.
        empty.speedUnits = Units.CELSIUS
    }

    companion object {
        const val EXAMPLE = "\$RAOSD,35.1,A,36.0,P,10.2,P,15.3,0.1,N*41"
    }
}