package net.sf.marineapi.nmea.sentence

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/*
 * TalkerIdTest.java
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
 */ /**
 * @author Kimmo Tuukkanen
 */
class TalkerIdTest {
    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    /**
     * Test method for
     * [TalkerId.parse].
     */
    @Test
    fun testParse() {
        assertEquals(TalkerId.GP, TalkerId.parse("\$GPGLL,,,,,,,"))
        assertEquals(TalkerId.GL, TalkerId.parse("\$GLGSV,,,,,,,"))
        assertEquals(TalkerId.GN, TalkerId.parse("\$GNGSV,,,,,,,"))
        assertEquals(TalkerId.II, TalkerId.parse("\$IIDPT,,,,,,,"))
    }

    @Test
    fun testParseProprietary() {
        assertEquals(TalkerId.P, TalkerId.parse("\$PRWIILOG,GGA,A,T,1,0"))
    }

    @Test
    fun testParseAIS() {
        assertEquals(TalkerId.AI, TalkerId.parse("!AIVDM,,,,,,,"))
        assertEquals(TalkerId.AB, TalkerId.parse("!ABVDM,,,,,,,"))
        assertEquals(TalkerId.BS, TalkerId.parse("!BSVDM,,,,,,,"))
    }

    @Test
    fun testParseUnknown() {
        try {
            TalkerId.parse("\$XXXXX,,,,,,")
            Assert.fail("Did not throw exception")
        } catch (e: Exception) {
            // pass
        }
    }
}