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

import net.sf.marineapi.nmea.parser.MWVParser
import net.sf.marineapi.nmea.sentence.MWVSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author Kimmo Tuukkanen
 */
class MWVTest {
    private var mwv: MWVSentence? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mwv = MWVParser(EXAMPLE)
    }

    /**
     * Test method for
     * [MWVParser.MWVParser]
     * .
     */
    @Test
    fun testMWVParserTalkerId() {
        val mwvp = MWVParser(TalkerId.II)
        assertEquals(TalkerId.II, mwvp.getTalkerId())
        assertEquals(SentenceId.MWV.toString(), mwvp.getSentenceId())
        assertEquals(DataStatus.VOID, mwvp.getStatus())
    }

    /**
     * Test method for [MWVParser.getAngle]
     * .
     */
    @Test
    fun testGetAngle() {
        assertEquals(125.1, mwv!!.getAngle(), 0.1) // "$IIMWV,125.1,T,5.5,A"
    }

    /**
     * Test method for [MWVParser.getSpeed]
     * .
     */
    @Test
    fun testGetSpeed() {
        assertEquals(5.5, mwv!!.getSpeed(), 0.1)
    }

    /**
     * Test method for
     * [MWVParser.getSpeedUnit].
     */
    @Test
    fun testGetSpeedUnit() {
        assertEquals(Units.METER, mwv!!.getSpeedUnit())
    }

    /**
     * Test method for
     * [MWVParser.getStatus].
     */
    @Test
    fun testGetStatus() {
        assertEquals(DataStatus.ACTIVE, mwv!!.getStatus())
    }

    /**
     * Test method for [MWVParser.isTrue].
     */
    @Test
    fun testIsTrue() {
        assertTrue(mwv!!.isTrue())
    }

    /**
     * Test method for
     * [MWVParser.setAngle].
     */
    @Test
    fun testSetAngle() {
        val angle = 88.123
        mwv!!.setAngle(angle)
        assertEquals(angle, mwv!!.getAngle(), 0.1)
    }

    /**
     * Test method for
     * [MWVParser.setAngle].
     */
    @Test
    fun testSetNegativeAngle() {
        val angle = -0.1
        try {
            mwv!!.setAngle(angle)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [MWVParser.setAngle].
     */
    @Test
    fun testSetAngleOutOfRange() {
        val angle = 360.1
        try {
            mwv!!.setAngle(angle)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [MWVParser.setSpeed].
     */
    @Test
    fun testSetSpeed() {
        val speed = 7.75
        mwv!!.setSpeed(speed)
        assertEquals(speed, mwv!!.getSpeed(), 0.1)
    }

    /**
     * Test method for
     * [MWVParser.setSpeed].
     */
    @Test
    fun testSetNegativeSpeed() {
        val speed = -0.01
        try {
            mwv!!.setSpeed(speed)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [MWVParser.setSpeedUnit]
     * .
     */
    @Test
    fun testSetSpeedUnit() {
        mwv!!.setSpeedUnit(Units.KILOMETERS)
        assertEquals(Units.KILOMETERS, mwv!!.getSpeedUnit())
    }

    /**
     * Test method for
     * [MWVParser.setSpeedUnit]
     * .
     */
    @Test
    fun testSetInvalidSpeedUnit() {
        try {
            mwv!!.setSpeedUnit(Units.FATHOMS)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [MWVParser.setStatus]
     * .
     */
    @Test
    fun testSetStatus() {
        mwv!!.setStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, mwv!!.getStatus())
    }

    /**
     * Test method for
     * [MWVParser.setTrue].
     */
    @Test
    fun testSetTrue() {
        assertTrue(mwv!!.isTrue())
        mwv!!.setTrue(false)
        assertFalse(mwv!!.isTrue())
    }

    companion object {
        const val EXAMPLE = "\$IIMWV,125.1,T,5.5,M,A"
    }
}