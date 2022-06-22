/* 
 * HDGTest.java
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

import net.sf.marineapi.nmea.parser.HDGParser
import net.sf.marineapi.nmea.sentence.HDGSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author Kimmo Tuukkanen
 */
class HDGTest {
    var hdg: HDGSentence? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        hdg = HDGParser(EXAMPLE)
    }

    /**
     * Test method for [].
     */
    @Test
    fun testConstructor() {
        val empty: HDGSentence = HDGParser(TalkerId.HC)
        assertEquals(TalkerId.HC, empty.getTalkerId())
        assertEquals(SentenceId.HDG.toString(), empty.getSentenceId())
        try {
            empty.getHeading()
        } catch (e: DataNotAvailableException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for [HDTParser.isTrue].
     */
    @Test
    fun testIsTrue() {
        assertFalse(hdg!!.isTrue())
    }

    /**
     * Test method for
     * [HDGParser.HDGParser]
     * .
     */
    @Test
    fun testHDGParserString() {
        assertTrue(hdg!!.isValid())
        assertEquals(TalkerId.HC, hdg!!.getTalkerId())
        assertEquals("HDG", hdg!!.getSentenceId())
    }

    /**
     * Test method for
     * [HDGParser.HDGParser]
     * .
     */
    @Test
    fun testHDGParserTalkerId() {
        val hdgp = HDGParser(TalkerId.HC)
        assertTrue(hdgp.isValid())
        assertEquals(TalkerId.HC, hdgp.getTalkerId())
        assertEquals("HDG", hdgp.getSentenceId())
    }

    /**
     * Test method for
     * [HDGParser.getDeviation].
     */
    @Test
    fun testGetDeviation() {
        assertEquals(1.2, hdg!!.getDeviation(), 0.1)
    }

    /**
     * Test method for
     * [HDGParser.getHeading].
     */
    @Test
    fun testGetHeading() {
        assertEquals(123.4, hdg!!.getHeading(), 0.1)
    }

    /**
     * Test method for
     * [HDGParser.getVariation].
     */
    @Test
    fun testGetVariation() {
        // 1.2 degrees west -> -1.2
        assertEquals(-1.2, hdg!!.getVariation(), 0.1)
    }

    /**
     * Test method for
     * [HDGParser.setDeviation].
     */
    @Test
    fun testSetDeviationWest() {
        val dev = -5.5
        hdg!!.setDeviation(dev)
        assertEquals(dev, hdg!!.getDeviation(), 0.1)
        assertTrue(hdg.toString().contains(",005.5,W,"))
    }

    /**
     * Test method for
     * [HDGParser.setDeviation].
     */
    @Test
    fun testSetDeviationEast() {
        val dev = 5.5
        hdg!!.setDeviation(dev)
        assertEquals(dev, hdg!!.getDeviation(), 0.1)
        assertTrue(hdg.toString().contains(",005.5,E,"))
    }

    /**
     * Test method for
     * [HDGParser.setDeviation].
     */
    @Test
    fun testSetDeviationTooHigh() {
        val value = 180.000001
        try {
            hdg!!.setDeviation(value)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [HDGParser.setDeviation].
     */
    @Test
    fun testSetDeviationTooLow() {
        val value = -180.000001
        try {
            hdg!!.setHeading(value)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [HDGParser.setHeading].
     */
    @Test
    fun testSetHeading() {
        val value = 359.9
        hdg!!.setHeading(value)
        assertEquals(value, hdg!!.getHeading(), 0.1)
    }

    /**
     * Test method for
     * [HDGParser.setHeading].
     */
    @Test
    fun testSetHeadingTooHigh() {
        val value = 360.000001
        try {
            hdg!!.setHeading(value)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [HDGParser.setHeading].
     */
    @Test
    fun testSetHeadingTooLow() {
        val value = -0.000001
        try {
            hdg!!.setHeading(value)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [HDGParser.setVariation].
     */
    @Test
    fun testSetVariationEast() {
        val `var` = 179.9
        hdg!!.setVariation(`var`)
        assertEquals(`var`, hdg!!.getVariation(), 0.1)
        assertTrue(hdg.toString().contains(",179.9,E*"))
    }

    /**
     * Test method for
     * [HDGParser.setVariation].
     */
    @Test
    fun testSetVariationWest() {
        val `var` = -0.1
        hdg!!.setVariation(`var`)
        assertEquals(`var`, hdg!!.getVariation(), 0.1)
        assertTrue(hdg.toString().contains(",000.1,W*"))
    }

    /**
     * Test method for
     * [HDGParser.setVariation].
     */
    @Test
    fun testSetVariationTooHigh() {
        val `var` = 180.00001
        try {
            hdg!!.setVariation(`var`)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [HDGParser.setVariation].
     */
    @Test
    fun testSetVariationTooLow() {
        val `var` = -180.00001
        try {
            hdg!!.setVariation(`var`)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    companion object {
        const val EXAMPLE = "\$HCHDG,123.4,1.2,E,1.2,W"
    }
}