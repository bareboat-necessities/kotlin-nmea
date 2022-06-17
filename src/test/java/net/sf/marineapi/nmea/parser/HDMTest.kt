/* 
 * HDMTest.java
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

import org.junit.Assert.assertEquals

/**
 * HDM parser tests.
 *
 * @author Kimmo Tuukkanen
 */
class HDMTest {
    var hdm: HDMSentence? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        hdm = HDMParser(EXAMPLE)
    }

    /**
     * Test method for [].
     */
    @Test
    fun testConstructor() {
        val empty: HDMSentence = HDMParser(TalkerId.HC)
        assertEquals(TalkerId.HC, empty.talkerId)
        assertEquals(SentenceId.HDM.toString(), empty.sentenceId)
        try {
            empty.heading
        } catch (e: DataNotAvailableException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.HDTParser.isTrue].
     */
    @Test
    fun testIsTrue() {
        assertFalse(hdm.isTrue)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.HDMParser.getHeading].
     */
    @Test
    fun testGetHeading() {
        val value: Double = hdm.heading
        assertEquals(90.0, value, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.HDMParser.setHeading].
     */
    @Test
    fun testSetHeading() {
        hdm.heading = 123.45
        assertEquals(123.5, hdm.heading, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.HDMParser.setHeading].
     */
    @Test
    fun testSetNegativeHeading() {
        try {
            hdm.heading = -0.005
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.HDMParser.setHeading].
     */
    @Test
    fun testSetHeadingTooHigh() {
        try {
            hdm.heading = 360.0001
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    companion object {
        const val EXAMPLE = "\$GPHDM,90.0,M"
    }
}