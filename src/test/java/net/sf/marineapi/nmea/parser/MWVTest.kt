/* 
 * MWVTest.java
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
package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Units
import org.junit.Assert.assertEquals

/**
 * @author Kimmo Tuukkanen
 */
class MWVTest {
    private var mwv: MWVSentence? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mwv = MWVParser(EXAMPLE)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.MWVParser]
     * .
     */
    @Test
    fun testMWVParserTalkerId() {
        val mwvp = MWVParser(TalkerId.II)
        assertEquals(TalkerId.II, mwvp.talkerId)
        assertEquals(SentenceId.MWV.toString(), mwvp.sentenceId)
        assertEquals(DataStatus.VOID, mwvp.status)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.MWVParser.getAngle]
     * .
     */
    @Test
    fun testGetAngle() {
        assertEquals(125.1, mwv.angle, 0.1) // "$IIMWV,125.1,T,5.5,A"
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.MWVParser.getSpeed]
     * .
     */
    @Test
    fun testGetSpeed() {
        assertEquals(5.5, mwv.speed, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.getSpeedUnit].
     */
    @Test
    fun testGetSpeedUnit() {
        assertEquals(Units.METER, mwv.speedUnit)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.getStatus].
     */
    @Test
    fun testGetStatus() {
        assertEquals(DataStatus.ACTIVE, mwv.status)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.MWVParser.isTrue].
     */
    @Test
    fun testIsTrue() {
        assertTrue(mwv.isTrue)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.setAngle].
     */
    @Test
    fun testSetAngle() {
        val angle = 88.123
        mwv.angle = angle
        assertEquals(angle, mwv.angle, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.setAngle].
     */
    @Test
    fun testSetNegativeAngle() {
        val angle = -0.1
        try {
            mwv.angle = angle
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.setAngle].
     */
    @Test
    fun testSetAngleOutOfRange() {
        val angle = 360.1
        try {
            mwv.angle = angle
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.setSpeed].
     */
    @Test
    fun testSetSpeed() {
        val speed = 7.75
        mwv.speed = speed
        assertEquals(speed, mwv.speed, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.setSpeed].
     */
    @Test
    fun testSetNegativeSpeed() {
        val speed = -0.01
        try {
            mwv.speed = speed
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.setSpeedUnit]
     * .
     */
    @Test
    fun testSetSpeedUnit() {
        mwv.speedUnit = Units.KILOMETERS
        assertEquals(Units.KILOMETERS, mwv.speedUnit)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.setSpeedUnit]
     * .
     */
    @Test
    fun testSetInvalidSpeedUnit() {
        try {
            mwv.speedUnit = Units.FATHOMS
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.setStatus]
     * .
     */
    @Test
    fun testSetStatus() {
        mwv.status = DataStatus.VOID
        assertEquals(DataStatus.VOID, mwv.status)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.MWVParser.setTrue].
     */
    @Test
    fun testSetTrue() {
        assertTrue(mwv.isTrue)
        mwv.isTrue = false
        assertFalse(mwv.isTrue)
    }

    companion object {
        const val EXAMPLE = "\$IIMWV,125.1,T,5.5,M,A"
    }
}