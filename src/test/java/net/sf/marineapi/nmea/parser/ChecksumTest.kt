/* 
 * ChecksumTest.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.Checksum.add
import net.sf.marineapi.nmea.sentence.Checksum.calculate
import net.sf.marineapi.nmea.sentence.Checksum.index
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests the Checksum class.
 *
 * @author Kimmo Tuukkanen
 */
class ChecksumTest {
    /**
     * Test method for
     * [Checksum.add].
     */
    @Test
    fun testAdd() {
        val a = "\$GPGLL,6011.552,N,02501.941,E,120045,A"
        val b = "\$GPGLL,6011.552,N,02501.941,E,120045,A*"
        val c = "\$GPGLL,6011.552,N,02501.941,E,120045,A*00"
        val expected = "$a*26"
        assertEquals(expected, add(a))
        assertEquals(expected, add(b))
        assertEquals(expected, add(c))
    }

    /**
     * Test method for
     * [Checksum.calculate]
     * .
     */
    @Test
    fun testCalculate() {
        assertEquals("1D", calculate(BODTest.EXAMPLE))
        assertEquals("63", calculate(GGATest.EXAMPLE))
        assertEquals("26", calculate(GLLTest.EXAMPLE))
        assertEquals("0B", calculate(RMCTest.EXAMPLE))
        assertEquals("3D", calculate(GSATest.EXAMPLE))
        assertEquals("73", calculate(GSVTest.EXAMPLE))
        assertEquals("58", calculate(RMBTest.EXAMPLE))
        assertEquals("25", calculate(RTETest.EXAMPLE))
    }

    @Test
    fun testDelimiterIndex() {
        assertEquals(13, index("\$GPGGA,,,,,,,").toLong())
        assertEquals(13, index("\$GPGGA,,,,,,,*00").toLong())
    }
}