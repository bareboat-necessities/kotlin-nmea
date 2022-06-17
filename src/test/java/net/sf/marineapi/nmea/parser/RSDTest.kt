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

import net.sf.marineapi.nmea.sentence.RSDSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DisplayRotation
import net.sf.marineapi.nmea.util.Units
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
     * [RSDParser.getOriginOneRange].
     */
    @Test
    fun testGetOriginOneRange() {
        Assert.assertEquals(12.0, example!!.getOriginOneRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getOriginOneBearing].
     */
    @Test
    fun testGetOriginOneBearing() {
        Assert.assertEquals(90.0, example!!.getOriginOneBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getVRMOneRange].
     */
    @Test
    fun testGetVRMOneRange() {
        Assert.assertEquals(24.0, example!!.getVRMOneRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getEBLOneBearing].
     */
    @Test
    fun testGetEBLOneBearing() {
        Assert.assertEquals(45.0, example!!.getEBLOneBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getOriginTwoRange].
     */
    @Test
    fun testGetOriginTwoRange() {
        Assert.assertEquals(6.0, example!!.getOriginTwoRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getOriginTwoBearing].
     */
    @Test
    fun testGetOriginTwoBearing() {
        Assert.assertEquals(270.0, example!!.getOriginTwoBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getVRMTwoRange].
     */
    @Test
    fun testGetVRMTwoRange() {
        Assert.assertEquals(12.0, example!!.getVRMTwoRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getEBLTwoBearing].
     */
    @Test
    fun testGetEBLTwoBearing() {
        Assert.assertEquals(315.0, example!!.getEBLTwoBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getCursorRange].
     */
    @Test
    fun testGetCursorRange() {
        Assert.assertEquals(6.5, example!!.getCursorRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getCursorBearing].
     */
    @Test
    fun testGetCursorBearing() {
        Assert.assertEquals(118.0, example!!.getCursorBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getRangeScale].
     */
    @Test
    fun testGetRangeScale() {
        Assert.assertEquals(96.0, example!!.getRangeScale(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.getRangeUnits].
     */
    @Test
    fun testGetRangeUnits() {
        Assert.assertEquals(Units.NAUTICAL_MILES, example!!.getRangeUnits())
    }

    /**
     * Test for
     * [RSDParser.getDisplayRotation].
     */
    @Test
    fun testGetDisplayRotation() {
        Assert.assertEquals(DisplayRotation.NORTH_UP, example!!.getDisplayRotation())
    }

    /**
     * Test for
     * [RSDParser.setOriginOneRange].
     */
    @Test
    fun testSetOriginOneRange() {
        val newRange = 0.75
        empty!!.setOriginOneRange(newRange)
        Assert.assertEquals(newRange, empty!!.getOriginOneRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setOriginOneBearing].
     */
    @Test
    fun testSetOriginOneBearing() {
        val newBearing = 93.2
        empty!!.setOriginOneBearing(newBearing)
        Assert.assertEquals(newBearing, empty!!.getOriginOneBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setVRMOneRange].
     */
    @Test
    fun testSetVRMOneRange() {
        val newRange = 12.5
        empty!!.setVRMOneRange(newRange)
        Assert.assertEquals(newRange, empty!!.getVRMOneRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setEBLOneBearing].
     */
    @Test
    fun testSetEBLOneBearing() {
        val newBearing = 147.0
        empty!!.setEBLOneBearing(newBearing)
        Assert.assertEquals(newBearing, empty!!.getEBLOneBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setOriginTwoRange].
     */
    @Test
    fun testSetOriginTwoRange() {
        val newRange = 0.75
        empty!!.setOriginTwoRange(newRange)
        Assert.assertEquals(newRange, empty!!.getOriginTwoRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setOriginTwoBearing].
     */
    @Test
    fun testSetOriginTwoBearing() {
        val newBearing = 93.2
        empty!!.setOriginTwoBearing(newBearing)
        Assert.assertEquals(newBearing, empty!!.getOriginTwoBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setVRMTwoRange].
     */
    @Test
    fun testSetVRMTwoRange() {
        val newRange = 12.5
        empty!!.setVRMTwoRange(newRange)
        Assert.assertEquals(newRange, empty!!.getVRMTwoRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setEBLTwoBearing].
     */
    @Test
    fun testSetEBLTwoBearing() {
        val newBearing = 147.0
        empty!!.setEBLTwoBearing(newBearing)
        Assert.assertEquals(newBearing, empty!!.getEBLTwoBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setCursorRange].
     */
    @Test
    fun testSetCursorRange() {
        val newRange = 48.32
        empty!!.setCursorRange(newRange)
        Assert.assertEquals(newRange, empty!!.getCursorRange(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setCursorBearing].
     */
    @Test
    fun testSetCursorBearing() {
        val newBearing = 300.4
        empty!!.setCursorBearing(newBearing)
        Assert.assertEquals(newBearing, empty!!.getCursorBearing(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setRangeScale].
     */
    @Test
    fun testSetRangeScale() {
        val newScale = 0.75
        empty!!.setRangeScale(newScale)
        Assert.assertEquals(newScale, empty!!.getRangeScale(), 0.0)
    }

    /**
     * Test for
     * [RSDParser.setRangeUnits].
     */
    @Test(expected = IllegalArgumentException::class)
    fun testSetRangeUnits() {
        val newUnits = Units.KILOMETERS
        empty!!.setRangeUnits(newUnits)
        Assert.assertEquals(newUnits, empty!!.getRangeUnits())

        // Invalid range unit. Should throw IllegalArgumentException
        val invalidUnits = Units.FATHOMS
        empty!!.setRangeUnits(invalidUnits)
    }

    /**
     * Test for
     * [RSDParser.setDisplayRotation].
     */
    @Test
    fun testSetDisplayRotation() {
        val newRotation = DisplayRotation.COURSE_UP
        empty!!.setDisplayRotation(newRotation)
        Assert.assertEquals(newRotation, empty!!.getDisplayRotation())
    }

    companion object {
        const val EXAMPLE = "\$RARSD,12,90,24,45,6,270,12,315,6.5,118,96,N,N*5A"
    }
}