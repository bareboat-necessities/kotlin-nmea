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

import net.sf.marineapi.nmea.sentence.OSDSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.ReferenceSystem
import net.sf.marineapi.nmea.util.Units
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
     * [OSDParser.getHeading].
     */
    @Test
    fun testGetHeading() {
        Assert.assertEquals(35.1, example!!.getHeading(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.getHeadingStatus].
     */
    @Test
    fun testGetHeadingStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, example!!.getHeadingStatus())
    }

    /**
     * Test for
     * [OSDParser.getCourse].
     */
    @Test
    fun testGetCourse() {
        Assert.assertEquals(36.0, example!!.getCourse(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.getCourseReference].
     */
    @Test
    fun testGetCourseReference() {
        Assert.assertEquals(
            ReferenceSystem.POSITIONING_SYSTEM_GROUND_REFERENCE,
            example!!.getCourseReference()
        )
    }

    /**
     * Test for
     * [OSDParser.getSpeed].
     */
    @Test
    fun testGetSpeed() {
        Assert.assertEquals(10.2, example!!.getSpeed(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.getSpeedReference].
     */
    @Test
    fun testGetSpeedReference() {
        Assert.assertEquals(
            ReferenceSystem.POSITIONING_SYSTEM_GROUND_REFERENCE,
            example!!.getSpeedReference()
        )
    }

    /**
     * Test for
     * [OSDParser.getVesselSet].
     */
    @Test
    fun testGetVesselSet() {
        Assert.assertEquals(15.3, example!!.getVesselSet(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.getVesselDrift].
     */
    @Test
    fun testGetVesselDrift() {
        Assert.assertEquals(0.1, example!!.getVesselDrift(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.getSpeedUnits].
     */
    @Test
    fun testGetSpeedUnits() {
        Assert.assertEquals(Units.NAUTICAL_MILES, example!!.getSpeedUnits())
    }

    /**
     * Test for
     * [OSDParser.setHeading].
     */
    @Test
    fun testSetHeading() {
        val newHeading = 275.2
        empty!!.setHeading(newHeading)
        Assert.assertEquals(newHeading, empty!!.getHeading(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.setHeadingStatus].
     */
    @Test
    fun testSetHeadingStatus() {
        val newStatus = DataStatus.VOID
        empty!!.setHeadingStatus(newStatus)
        Assert.assertEquals(newStatus, empty!!.getHeadingStatus())
    }

    /**
     * Test for
     * [OSDParser.setCourse].
     */
    @Test
    fun testSetCourse() {
        val newCourse = 95.3
        empty!!.setCourse(newCourse)
        Assert.assertEquals(newCourse, empty!!.getCourse(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.setCourseReference].
     */
    @Test
    fun testSetCourseReference() {
        val newReference = ReferenceSystem.BOTTOM_TRACKING_LOG
        empty!!.setCourseReference(newReference)
        Assert.assertEquals(newReference, empty!!.getCourseReference())
    }

    /**
     * Test for
     * [OSDParser.setSpeed].
     */
    @Test
    fun testSetSpeed() {
        val newSpeed = 11.2
        empty!!.setSpeed(newSpeed)
        Assert.assertEquals(newSpeed, empty!!.getSpeed(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.setSpeedReference].
     */
    @Test
    fun testSetSpeedReference() {
        val newReference = ReferenceSystem.RADAR_TRACKING
        empty!!.setSpeedReference(newReference)
        Assert.assertEquals(newReference, empty!!.getSpeedReference())
    }

    /**
     * Test for
     * [OSDParser.setVesselSet].
     */
    @Test
    fun testSetVesselSet() {
        val newSet = 13.9
        empty!!.setVesselSet(newSet)
        Assert.assertEquals(newSet, empty!!.getVesselSet(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.setVesselDrift].
     */
    @Test
    fun testSetVesselDrift() {
        val newDrift = 365.4
        empty!!.setVesselDrift(newDrift)
        Assert.assertEquals(newDrift, empty!!.getVesselDrift(), 0.0)
    }

    /**
     * Test for
     * [OSDParser.setSpeedUnits].
     */
    @Test(expected = IllegalArgumentException::class)
    fun testSetSpeedUnits() {
        val newUnits = Units.NAUTICAL_MILES
        empty!!.setSpeedUnits(newUnits)
        Assert.assertEquals(newUnits, empty!!.getSpeedUnits())

        // An invalid speed unit. Should throw IllegalArgumentException.
        empty!!.setSpeedUnits(Units.CELSIUS)
    }

    companion object {
        const val EXAMPLE = "\$RAOSD,35.1,A,36.0,P,10.2,P,15.3,0.1,N*41"
    }
}