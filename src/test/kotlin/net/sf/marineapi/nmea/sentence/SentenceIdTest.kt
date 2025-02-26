/* 
 * SentenceIdTest.java
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
package net.sf.marineapi.nmea.sentence

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

/**
 * @author Kimmo Tuukkanen
 */
class SentenceIdTest {
    @Test
    fun testParseKnownId() {
        val s = SentenceId.parse("\$GPGLL,,,,,,,")
        assertEquals(SentenceId.GLL, s)
    }

    @Test
    fun testParseUnknownId() {
        try {
            SentenceId.parse("\$ABCDE,,,,,,")
            fail("Did not throw exception")
        } catch (e: Exception) {
            // pass
        }
    }

    @Test
    fun testParseStrStandardId() {
        val s = SentenceId.parseStr("\$GPGLL,,,,,,,")
        assertEquals("GLL", s)
    }

    @Test
    fun testParseStrNormalLengthProprietaryId() {
        val s = SentenceId.parseStr("\$PGRMZ,,,,,,,")
        assertEquals("GRMZ", s)
    }

    @Test
    fun testParseStrShortProprietaryId() {
        val s = SentenceId.parseStr("\$PBVE,,,,,,,")
        assertEquals("BVE", s)
    }

    @Test
    fun testParseStrShortestPossibleProprietaryId() {
        val s = SentenceId.parseStr("\$PAB,,,,,,,")
        assertEquals("AB", s)
    }

    @Test
    fun testParseStrLongestPossibleProprietaryId() {
        val s = SentenceId.parseStr("\$PABCDEFGHI,,,,,,,")
        assertEquals("ABCDEFGHI", s)
    }
}