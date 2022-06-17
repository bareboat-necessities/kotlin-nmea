/* 
 * RSDTest.java
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
 * RSDTest
 *
 * @author Joshua Sweaney
 */
class RSDTest {
    var example: RSDSentence? = null
    var empty: RSDSentence? = null
    @Before
    fun setUp() {
        example = RSDParser(EXAMPLE)
        empty = RSDParser(TalkerId.RA)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getOriginOneRange].
     */
    @Test
    fun testGetOriginOneRange() {
        assertEquals(12.0, example.originOneRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getOriginOneBearing].
     */
    @Test
    fun testGetOriginOneBearing() {
        assertEquals(90.0, example.originOneBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getVRMOneRange].
     */
    @Test
    fun testGetVRMOneRange() {
        assertEquals(24.0, example.vRMOneRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getEBLOneBearing].
     */
    @Test
    fun testGetEBLOneBearing() {
        assertEquals(45.0, example.eBLOneBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getOriginTwoRange].
     */
    @Test
    fun testGetOriginTwoRange() {
        assertEquals(6.0, example.originTwoRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getOriginTwoBearing].
     */
    @Test
    fun testGetOriginTwoBearing() {
        assertEquals(270.0, example.originTwoBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getVRMTwoRange].
     */
    @Test
    fun testGetVRMTwoRange() {
        assertEquals(12.0, example.vRMTwoRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getEBLTwoBearing].
     */
    @Test
    fun testGetEBLTwoBearing() {
        assertEquals(315.0, example.eBLTwoBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getCursorRange].
     */
    @Test
    fun testGetCursorRange() {
        assertEquals(6.5, example.cursorRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getCursorBearing].
     */
    @Test
    fun testGetCursorBearing() {
        assertEquals(118.0, example.cursorBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getRangeScale].
     */
    @Test
    fun testGetRangeScale() {
        assertEquals(96.0, example.rangeScale, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getRangeUnits].
     */
    @Test
    fun testGetRangeUnits() {
        assertEquals(Units.NAUTICAL_MILES, example.rangeUnits)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.getDisplayRotation].
     */
    @Test
    fun testGetDisplayRotation() {
        assertEquals(DisplayRotation.NORTH_UP, example.displayRotation)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setOriginOneRange].
     */
    @Test
    fun testSetOriginOneRange() {
        val newRange = 0.75
        empty.originOneRange = newRange
        assertEquals(newRange, empty.originOneRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setOriginOneBearing].
     */
    @Test
    fun testSetOriginOneBearing() {
        val newBearing = 93.2
        empty.originOneBearing = newBearing
        assertEquals(newBearing, empty.originOneBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setVRMOneRange].
     */
    @Test
    fun testSetVRMOneRange() {
        val newRange = 12.5
        empty.vRMOneRange = newRange
        assertEquals(newRange, empty.vRMOneRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setEBLOneBearing].
     */
    @Test
    fun testSetEBLOneBearing() {
        val newBearing = 147.0
        empty.eBLOneBearing = newBearing
        assertEquals(newBearing, empty.eBLOneBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setOriginTwoRange].
     */
    @Test
    fun testSetOriginTwoRange() {
        val newRange = 0.75
        empty.originTwoRange = newRange
        assertEquals(newRange, empty.originTwoRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setOriginTwoBearing].
     */
    @Test
    fun testSetOriginTwoBearing() {
        val newBearing = 93.2
        empty.originTwoBearing = newBearing
        assertEquals(newBearing, empty.originTwoBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setVRMTwoRange].
     */
    @Test
    fun testSetVRMTwoRange() {
        val newRange = 12.5
        empty.vRMTwoRange = newRange
        assertEquals(newRange, empty.vRMTwoRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setEBLTwoBearing].
     */
    @Test
    fun testSetEBLTwoBearing() {
        val newBearing = 147.0
        empty.eBLTwoBearing = newBearing
        assertEquals(newBearing, empty.eBLTwoBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setCursorRange].
     */
    @Test
    fun testSetCursorRange() {
        val newRange = 48.32
        empty.cursorRange = newRange
        assertEquals(newRange, empty.cursorRange, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setCursorBearing].
     */
    @Test
    fun testSetCursorBearing() {
        val newBearing = 300.4
        empty.cursorBearing = newBearing
        assertEquals(newBearing, empty.cursorBearing, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setRangeScale].
     */
    @Test
    fun testSetRangeScale() {
        val newScale = 0.75
        empty.rangeScale = newScale
        assertEquals(newScale, empty.rangeScale, 0.0)
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setRangeUnits].
     */
    @Test(expected = IllegalArgumentException::class)
    fun testSetRangeUnits() {
        val newUnits = Units.KILOMETERS
        empty.rangeUnits = newUnits
        assertEquals(newUnits, empty.rangeUnits)

        // Invalid range unit. Should throw IllegalArgumentException
        val invalidUnits = Units.FATHOMS
        empty.rangeUnits = invalidUnits
    }

    /**
     * Test for
     * [net.sf.marineapi.nmea.parser.RSDParser.setDisplayRotation].
     */
    @Test
    fun testSetDisplayRotation() {
        val newRotation: DisplayRotation = DisplayRotation.COURSE_UP
        empty.displayRotation = newRotation
        assertEquals(newRotation, empty.displayRotation)
    }

    companion object {
        const val EXAMPLE = "\$RARSD,12,90,24,45,6,270,12,315,6.5,118,96,N,N*5A"
    }
}